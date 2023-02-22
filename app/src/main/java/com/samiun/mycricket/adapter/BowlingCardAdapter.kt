package com.samiun.mycricket.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.samiun.mycricket.R
import com.samiun.mycricket.model.fixturewithdetails.Bowling
import com.samiun.mycricket.model.fixturewithdetails.Lineup
import com.samiun.mycricket.network.overview.CricketViewModel
import com.samiun.mycricket.ui.DetailsFragmentDirections
import kotlinx.android.synthetic.main.batting_card.view.*

class BowlingCardAdapter(private val context: Context, private val viewModel : CricketViewModel, private var arrayList:MutableList<Bowling>, private val lineup:List<Lineup>)
    : RecyclerView.Adapter<BowlingCardAdapter.BowlingCardViewHolder>(){
    class BowlingCardViewHolder(
        binding: View
    ): RecyclerView.ViewHolder(binding){
        val playerName = itemView.playername
        val runs = itemView.runorover
        val balls = itemView.ballormaiden
        val fours = itemView.foursorruns
        val sixs = itemView.sixorwicket
        val strikerate = itemView.strikerateoreconomy
        val item = itemView.constraint_item

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BowlingCardAdapter.BowlingCardViewHolder {
        val root = LayoutInflater.from(parent.context).inflate(R .layout.batting_card,parent,false)
        return BowlingCardAdapter.BowlingCardViewHolder(root)
    }

    override fun onBindViewHolder(holder: BowlingCardAdapter.BowlingCardViewHolder, position: Int) {
        val info = arrayList[position]
        for(i in lineup!!){
            if(info.player_id== i.id){
                holder.playerName.text = "${i.firstname} ${i.lastname}"
            }
        }
        holder.runs.text = "${info.overs}"
        holder.balls.text = "${info.medians}"
        holder.fours.text = "${info.runs}"
        holder.sixs.text = "${info.wickets}"
        holder.strikerate.text = "${info.rate}"


        holder.item.setOnClickListener{
            try {
                val action = DetailsFragmentDirections.actionDetailsFragmentToPlayerFragment2(info.player_id!!)
                holder.itemView.findNavController().navigate(action)
            }catch (e: Exception){
                Log.e("Batting Adapter", "onBindViewHolder: $e", )

            }
        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }
}