package com.samiun.mycricket.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.samiun.mycricket.MatchInforFragmentArgs
import com.samiun.mycricket.R
import com.samiun.mycricket.ScoreCardFragmentArgs
import com.samiun.mycricket.databinding.FragmentMatchInforBinding
import com.samiun.mycricket.databinding.FragmentScoreCardBinding
import com.samiun.mycricket.utils.Constants

class MatchInforFragment : Fragment() {

    val gerArgs: MatchInforFragmentArgs by navArgs()
    private var _binding: FragmentMatchInforBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMatchInforBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val data = gerArgs.matchdetails

        val localSquadStr = mutableListOf<String>()
        val visitorSquadStr =  mutableListOf<String>()
        for(i in data.lineup!!){
            if(i.lineup!!.team_id == data.localteam_id){
                i.fullname?.let { localSquadStr.add(it)
                }
            }
            else{
                i.fullname?.let { visitorSquadStr.add(it) }
            }
        }
        val localSquadtxt = localSquadStr.joinToString(", ")
        val visitorSquadtxt = visitorSquadStr.joinToString(", ")
        binding.localteamSquad.text = localSquadtxt
        binding.visitorteamSquad.text = visitorSquadtxt
        binding.dateData.text = data.starting_at?.let { Constants.dateFormat(it) }
        binding.timeData.text = data.starting_at?.let { Constants.timeFormat(it) }


    }


}