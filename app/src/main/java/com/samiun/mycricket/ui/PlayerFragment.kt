package com.samiun.mycricket.ui

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
                var format = "T20"
                getRuns(  careers,battingCareer,format)

                binding.playerBatting.setOnClickListener {
                     battingCareer = "men"

                    ///binding. battingCareerMan.setBackgroundColor(R.color.colorOnPrimary)
                    getRuns(  careers,battingCareer,format)
                    Log.d("Man Ranking", "onViewCreated:$ battingCareer $format ")
                }
                binding. playerBowling.setOnClickListener {
                     battingCareer = "women"
                    getRuns( battingCareer,format)
                    Log.d("Woman Ranking", "onViewCreated:$ battingCareer $format ")

                }
                binding.testranking.setOnClickListener {
                    format = "TEST"
                    getRuns( battingCareer,format)
                    Log.d("Test Ranking", "onViewCreated:$ battingCareer $format ")

                }
                binding.t20ranking.setOnClickListener {
                    format = "T20I"
                    getRuns( battingCareer,format)
                    Log.d("T20 Ranking", "onViewCreated:$ battingCareer $format ")

                }
                binding.odiranking.setOnClickListener {
                    format = "ODI"
                    getRuns( battingCareer,format)
                    Log.d("ODI Ranking", "onViewCreated:$ battingCareer $format ")

                }

            }

        }





    }
    fun getCountry(id: Int): String{
        return ""
    }

    fun getRuns(careers: List<Career>, type:String, isBatting:Boolean): Int{
        return careers.filter { it.type == type }
            .sumOf { it.batting?.runs_scored ?: 0 }
    }
}