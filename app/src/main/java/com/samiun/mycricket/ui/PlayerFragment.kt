package com.samiun.mycricket.ui

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.samiun.mycricket.R
import com.samiun.mycricket.adapter.TeamDetailViewPagerAdapter
import com.samiun.mycricket.databinding.FragmentPlayerBinding
import com.samiun.mycricket.databinding.FragmentTeamBinding
import com.samiun.mycricket.model.playerDetails.Career
import com.samiun.mycricket.model.playerDetails.PlayerDetailsData
import com.samiun.mycricket.network.overview.CricketViewModel
import com.samiun.mycricket.utils.Constants

class PlayerFragment : Fragment() {
    private lateinit var viewModel: CricketViewModel

    private val navArgs by navArgs<PlayerFragmentArgs>()
    private var _binding: FragmentPlayerBinding? = null
    val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPlayerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val PlayerId = navArgs.playerId


        viewModel = ViewModelProvider(this)[CricketViewModel::class.java]

        viewModel.getPlayerCareer(PlayerId).observe(viewLifecycleOwner){
            if (it != null) {
                binding.playerName.text = it.fullname
                binding.playerAge.text = Constants.calculateAge(it.dateofbirth)
                binding.playerType.text= it.id.toString()
                binding.playerCountry.text =getCountry(it.country_id!!)

                val careers: List<Career> = it.career!!
                var battingCareer = true

                val matches = getMatches(careers)
                val innings = getInnings(careers)
                val runs = getRuns(careers)
                val balls = getBalls(careers)
                val highest = getHighest(careers)
                val average = getAverage(careers)
                val stirkerate  = getStrikerate(careers)




                binding.playert20runs.text = runs[0].toString()
                binding.playerOdruns.text = runs[1].toString()
                binding.playerTestruns.text = runs[2].toString()

                Log.e("Player Fragment", "Runs onViewCreated: ${runs[0]}, ${runs[1]}")

               // getRuns(  careers,battingCareer,format)
//
//                binding.playerBatting.setOnClickListener {
//                     battingCareer = "men"
//
//                    ///binding. battingCareerMan.setBackgroundColor(R.color.colorOnPrimary)
//                    getRuns(  careers,battingCareer,format)
//                    Log.d("Man Ranking", "onViewCreated:$ battingCareer $format ")
//                }
//                binding. playerBowling.setOnClickListener {
//                     battingCareer = "women"
//                    getRuns( battingCareer,format)
//                    Log.d("Woman Ranking", "onViewCreated:$ battingCareer $format ")
//
//                }
//                binding.testranking.setOnClickListener {
//                    format = "TEST"
//                    getRuns( battingCareer,format)
//                    Log.d("Test Ranking", "onViewCreated:$ battingCareer $format ")
//
//                }
//                binding.t20ranking.setOnClickListener {
//                    format = "T20I"
//                    getRuns( battingCareer,format)
//                    Log.d("T20 Ranking", "onViewCreated:$ battingCareer $format ")
//
//                }
//                binding.odiranking.setOnClickListener {
//                    format = "ODI"
//                    getRuns( battingCareer,format)
//                    Log.d("ODI Ranking", "onViewCreated:$ battingCareer $format ")
//
//                }

            }

        }





    }

    private fun getStrikerate(careers: List<Career>): List<Int> {
        val strikerate = mutableListOf<Int>()
        val runs = getRuns(careers)
        val balls = getBalls(careers)
        val t20 =  (runs[0]/balls[0])*100
        val odi =  (runs[1]/balls[1])*100
        val test =  (runs[2]/balls[2])*100
        strikerate.add(t20)
        strikerate.add(odi)
        strikerate.add(test)
        binding.playert20SR.text = t20.toString()
        binding.playerTestSR.text = test.toString()
        binding.playerOdSR.text = odi.toString()
        return strikerate

    }

    private fun getAverage(careers: List<Career>): List<Int> {
        val runs = getRuns(careers)
        val inngings = getInnings(careers)
        val notOuts = mutableListOf<Int>()
        val t20= careers.filter { it.type =="T20"|| it.type =="T20I"}
            .sumOf { it.batting?.not_outs ?: 0 }
        notOuts.add(t20)

        val test= careers.filter { it.type =="Test/5day"}
            .sumOf { it.batting?.innings ?: 0 }
        notOuts.add(test)

        val odi= careers.filter { it.type =="ODI"}
            .sumOf { it.batting?.innings ?: 0 }
        notOuts.add(odi)


        val average = mutableListOf<Int>()

        val t20average =runs[0]/( inngings[0]-notOuts[0])
        val odiAverage =runs[1]/( inngings[1]-notOuts[1])
        val testAverage =runs[2]/( inngings[2]-notOuts[2])
        average.add(t20average)
        average.add(odiAverage)
        average.add(testAverage)

        binding.playert20average.text = t20average.toString()
        binding.playerTestaverage.text = testAverage.toString()
        binding.playerOdiaverage.text = odiAverage.toString()

        return average


    }

    private fun getHighest(careers: List<Career>): MutableList<Int> {
        val highest = mutableListOf<Int>()
        val t20= careers.filter { it.type =="T20"|| it.type =="T20I"}
            .maxOf { it.batting?.highest_inning_score ?: 0 }
        highest.add(t20)

        val test= careers.filter { it.type =="Test/5day"}
            .maxOf { it.batting?.highest_inning_score ?: 0 }
        highest.add(test)

        val odi= careers.filter { it.type =="ODI"}
            .maxOf { it.batting?.highest_inning_score ?: 0 }
        highest.add(odi)

        binding.playert20highest.text = t20.toString()
        binding.playerTesthighest.text = test.toString()
        binding.playerOdiHighest.text = odi.toString()

        return highest
    }

    private fun getBalls(careers: List<Career>): MutableList<Int> {
        val balls = mutableListOf<Int>()
        val t20= careers.filter { it.type =="T20"|| it.type =="T20I"}
            .sumOf { it.batting?.balls_faced ?: 0 }
        balls.add(t20)

        val test= careers.filter { it.type =="Test/5day"}
            .sumOf { it.batting?.balls_faced ?: 0 }
        balls.add(test)

        val odi= careers.filter { it.type =="ODI"}
            .sumOf { it.batting?.balls_faced ?: 0 }
        balls.add(odi)

        binding.playert20balls.text = t20.toString()
        binding.playerTestballs.text = test.toString()
        binding.playerOdiballs.text = odi.toString()

        return balls
    }

    private fun getInnings(careers: List<Career>): List<Int> {
        val innings = mutableListOf<Int>()
        val t20= careers.filter { it.type =="T20"|| it.type =="T20I"}
            .sumOf { it.batting?.innings ?: 0 }
        innings.add(t20)

        val test= careers.filter { it.type =="Test/5day"}
            .sumOf { it.batting?.innings ?: 0 }
        innings.add(test)

        val odi= careers.filter { it.type =="ODI"}
            .sumOf { it.batting?.innings ?: 0 }
        innings.add(odi)

        binding.playert20Innings.text = t20.toString()
        binding.playerTestInnings.text = test.toString()
        binding.playerOdInnigns.text = odi.toString()
        return innings
    }

    private fun getMatches(careers: List<Career>): List<Int> {
        val matches = mutableListOf<Int>()
        val t20= careers.filter { it.type =="T20"|| it.type =="T20I"}
            .sumOf { it.batting?.matches ?: 0 }
        matches.add(t20)

        val test= careers.filter { it.type =="Test/5day"}
            .sumOf { it.batting?.matches ?: 0 }
        matches.add(test)

        val odi= careers.filter { it.type =="ODI"}
            .sumOf { it.batting?.matches ?: 0 }
         matches.add(odi)

        binding.playert20matches.text = t20.toString()
        binding.playerTestmathces.text = test.toString()
        binding.playerOdimatches.text = odi.toString()

        return matches
    }

    fun getCountry(id: Int): String{
        return ""
    }

    fun getRuns(careers: List<Career>):List<Int>{

        val runs = mutableListOf<Int>()
        val t20= careers.filter { it.type =="T20"|| it.type =="T20I"}
            .sumOf { it.batting?.runs_scored ?: 0 }
        runs.add(t20)

        val test= careers.filter { it.type =="Test/5day"}
            .sumOf { it.batting?.runs_scored ?: 0 }
        runs.add(test)

        val odi= careers.filter { it.type =="ODI"}
            .sumOf { it.batting?.runs_scored ?: 0 }
        runs.add(odi)

        return runs
    }
}