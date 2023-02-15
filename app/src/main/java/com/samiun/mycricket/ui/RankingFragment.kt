package com.samiun.mycricket.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.samiun.mycricket.adapter.BowlingCardAdapter
import com.samiun.mycricket.adapter.RankingAdapter
import com.samiun.mycricket.databinding.FragmentRankingBinding
import com.samiun.mycricket.model.fixturewithdetails.Bowling
import com.samiun.mycricket.network.overview.CricketViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class RankingFragment : Fragment() {
    var grid = 1
    private lateinit var viewModel: CricketViewModel
    private lateinit var rankingRecyclerView: RecyclerView


    private var _binding: FragmentRankingBinding? = null
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

        _binding = FragmentRankingBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this)[CricketViewModel::class.java]
        var gender = "men"
        var format = "T20I"
        rankingAdapter(gender,format)

        binding.genderMan.setOnClickListener {
            gender = "men"
            binding.testranking.visibility = View.VISIBLE
            rankingAdapter(gender,format)
            Log.d("Man Ranking", "onViewCreated:$gender $format ")
        }
        binding.genderWoman.setOnClickListener {
            gender = "women"
            binding.testranking.visibility = View.GONE
            rankingAdapter(gender,format)
            Log.d("Woman Ranking", "onViewCreated:$gender $format ")

        }
        binding.testranking.setOnClickListener {
            format = "TEST"
            rankingAdapter(gender,format)
            Log.d("Test Ranking", "onViewCreated:$gender $format ")

        }
        binding.t20ranking.setOnClickListener {
            format = "T20I"
            rankingAdapter(gender,format)
            Log.d("T20 Ranking", "onViewCreated:$gender $format ")

        }
        binding.odiranking.setOnClickListener {
            format = "ODI"
            rankingAdapter(gender,format)
            Log.d("ODI Ranking", "onViewCreated:$gender $format ")

        }
        rankingRecyclerView = binding.rankingRv
        viewModel.getRanking("men", "T20I").observe(viewLifecycleOwner){
            rankingRecyclerView.adapter = RankingAdapter(requireContext(), viewModel, it.team!!)
            Log.d("Ranking", "onViewCreated: ${it.team}")
        }

//        viewModel.getRanking("man","T20").observe(viewLifecycleOwner){

        }

//        binding.bottomNav.setOnItemSelectedListener {
//            when(it.itemId){
//                R.id.home_bottom_nav->{
//                    findNavController().navigate(R.id.homeFragment)
//                    Toast.makeText(requireContext(), "You are on home!", Toast.LENGTH_SHORT).show()
//                    return@setOnItemSelectedListener true
//                }
//                R.id.ranking_bottom_nav->{
//
//                    Toast.makeText(requireContext(), "Your are on Ranking!", Toast.LENGTH_SHORT).show()
//                    return@setOnItemSelectedListener true
//                }
//                R.id.search_bottom_nav->{
//                    Toast.makeText(requireContext(), "Search Button Clicked", Toast.LENGTH_SHORT).show()
//                    return@setOnItemSelectedListener true
//                }
//                else ->{
//                    Toast.makeText(requireContext(), "More Button Clicked", Toast.LENGTH_SHORT).show()
//                    return@setOnItemSelectedListener true
//                }
//            }
//
//        }

    fun rankingAdapter(gender:String, format: String){
        viewModel.getRanking(gender, format).observe(viewLifecycleOwner){
            rankingRecyclerView.adapter = RankingAdapter(requireContext(), viewModel, it.team!!)
            Log.d("Ranking", "onViewCreated: ${it.team}")
        }
    }

}