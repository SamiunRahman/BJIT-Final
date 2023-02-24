package com.samiun.mycricket.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.samiun.mycricket.R
import com.samiun.mycricket.adapter.RankingAdapter
import com.samiun.mycricket.databinding.FragmentRankingBinding
import com.samiun.mycricket.network.overview.CricketViewModel
import kotlinx.android.synthetic.main.match_list.*


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

        binding.bottomNav.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home_bottom_nav->{

                    findNavController().navigate(R.id.homeFragment)
                    return@setOnItemSelectedListener true
                }
                R.id.ranking_bottom_nav->{
                    Toast.makeText(requireContext(), "You are on ranking Fragment", Toast.LENGTH_SHORT).show()
                    return@setOnItemSelectedListener true
                }
                R.id.search_bottom_nav->{
                    findNavController().navigate(R.id.searchFragment)
                    return@setOnItemSelectedListener true
                }
                else ->{
                    findNavController().navigate(R.id.seriesFragment)
                    return@setOnItemSelectedListener true
                }
            }
        }

        rankingRecyclerView = binding.rankingRv
        rankingAdapter(gender,format)


        }

    fun rankingAdapter(gender:String, format: String){
        try {
            viewModel.getRanking(gender, format).observe(viewLifecycleOwner){
                if(it!=null){
                    rankingRecyclerView.adapter = RankingAdapter(requireContext(), viewModel, it.team!!)
                }
            }
        }catch (e:Exception){
            Log.e("Ranking Fragment Exception", "rankingAdapter:$e ", )
        }

    }

}