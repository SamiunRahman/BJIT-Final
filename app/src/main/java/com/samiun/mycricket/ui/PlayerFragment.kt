//package com.samiun.mycricket.ui
//
//import android.os.Bundle
//import android.util.Log
//import androidx.fragment.app.Fragment
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.lifecycle.ViewModelProvider
//import androidx.navigation.fragment.navArgs
//import com.bumptech.glide.Glide
//import com.google.android.material.tabs.TabLayoutMediator
//import com.samiun.mycricket.R
//import com.samiun.mycricket.adapter.TeamDetailViewPagerAdapter
//import com.samiun.mycricket.databinding.FragmentPlayerBinding
//import com.samiun.mycricket.databinding.FragmentTeamBinding
//import com.samiun.mycricket.network.overview.CricketViewModel
//
//class PlayerFragment : Fragment() {
//    private lateinit var viewModel: CricketViewModel
//
//    private val navArgs by navArgs<PlayerFragmentArgs>()
//    private var _binding: FragmentPlayerBinding? = null
//    val binding get() = _binding!!
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        _binding = FragmentPlayerBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        val PlayerId = navArgs.playerId
//        rec
//
//        viewModel = ViewModelProvider(this)[CricketViewModel::class.java]
//
//        viewModel.getPlayerDetails(PlayerId).observe(viewLifecycleOwner){
//            if (it != null) {
//                // detailData = it
//                Log.e("Get Details", "onViewCreated: ${it?.squad?.size}")
//                binding.homeTeamName.text = it.name
//                Glide
//                    .with(requireContext())
//                    .load(it.image_path)
//                    .placeholder(R.drawable.image_downloading)
//                    .error(R.drawable.not_found_image)
//                    .into(binding.teamImage)
//
//                val tabAdapter = TeamDetailViewPagerAdapter(childFragmentManager, lifecycle, it)
//                viewpager.adapter = tabAdapter
//                TabLayoutMediator(tabLayout, viewpager) { tab, position ->
//                    when (position) {
//                        0 -> {
//                            tab.text = "Fixure"
//                        }
//                        1 -> {
//                            tab.text = "Results"
//                        }
//                        2 -> {
//                            tab.text = "Squad"
//                        }
//                        else -> {
//                            tab.text = "Stats"
//                        }
//                    }
//                }.attach()
//
//            }
//
//        }
//
//
//
//    }
//}