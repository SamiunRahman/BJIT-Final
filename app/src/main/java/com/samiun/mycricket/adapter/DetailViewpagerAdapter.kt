package com.samiun.mycricket.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.samiun.mycricket.ui.detail.ScoreCardFragment


class DetailViewpagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle): FragmentStateAdapter(fragmentManager,lifecycle){
    override fun getItemCount(): Int {
        return 5
    }

    override fun createFragment(position: Int): Fragment {

        when(position){
            0->{
              return ScoreCardFragment()

            }
            1-> {
               return ScoreCardFragment()

            }
            2->{
                return ScoreCardFragment()
            }

            3->{
                return ScoreCardFragment()

            }

            else ->{
                return ScoreCardFragment()

            }

        }
    }
}
