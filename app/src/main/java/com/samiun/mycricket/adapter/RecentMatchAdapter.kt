package com.samiun.mycricket.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.samiun.mycricket.R
import com.samiun.mycricket.model.fixture.FixtureEntity
import com.samiun.mycricket.model.team.TeamEntity
import com.samiun.mycricket.network.overview.CricketViewModel
import com.samiun.mycricket.ui.HomeFragment
import kotlinx.android.synthetic.main.fragment_details.view.*
import kotlinx.android.synthetic.main.match_list.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RecentMatchAdapter(private val context: Context, private val viewModel: CricketViewModel,private var arrayList: List<FixtureEntity>)
    :RecyclerView.Adapter<RecentMatchAdapter.RecentMatchViewHolder>(){
        class RecentMatchViewHolder(
            binding: View
        ): RecyclerView.ViewHolder(binding){
            val homeTeamName = itemView.home_team
            val awayTeamName = itemView.away_team
            val hometeamImage = itemView.home_toss
            val awayteamImage = itemView.away_toss
            val notes = itemView.match_notes

        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentMatchViewHolder {
        val root = LayoutInflater.from(parent.context).inflate(R .layout.match_list,parent,false)
        return RecentMatchViewHolder(root)
    }

    override fun onBindViewHolder(holder: RecentMatchViewHolder, position: Int) {
        val match = arrayList[position]
        holder.notes.text = match.note

        GlobalScope.launch {
            val hometeam = match.localteam_id?.let { viewModel.findTeamById(it) }
            val awayteam = match.visitorteam_id?.let { viewModel.findTeamById(it) }

            withContext(Dispatchers.Main) {
                if (hometeam != null) {
                    holder.homeTeamName.text = hometeam.code
                    Glide
                        .with(context)
                        .load(hometeam.image_path)
                        .placeholder(R.drawable.image_downloading)
                        .error(R.drawable.not_found_image)
                        .into(holder.hometeamImage)
                } else {
                    val id = match.id.toString()
                    holder.homeTeamName.text = id
                }

                if (awayteam != null) {
                    holder.awayTeamName.text = awayteam.code
                    Glide
                        .with(context)
                        .load(awayteam.image_path)
                        .placeholder(R.drawable.image_downloading)
                        .error(R.drawable.not_found_image)
                        .into(holder.awayteamImage)
                } else {
                    val id = match.id.toString()
                    holder.awayTeamName.text = id
                }
            }

        }

    }


    override fun getItemCount(): Int {
        return  arrayList.size
    }

}