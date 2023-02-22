package com.samiun.mycricket.ui.detail

import android.os.Bundle
import android.util.Log
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
import com.samiun.mycricket.databinding.FragmentDetailStatsBinding
import com.samiun.mycricket.databinding.FragmentScoreCardBinding
import com.samiun.mycricket.model.fixturewithdetails.Batting
import com.samiun.mycricket.model.fixturewithdetails.Bowling
import com.samiun.mycricket.network.overview.CricketViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailStats : Fragment() {
    val gerArgs: DetailStatsArgs by navArgs()
    private var _binding: FragmentDetailStatsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailStatsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val  viewModel = ViewModelProvider(this)[CricketViewModel::class.java]

        val data = gerArgs.matchdetails


        val batting = data.batting
        val bowling = data.bowling


        val topBatters = batting?.sortedWith(compareByDescending<Batting> { it.score }.thenBy { it.ball })
            ?.take(3)

        val topBowlers = bowling?.sortedWith(compareByDescending<Bowling> { it.wickets }.thenBy { it.runs })
            ?.take(3)

        GlobalScope.launch {
            val runs = data.id?.let { viewModel.findFixturebyid(it) }
            try {
                val team1runs = runs?.runs?.get(0)?.score
                val team1wicket = runs?.runs?.get(0)?.wickets
                val team2runs = runs?.runs?.get(1)?.score
                val team2wicket = runs?.runs?.get(1)?.wickets
                val runsperwicket = team1runs?.div(team1wicket!!)
                val runs2perwicket = team2runs?.div(team2wicket!!)
            }
            catch (e: Exception){
                Log.e("Match Details Stats", "onViewCreated: $e", )
            }

        }

        try {
            binding.rvTopBatsman.adapter = BattingCardAdapter(requireContext(), viewModel,
                topBatters as MutableList<Batting>, data.lineup!!)

            binding.rvMostImpactfulBowler.adapter = BowlingCardAdapter(requireContext(), viewModel,
                topBowlers as MutableList<Bowling>, data.lineup!!)
        }
        catch (e: Exception){
            Log.e("Detail Stats Exception", "onViewCreated: $e", )
        }


/*        binding.rvMostImpactfulBowler.adapter = BowlingCardAdapter(requireContext(), viewModel,
            topBowlers as MutableList<Bowling>, data.lineup!!)*/

    }


}