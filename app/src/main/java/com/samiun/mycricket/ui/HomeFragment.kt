package com.samiun.mycricket.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.samiun.mycricket.R
import com.samiun.mycricket.adapter.RecentMatchAdapter
import com.samiun.mycricket.databinding.FragmentHomeBinding
import com.samiun.mycricket.model.fixture.FixtureEntity
import com.samiun.mycricket.network.overview.CricketViewModel

class HomeFragment : Fragment() {

    var grid = 1
    private lateinit var viewModel: CricketViewModel

    lateinit var matchList: List<FixtureEntity>
    private lateinit var liveRecyclerView: RecyclerView
    private lateinit var recentRecyclerView:RecyclerView
    private lateinit var upcomingRecyclerView: RecyclerView

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this)[CricketViewModel::class.java]

        recentRecyclerView = binding.recentMatchesRv

        viewModel.getCountries()
        viewModel.getLeagues()
        viewModel.getFixtures()
        viewModel.getTeams()


        viewModel.readFixtureEntity.observe(viewLifecycleOwner){
            val adapterViewState = recentRecyclerView.layoutManager?.onSaveInstanceState()
            recentRecyclerView.layoutManager?.onRestoreInstanceState(adapterViewState)
            recentRecyclerView.adapter = RecentMatchAdapter(requireContext(), viewModel, viewModel.readFixtureEntity.value!!)
        }
        binding.swipeToRefresh.setOnRefreshListener {
            //getTypeArticles(type)
            binding.swipeToRefresh.isRefreshing = false
        }
    }

}