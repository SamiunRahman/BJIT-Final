package com.samiun.mycricket.adapter

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.samiun.mycricket.model.fixturewithdetails.FixtureWithDetailsData
import com.samiun.mycricket.ui.detail.*


class DetailViewpagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    val adapterData: FixtureWithDetailsData
): FragmentStateAdapter(fragmentManager,lifecycle){
    override fun getItemCount(): Int {
        return 5
    }

    override fun createFragment(position: Int): Fragment {
        val args = Bundle()
        Log.e("Get Details", "adapter: ${adapterData?.lineup?.size}")

        when(position){
            0->{
                val fragment = MatchInforFragment()
                fragment.arguments = Bundle().apply {
                    putParcelable("matchdetails",adapterData)
                }
                return fragment
            }
            1-> {
                val fragment = SummeryFragment()
                fragment.arguments = Bundle().apply {
                    putParcelable("matchdetails",adapterData)
                }
                return fragment


            }
            2->{
                val fragment = ScoreCardFragment()
                //args.putParcelable("matchdetails", adapterData)
                fragment.arguments = Bundle().apply {
                    putParcelable("matchdetails",adapterData)
                }
                return fragment

            }
            3->{
                val fragment = BallsFragment()
                //args.putParcelable("matchdetails", adapterData)
                fragment.arguments = Bundle().apply {
                    putParcelable("matchdetails",adapterData)
                }
                return fragment

            }


            else ->{
                val fragment = DetailStats()
                fragment.arguments = Bundle().apply {
                    putParcelable("matchdetails",adapterData)
                }
                return fragment
            }

        }
    }


}
