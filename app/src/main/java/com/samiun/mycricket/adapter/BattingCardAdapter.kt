package com.samiun.mycricket.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.samiun.mycricket.R
import androidx.recyclerview.widget.RecyclerView
import com.samiun.mycricket.model.fixturewithdetails.Batting
import com.samiun.mycricket.model.fixturewithdetails.Lineup
import com.samiun.mycricket.network.overview.CricketViewModel
import kotlinx.android.synthetic.main.batting_card.view.*

class BattingCardAdapter(private val context:Context, private val viewModel : CricketViewModel, private var arrayList:MutableList<Batting>, private val lineup:List<Lineup>)
    :RecyclerView.Adapter<BattingCardAdapter.BattingCardViewHolder>(){
        class BattingCardViewHolder(
            binding: View
        ):RecyclerView.ViewHolder(binding){
            val playerName = itemView.playername
            val runs = itemView.runorover
            val balls = itemView.ballormaiden
            val fours = itemView.foursorruns
            val sixs = itemView.sixorwicket
            val strikerate = itemView.strikerateoreconomy

        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BattingCardViewHolder {
        val root = LayoutInflater.from(parent.context).inflate(R .layout.batting_card,parent,false)
        return BattingCardViewHolder(root)
    }

    override fun onBindViewHolder(holder: BattingCardViewHolder, position: Int) {
        val info = arrayList[position]
        for(i in lineup!!){
            if(info.player_id== i.id){
                holder.playerName.text = "${i.firstname} ${i.lastname}"
            }
        }
        holder.runs.text = "${info.score}"
        holder.balls.text = "${info.ball}"
        holder.fours.text = "${info.four_x}"
        holder.sixs.text = "${info.six_x}"
        holder.strikerate.text = "${info.rate}"
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }
}