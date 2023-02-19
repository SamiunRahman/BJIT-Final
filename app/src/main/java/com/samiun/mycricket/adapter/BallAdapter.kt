package com.samiun.mycricket.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.samiun.mycricket.R
import com.samiun.mycricket.model.fixturewithdetails.Ball
import com.samiun.mycricket.model.ranking.Team
import com.samiun.mycricket.network.overview.CricketViewModel
import com.samiun.mycricket.ui.RankingFragmentDirections
import kotlinx.android.synthetic.main.ranking_list.view.*

class BallsAdapter(private val context: Context, private val viewModel: CricketViewModel, private var data: List<Ball>)
    : RecyclerView.Adapter<BallsAdapter.BallsViewHolder>(){
    class BallsViewHolder(
        binding: View
    ): RecyclerView.ViewHolder(binding){
        val teamRank = itemView.rank_position
        val teamImage = itemView.ranking_image
        val teamname = itemView.team_name
        val rating = itemView.rating
        val item = itemView.ranking_item

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BallsViewHolder {
        val root = LayoutInflater.from(parent.context).inflate(R .layout.ranking_list,parent,false)
        Toast.makeText(context, "Oncreateviewholder", Toast.LENGTH_SHORT).show()
        return BallsViewHolder(root)
    }

    override fun onBindViewHolder(holder: BallsViewHolder, position: Int) {
        val info = data[position]

        holder.teamRank.text = info.ball.toString()
        holder.teamname.text = "${info.bowler?.fullname} to ${info.batsman?.fullname} "
       // holder.rating.text = info.score.

//
//        holder.item.setOnClickListener {
//            val action = RankingFragmentDirections.actionRankingFragmentToTeamFragment(info.id)
//            holder.itemView.findNavController().navigate(action)
//        }

    }

    override fun getItemCount(): Int {
        // Log.d("Ranking Adapter", "getItemCount: ${data.size}")
        return data.size

    }
}