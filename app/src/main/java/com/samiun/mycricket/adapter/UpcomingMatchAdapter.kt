package com.samiun.mycricket.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.samiun.mycricket.R
import com.samiun.mycricket.model.fixture.FixtureEntity
import com.samiun.mycricket.network.overview.CricketViewModel
import kotlinx.android.synthetic.main.fragment_details.view.*
import kotlinx.android.synthetic.main.match_list.view.*
import kotlinx.coroutines.*

class UpcomingMatchAdapter(private val context: Context, private val viewModel: CricketViewModel,private var arrayList: List<FixtureEntity>)
    :RecyclerView.Adapter<UpcomingMatchAdapter.UpcomingMatchViewHolder>(){
    class UpcomingMatchViewHolder(
        binding: View
    ): RecyclerView.ViewHolder(binding){
        val homeTeamName = itemView.home_team
        val awayTeamName = itemView.away_team
        val hometeamImage = itemView.home_toss
        val awayteamImage = itemView.away_toss
        val notes = itemView.match_notes
        val homescore = itemView.home_team_score
        val awayscore = itemView.away_team_score


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpcomingMatchViewHolder {
        val root = LayoutInflater.from(parent.context).inflate(R .layout.match_list,parent,false)
        return UpcomingMatchAdapter.UpcomingMatchViewHolder(root)
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onBindViewHolder(holder: UpcomingMatchAdapter.UpcomingMatchViewHolder, position: Int) {
        val match = arrayList[position]
        holder.notes.text = match.starting_at
        Log.e("Fixture with run", "onBindViewHolder:${match.id} ", )




        GlobalScope.launch {
            val hometeam = match.localteam_id?.let { viewModel.findTeamById(it) }
            val awayteam = match.visitorteam_id?.let { viewModel.findTeamById(it) }

            withContext(Dispatchers.Main) {

                if (hometeam != null) {

                    if(hometeam.name!!.length>15){
                        holder.homeTeamName.text = hometeam.code
                    }
                    else
                        holder.homeTeamName.text = hometeam.name




                    Glide
                        .with(context)
                        .load(hometeam.image_path)
                        .placeholder(R.drawable.image_downloading)
                        .error(R.drawable.not_found_image)
                        .into(holder.hometeamImage)
                } else {
                    val id = match.id.toString()
                    holder.homeTeamName.text = id
                    Log.e("Adapter", "${match.id} ", )
                }

                if (awayteam != null) {
                    if(awayteam.name!!.length>15){
                        holder.awayTeamName.text = awayteam.code
                    }
                    else
                        holder.awayTeamName.text = awayteam.name
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