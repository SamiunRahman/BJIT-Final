package com.samiun.mycricket.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.samiun.mycricket.R
import com.samiun.mycricket.adapter.RankingAdapter
import com.samiun.mycricket.databinding.FragmentRankingBinding
import com.samiun.mycricket.network.overview.CricketViewModel


class RankingFragment : Fragment() {
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

    @SuppressLint("ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this)[CricketViewModel::class.java]
        var gender = "men"
        var format = "T20I"
        rankingAdapter(gender,format)

        binding.genderMan.setOnClickListener {
            gender = "men"

            ///binding.genderMan.setBackgroundColor(R.color.colorOnPrimary)
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

//        val toggleGroup = binding.formatGroup
//        toggleGroup.addOnButtonCheckedListener { group, checkedId, isChecked ->
//            val odiButton = binding.odiranking
//            if (isChecked) {
//                odiButton.setBackgroundColor(resources.getColor(R.color.colorPrimary))
//            } else {
//                odiButton.setBackgroundColor(resources.getColor(android.R.color.transparent))
//            }
//            val t20button = binding.t20ranking
//            if (isChecked) {
//                t20button.setBackgroundColor(resources.getColor(R.color.colorPrimary))
//            } else {
//                t20button.setBackgroundColor(resources.getColor(android.R.color.transparent))
//            }
//            val testButton = binding.testranking
//            if (isChecked) {
//                testButton.setBackgroundColor(resources.getColor(R.color.colorPrimary))
//            } else {
//                testButton.setBackgroundColor(resources.getColor(android.R.color.transparent))
//            }
//        }

        rankingRecyclerView = binding.rankingRv
        viewModel.getRanking("men", "T20I").observe(viewLifecycleOwner){
            rankingRecyclerView.adapter = RankingAdapter(requireContext(), viewModel, it.team!!)
            Log.d("Ranking", "onViewCreated: ${it.team}")
        }

        }

    fun rankingAdapter(gender:String, format: String){
        viewModel.getRanking(gender, format).observe(viewLifecycleOwner){
            rankingRecyclerView.adapter = RankingAdapter(requireContext(), viewModel, it.team!!)
            Log.d("Ranking", "onViewCreated: ${it.team}")
        }
    }

}