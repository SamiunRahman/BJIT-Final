package com.samiun.mycricket.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.samiun.mycricket.R
import com.samiun.mycricket.adapter.BattingCardAdapter
import com.samiun.mycricket.adapter.BowlingCardAdapter
import com.samiun.mycricket.databinding.FragmentScoreCardBinding
import com.samiun.mycricket.databinding.FragmentSummeryBinding
import com.samiun.mycricket.model.fixturewithdetails.Batting
import com.samiun.mycricket.model.fixturewithdetails.Bowling
import com.samiun.mycricket.model.fixturewithdetails.Lineup
import com.samiun.mycricket.model.team.TeamEntity
import com.samiun.mycricket.network.overview.CricketViewModel
import kotlinx.android.parcel.RawValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SummeryFragment : Fragment() {
    val gerArgs: SummeryFragmentArgs by navArgs()
    private var _binding: FragmentSummeryBinding? = null
    private val binding get() = _binding!!
    //private lateinit var battingRV : RecyclerView
    //private lateinit var bowlingRV: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSummeryBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val  viewModel = ViewModelProvider(this)[CricketViewModel::class.java]

        val data = gerArgs.matchdetails


        var battingFirstTeam: TeamEntity
        var bowlingFirstTeam: TeamEntity
        val lineup = data.lineup
        val batting = data.batting
        val bowling = data.bowling
        val battingFirstBatting : MutableList<Batting> = mutableListOf<Batting>()
        val bowlingFirstBatting : MutableList<Batting> = mutableListOf<Batting>()
        val battingFirstBowling : MutableList<Bowling> = mutableListOf<Bowling>()
        val bowlingFirstBowling : MutableList<Bowling> = mutableListOf<Bowling>()
        var battingFirstId: Int
        val bowlingFirstId : Int
        if((data.localteam_id==data.toss_won_team_id && data.elected =="batting")||(data.visitorteam_id==data.toss_won_team_id && data.elected =="bowling")){
            battingFirstId = data.localteam_id!!
            bowlingFirstId  = data.visitorteam_id!!
        }
        else{
            bowlingFirstId = data.localteam_id!!
            battingFirstId  = data.visitorteam_id!!
        }


        GlobalScope.launch {
            battingFirstTeam = viewModel.findTeamById(battingFirstId)
            bowlingFirstTeam= viewModel.findTeamById(bowlingFirstId)
            withContext(Dispatchers.Main){
                binding.battingfirstSumTv.text = "${battingFirstTeam.name} Batting"
                binding.bowlingfirstSumTv.text = "${bowlingFirstTeam.name} Bowling"
                binding.battingsecondSumTv.text = "${bowlingFirstTeam.name} Batting"
                binding.bowlingsecondSumTv.text = "${battingFirstTeam.name} Bowling"
            }

        }
        for(i in lineup!!){
            for(j in batting!!){
                if(j.player_id == i.id && i.lineup!!.team_id==battingFirstId){
                    battingFirstBatting.add(j)
                }
                else if(j.player_id == i.id && i.lineup!!.team_id==bowlingFirstId){
                    bowlingFirstBatting.add(j)
                }
            }
        }

        for(i in lineup!!){
            for(j in bowling!!){
                if(j.player_id == i.id && i.lineup!!.team_id==battingFirstId){
                    battingFirstBowling.add(j)
                }
                else if(j.player_id == i.id && i.lineup!!.team_id==bowlingFirstId){
                    bowlingFirstBowling.add(j)

                }
            }
        }


        val battingFirstBattingSummery = battingFirstBatting.sortedWith(compareByDescending<Batting> { it.score }.thenBy { it.ball })
            .take(3)
        val bowlingFirstBattingSummery = bowlingFirstBatting.sortedWith(compareByDescending<Batting> { it.score }.thenBy { it.ball })
            .take(3)
        val battingFirstBowlingSummery = battingFirstBowling.sortedWith(compareByDescending<Bowling> { it.wickets }.thenBy { it.runs })
            .take(3)
        val bowlingFirstBowlingSummery = bowlingFirstBowling.sortedWith(compareByDescending<Bowling> { it.wickets }.thenBy { it.runs })
            .take(3)

        try {
            binding.battingfirstSumRv.adapter = BattingCardAdapter(requireContext(), viewModel,
                battingFirstBattingSummery as MutableList<Batting>, lineup)
        }
        catch (e:Exception){
            binding.battingfirstSumTv.text = "Summery is not Available!"

        }

        try {
            binding.battingsecondSumRv.adapter = BattingCardAdapter(requireContext(), viewModel,
                bowlingFirstBattingSummery as MutableList<Batting>, lineup)

        }
        catch (e:Exception){
            binding.battingsecondSumTv.visibility = View.GONE

        }
        try {

            binding.bowlingfirstSumRv.adapter = BowlingCardAdapter(requireContext(), viewModel,
                bowlingFirstBowlingSummery as MutableList<Bowling>, lineup)
        }
        catch (e:Exception){
            binding.bowlingfirstSumTv.visibility =View.GONE

        }

        try {

            binding.bowlingsecondSumRv.adapter = BowlingCardAdapter(requireContext(), viewModel,
                battingFirstBowlingSummery as MutableList<Bowling>, lineup)
        }
        catch (e:Exception){
            binding.bowlingsecondSumTv.visibility = View.GONE

        }



    }
}