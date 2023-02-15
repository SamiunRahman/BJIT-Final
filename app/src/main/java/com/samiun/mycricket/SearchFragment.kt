package com.samiun.mycricket

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.samiun.mycricket.adapter.RecentMatchAdapter
import com.samiun.mycricket.adapter.SearchTeamAdapter
import com.samiun.mycricket.databinding.FragmentHomeBinding
import com.samiun.mycricket.databinding.FragmentSearchBinding
import com.samiun.mycricket.network.overview.CricketViewModel


class SearchFragment : Fragment() {

    private lateinit var viewModel: CricketViewModel
    private lateinit var searchRecyclerview: RecyclerView
    private var _binding: FragmentSearchBinding? = null
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
        _binding = FragmentSearchBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[CricketViewModel::class.java]
        searchRecyclerview = binding.searchRv


        viewModel.readTeamEntity.observe(viewLifecycleOwner){
            val adapterViewState = searchRecyclerview.layoutManager?.onSaveInstanceState()
            searchRecyclerview.layoutManager?.onRestoreInstanceState(adapterViewState)
            searchRecyclerview.adapter = SearchTeamAdapter(requireContext(), viewModel, it!!)
        }
    }

}