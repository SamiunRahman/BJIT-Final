package com.samiun.mycricket.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.samiun.mycricket.ScoreCardFragmentArgs
import com.samiun.mycricket.databinding.FragmentScoreCardBinding
import com.samiun.mycricket.model.fixturewithdetails.Batting
import com.samiun.mycricket.model.fixturewithdetails.Bowling
import com.samiun.mycricket.model.team.TeamEntity
import com.samiun.mycricket.network.overview.CricketViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ScoreCardFragment : Fragment() {

    val gerArgs: ScoreCardFragmentArgs by navArgs()
    private var _binding: FragmentScoreCardBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentScoreCardBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val  viewModel = ViewModelProvider(this)[CricketViewModel::class.java]


        val data = gerArgs.matchdetails
        var battingScore = ""
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

        }
        for(i in lineup!!){
            for(j in batting!!){
                if(j.player_id == i.id && i.lineup!!.team_id==battingFirstId){
                    battingScore+=i.firstname+" "+i.lastname+" ${j.score} in ${j.ball}  batting  First\n"
                    battingFirstBatting.add(j)
                }
                else if(j.player_id == i.id && i.lineup!!.team_id==bowlingFirstId){
                    bowlingFirstBatting.add(j)
                    battingScore+=i.firstname+" "+i.lastname+" ${j.score} in ${j.ball}  Bowling  First\n"


                }
            }
        }
        battingScore+= "\n\n"

        for(i in lineup!!){
            for(j in bowling!!){
                if(j.player_id == i.id && i.lineup!!.team_id==battingFirstId){
                    battingScore+=i.firstname+" "+i.lastname+" ${j.runs} in ${j.overs} batting First\n"
                    battingFirstBowling.add(j)
                }
                else if(j.player_id == i.id && i.lineup!!.team_id==bowlingFirstId){
                    battingScore+=i.firstname+" "+i.lastname+" ${j.runs} in ${j.overs} Bowling First\n"
                    bowlingFirstBowling.add(j)

                }
            }
        }


binding.scoreCardFragmenttv.text = battingScore



    }

}