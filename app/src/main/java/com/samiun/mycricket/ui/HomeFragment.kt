package com.samiun.mycricket.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.samiun.mycricket.R
import com.samiun.mycricket.adapter.RecentMatchAdapter
import com.samiun.mycricket.adapter.UpcomingMatchAdapter
import com.samiun.mycricket.databinding.FragmentHomeBinding
import com.samiun.mycricket.model.fixture.FixtureEntity
import com.samiun.mycricket.network.overview.CricketViewModel

private lateinit var topviewModel: CricketViewModel

class HomeFragment : Fragment() {
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
        topviewModel = viewModel

        recentRecyclerView = binding.recentMatchesRv
/*
        viewModel.getCountries()
        viewModel.getLeagues()
        viewModel.getFixtures()
        viewModel.getTeams()
        viewModel.getFixturesWithRun()
        viewModel.getRanking()*/

        viewModel.readFixtureWithRunEntity.observe(viewLifecycleOwner){
            val adapterViewState = recentRecyclerView.layoutManager?.onSaveInstanceState()
            recentRecyclerView.layoutManager?.onRestoreInstanceState(adapterViewState)
            recentRecyclerView.adapter = RecentMatchAdapter(requireContext(), viewModel, viewModel.readFixtureWithRunEntity.value!!)
        }

        upcomingRecyclerView = binding.upcomingMatchesRv
        viewModel.readFixtureEntity.observe(viewLifecycleOwner){
            val adapterViewState = recentRecyclerView.layoutManager?.onSaveInstanceState()
            recentRecyclerView.layoutManager?.onRestoreInstanceState(adapterViewState)
            upcomingRecyclerView.adapter = UpcomingMatchAdapter(requireContext(), viewModel, viewModel.readFixtureEntity.value!!)
        }

        binding.bottomNav.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home_bottom_nav->{

                    Toast.makeText(requireContext(), "You are on home!", Toast.LENGTH_SHORT).show()
                    return@setOnItemSelectedListener true
                }
                R.id.ranking_bottom_nav->{
                    findNavController().navigate(R.id.rankingFragment)

                    Toast.makeText(requireContext(), "Ranking Button Clicked", Toast.LENGTH_SHORT).show()
                    return@setOnItemSelectedListener true
                }
                R.id.search_bottom_nav->{
                    findNavController().navigate(R.id.searchFragment)
                    Toast.makeText(requireContext(), "Search Button Clicked", Toast.LENGTH_SHORT).show()
                    return@setOnItemSelectedListener true
                }
                else ->{
                    Toast.makeText(requireContext(), "More Button Clicked", Toast.LENGTH_SHORT).show()
                    return@setOnItemSelectedListener true
                }
            }

        }
    }
    fun getArticlesBackgroud() {
        topviewModel.getCountries()
        topviewModel.getLeagues()
        topviewModel.getFixtures()
        topviewModel.getTeams()
        topviewModel.getFixturesWithRun()
        topviewModel.getRanking()
    }


}