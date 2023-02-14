package com.samiun.mycricket.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.samiun.mycricket.R
import com.samiun.mycricket.model.ranking.Team
import com.samiun.mycricket.network.overview.CricketViewModel
import kotlinx.android.parcel.RawValue
import kotlinx.android.synthetic.main.batting_card.view.*
import kotlinx.android.synthetic.main.ranking_list.view.*

class RankingAdapter(private val context: Context, private val viewModel: CricketViewModel, private var data: List<Team>)
    : RecyclerView.Adapter<RankingAdapter.RankingViewHolder>(){
    class RankingViewHolder(
        binding: View
    ): RecyclerView.ViewHolder(binding){
        val teamRank = itemView.rank_position
        val teamImage = itemView.ranking_image
        val teamname = itemView.team_name
        val rating = itemView.rating

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankingViewHolder {
        val root = LayoutInflater.from(parent.context).inflate(R .layout.ranking_list,parent,false)
        Toast.makeText(context, "Oncreateviewholder", Toast.LENGTH_SHORT).show()
        return RankingViewHolder(root)
    }

    override fun onBindViewHolder(holder: RankingViewHolder, position: Int) {
        val info = data[position]
        holder.teamRank.text = info.position.toString()
        holder.teamname.text = info.name
        holder.rating.text = info.ranking.rating.toString()
        Glide
            .with(context)
            .load(info.image_path)
            .placeholder(R.drawable.image_downloading)
            .error(R.drawable.not_found_image)
            .into(holder.teamImage)

    }

    override fun getItemCount(): Int {
       // Log.d("Ranking Adapter", "getItemCount: ${data.size}")
        return data.size

    }
}