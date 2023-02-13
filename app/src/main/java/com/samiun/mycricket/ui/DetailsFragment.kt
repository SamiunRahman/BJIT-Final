package com.samiun.mycricket.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.samiun.mycricket.R
import com.samiun.mycricket.adapter.DetailViewpagerAdapter
import com.samiun.mycricket.databinding.FragmentDetailsBinding
import com.samiun.mycricket.model.fixturewithdetails.FixtureWithDetailsData
import com.samiun.mycricket.network.overview.CricketViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

lateinit var detailData: FixtureWithDetailsData

class DetailsFragment : Fragment() {
    private lateinit var viewModel: CricketViewModel

    private val navArgs by navArgs<DetailsFragmentArgs>()
    private var _binding: FragmentDetailsBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val match = navArgs.fixturewithrun
        val tabLayout = binding.tabLayout
        val viewpager = binding.viewPager
        val tabAdapter = DetailViewpagerAdapter(childFragmentManager, lifecycle)
        viewpager.adapter = tabAdapter

        viewModel = ViewModelProvider(this)[CricketViewModel::class.java]
        val id:Int = match.id!!

        viewModel.getDetailsByMatch(match.id!!).observe(viewLifecycleOwner){
            Log.d("Get Details", "onViewCreated: ${it?.lineup?.size}")
            if (it != null) {
                detailData = it
                val tabAdapter = DetailViewpagerAdapter(childFragmentManager, lifecycle)
                viewpager.adapter = tabAdapter
                Toast.makeText(requireContext(), "adapter created", Toast.LENGTH_SHORT).show()

            }

        }
        GlobalScope.launch {
            val hometeam = match.localteam_id?.let { viewModel.findTeamById(it) }
            val awayteam = match.visitorteam_id?.let { viewModel.findTeamById(it) }

            withContext(Dispatchers.Main) {
                binding.homeTeamName.text = hometeam!!.name.toString()
                binding.awayTeamName.text = awayteam!!.name.toString()

                if(hometeam.id == match.runs?.get(0)?.team_id) {
                    val homescore =hometeam.code.toString()+"\n"+ match.runs?.get(0)?.score.toString()+"/"+match.runs?.get(0)?.wickets.toString()+" ("+match.runs?.get(0)?.overs.toString()+")"
                    val awayscore = awayteam.code.toString()+"\n"+match.runs?.get(1)?.score.toString()+"/"+match.runs?.get(1)?.wickets.toString()+" ("+match.runs?.get(1)?.overs.toString()+")"
                    binding.detailScore.text = homescore+"\n"+awayscore
                }
                else{
                    val awayscore =awayteam.code.toString()+"\n"+  match.runs?.get(0)?.score.toString()+"/"+match.runs?.get(0)?.wickets.toString()+" ("+match.runs?.get(0)?.overs.toString()+")"
                    val homescore =hometeam.code.toString()+"\n"+ match.runs?.get(1)?.score.toString()+"/"+match.runs?.get(1)?.wickets.toString()+" ("+match.runs?.get(1)?.overs.toString()+")"
                    binding.detailScore.text = homescore+"\n"+awayscore

                }
                Glide
                    .with(requireContext())
                    .load(hometeam.image_path)
                    .placeholder(R.drawable.image_downloading)
                    .error(R.drawable.not_found_image)
                    .into(binding.homeImage)

                Glide
                    .with(requireContext())
                    .load(awayteam.image_path)
                    .placeholder(R.drawable.image_downloading)
                    .error(R.drawable.not_found_image)
                    .into(binding.awayImage)
            }
        }

        TabLayoutMediator(tabLayout, viewpager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Info"
                }
                1 -> {
                    tab.text = "Live"
                }
                2 -> {
                    tab.text = "Scorecard"
                }
                3 -> {
                    tab.text = "Stats"
                }
                else -> {
                    tab.text = "Points Table"
                }
            }
        }.attach()
    }
}