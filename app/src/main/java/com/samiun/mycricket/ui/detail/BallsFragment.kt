package com.samiun.mycricket.ui.detail

import android.os.Binder
import android.os.Bundle
import android.renderscript.ScriptGroup.Binding
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.samiun.mycricket.R
import com.samiun.mycricket.adapter.BallsAdapter
import com.samiun.mycricket.adapter.BattingCardAdapter
import com.samiun.mycricket.adapter.BowlingCardAdapter
import com.samiun.mycricket.databinding.FragmentBallsBinding
import com.samiun.mycricket.databinding.FragmentScoreCardBinding
import com.samiun.mycricket.model.fixturewithdetails.Ball
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


class BallsFragment : Fragment() {
    val gerArgs: BallsFragmentArgs by navArgs()
    private var _binding: FragmentBallsBinding? = null
    private val binding get() = _binding!!
    private lateinit var ballsRv : RecyclerView
    private lateinit var bowlingRV: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentBallsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val data = gerArgs.matchdetails
        val  viewModel = ViewModelProvider(this)[CricketViewModel::class.java]
        ballsRv = binding.ballsRv
        var battingfirstTeamId: Int?
        var bowlingfirstTeamId: Int?

        if((data.toss_won_team_id==data.localteam_id && data.elected == "bowling")||(data.toss_won_team_id==data.visitorteam_id && data.elected == "batting")){
            battingfirstTeamId = data.visitorteam_id
            bowlingfirstTeamId = data.localteam_id
        }
        else{
            battingfirstTeamId = data.localteam_id
            bowlingfirstTeamId = data.visitorteam_id
        }

        val balls: List<Ball>? = data.balls
        balls?.sortedWith(compareBy({ it.team_id != battingfirstTeamId }, { it.team_id != bowlingfirstTeamId }, { it.ball }))
        val ballsdesc = balls?.reversed()
        val adapterViewState = ballsRv.layoutManager?.onSaveInstanceState()
        ballsRv.layoutManager?.onRestoreInstanceState(adapterViewState)
        ballsRv.adapter = ballsdesc?.let { BallsAdapter(requireContext(), viewModel, it) }




    }

    private fun bowlingAdapter(
        viewModel: CricketViewModel,
        bowling: MutableList<Bowling>,
        lineup: @RawValue List<Lineup>
    ) {
        val adapterViewState = bowlingRV.layoutManager?.onSaveInstanceState()
        bowlingRV.layoutManager?.onRestoreInstanceState(adapterViewState)
        bowlingRV.adapter = BowlingCardAdapter(requireContext(), viewModel, bowling, lineup)
    }

}