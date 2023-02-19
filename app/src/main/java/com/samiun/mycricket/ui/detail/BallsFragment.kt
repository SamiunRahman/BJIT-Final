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
import com.samiun.mycricket.databinding.FragmentBallsBinding
import com.samiun.mycricket.databinding.FragmentScoreCardBinding
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
    private lateinit var battingRV : RecyclerView
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
        val  viewModel = ViewModelProvider(this)[CricketViewModel::class.java]



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