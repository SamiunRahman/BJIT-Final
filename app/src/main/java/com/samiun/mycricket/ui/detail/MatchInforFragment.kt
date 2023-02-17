package com.samiun.mycricket.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.samiun.mycricket.R
import com.samiun.mycricket.databinding.FragmentMatchInforBinding
import com.samiun.mycricket.databinding.FragmentScoreCardBinding
import com.samiun.mycricket.network.overview.CricketViewModel
import com.samiun.mycricket.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
        binding.infoMatchdata.text = data.round
        val  viewModel = ViewModelProvider(this)[CricketViewModel::class.java]

        GlobalScope.launch {
            val tossWinner = data.toss_won_team_id?.let { viewModel.findTeamById(it) }
            val firstUmpire = data.first_umpire_id?.let { viewModel.findOfficialbyId(it) }
            val secondUmpire = data.second_umpire_id?.let { viewModel.findOfficialbyId(it) }
            val refree = data.referee_id?.let { viewModel.findOfficialbyId(it) }
            val leagues = data.league_id?.let { viewModel.findLeaguebyId(it) }
            val venue = data.venue_id?.let { viewModel.findVenueById(it) }


            withContext(Dispatchers.Main){
                binding.tossData.text = "${tossWinner!!.name} won and Elected ${data.elected}"
                if (venue != null) {
                    binding.venuData.text = venue.name
                    binding.stadiumData.text = venue.name
                    binding.cityData.text = venue.city
                }
                if (firstUmpire != null&& secondUmpire!=null) {
                    binding.umpireData.text ="${firstUmpire.fullname}, ${secondUmpire.fullname}"
                }
                if (refree != null) {
                    binding.reffreeData.text = refree.fullname
                }
                if (leagues != null) {
                    binding.seriesData.text = leagues.name
                }



            }

        }


        binding.tossData.text = data.elected



    }


}