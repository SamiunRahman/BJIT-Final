package com.samiun.mycricket.adapter


import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.samiun.mycricket.R
import com.samiun.mycricket.model.fixturewithdetails.Lineup
import com.samiun.mycricket.model.players.PlayerData
import com.samiun.mycricket.model.team.TeamEntity
import com.samiun.mycricket.network.overview.CricketViewModel
import com.samiun.mycricket.ui.SearchFragmentDirections
import com.samiun.mycricket.ui.TeamFragmentDirections
import com.samiun.mycricket.ui.team.TeamSquadFragment
import com.samiun.mycricket.utils.MyApplication
import kotlinx.android.synthetic.main.lineup_list.view.*
import kotlinx.android.synthetic.main.ranking_list.view.*
import java.util.*

class LineupAdapter(private val context: Context, private var data: List<Lineup>)
    : RecyclerView.Adapter<LineupAdapter.LineupViewHolder>(){
    class LineupViewHolder(
        binding: View
    ): RecyclerView.ViewHolder(binding){
        val image = itemView.lineUpImage
        val name = itemView.lineupname


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LineupViewHolder {
        val root = LayoutInflater.from(parent.context).inflate(R .layout.lineup_list,parent,false)
        return LineupViewHolder(root)
    }

    override fun onBindViewHolder(holder: LineupViewHolder, position: Int) {
        val info = data[position]
        holder.name.text = info.fullname
        Glide
            .with(MyApplication.instance)
            .load(info.image_path)
            .placeholder(R.drawable.image_downloading)
            .error(R.drawable.not_found_image)
            .into(holder.image)

    }

    override fun getItemCount(): Int {
        return data.size

    }

}