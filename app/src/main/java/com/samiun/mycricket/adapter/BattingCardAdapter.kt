package com.samiun.mycricket.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import com.samiun.mycricket.R
import androidx.recyclerview.widget.RecyclerView
import com.samiun.mycricket.model.fixturewithdetails.Batting
import com.samiun.mycricket.model.fixturewithdetails.Lineup
import com.samiun.mycricket.network.overview.CricketViewModel
import com.samiun.mycricket.ui.DetailsFragmentDirections
import com.samiun.mycricket.ui.detail.ScoreCardFragmentDirections
import kotlinx.android.synthetic.main.batting_card.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

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
            val outBy = itemView.out_style
            val item = itemView.constraint_item

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
        var outStirng = ""
        holder.runs.text = "${info.score}"
        holder.balls.text = "${info.ball}"
        holder.fours.text = "${info.four_x}"
        holder.sixs.text = "${info.six_x}"
        holder.strikerate.text = "${info.rate}"

        holder.item.setOnClickListener{
            try {
                val action = DetailsFragmentDirections.actionDetailsFragmentToPlayerFragment2(info.player_id!!)
                holder.itemView.findNavController().navigate(action)
            }catch (e: Exception){
                Log.e("Batting Adapter", "onBindViewHolder: $e", )

            }
        }
        GlobalScope.launch {
            val bowler = info.bowling_player_id?.let { viewModel.findPlayerbyId(it) }
            val catchby = info.catch_stump_player_id?.let { viewModel.findLeaguebyId(it) }
            val runoutBy = info.runout_by_id?.let { viewModel.findPlayerbyId(it) }

            if(runoutBy!=null){
                outStirng = "Run Out(${runoutBy.fullname})"
            }
            else if(bowler!=null){
                outStirng = "B (${bowler.fullname})"
                if(catchby!=null){
                    outStirng+= " C ${catchby.name}"
                }

            }
            /*else{
                outStirng = "Not Out"
            }*/

            holder.outBy.text = outStirng
        }


    }

    override fun getItemCount(): Int {
        return arrayList.size
    }
}