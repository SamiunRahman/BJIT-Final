//package com.samiun.mycricket.adapter
//
//import android.os.Bundle
//import android.util.Log
//import androidx.fragment.app.Fragment
//import androidx.fragment.app.FragmentManager
//import androidx.lifecycle.Lifecycle
//import androidx.viewpager2.adapter.FragmentStateAdapter
//
//class DetailViewpagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle): FragmentStateAdapter(fragmentManager,lifecycle){
//    override fun getItemCount(): Int {
//        return 5
//    }
//
//    override fun createFragment(position: Int): Fragment {
//        //  val fragment = NewsFragment()
//        val args = Bundle()
////        when(position){
////            0->{
////                args.putString("type", Constant.topnews)
////                fragment.arguments = args
////                Log.d("TAG", "createFragment: ")
////                return fragment
////            }
////            1-> {
////                args.putString("type", Constant.sports)
////                fragment.arguments = args
////                return fragment
////            }
////            2->{
////                args.putString("type", Constant.entertainment)
////                fragment.arguments = args
////                return fragment
////            }
////
////            3->{
////                args.putString("type", Constant.technews)
////                fragment.arguments = args
////                return fragment
////            }
////
////            else ->{
////                args.putString("type", Constant.business)
////                fragment.arguments = args
////                return fragment
////            }
////
////        }
//
//}