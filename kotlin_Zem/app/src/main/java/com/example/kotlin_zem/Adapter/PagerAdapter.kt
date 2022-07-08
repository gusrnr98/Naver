package com.example.kotlin_zem.Adapter

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.kotlin_zem.Fragment.EndFragment
import com.example.kotlin_zem.Fragment.IngFragment
import com.example.kotlin_zem.Fragment.WaitFragment

private const val NUM_PAGES = 3
class PagerAdapter(fa: Fragment) : FragmentStateAdapter(fa) {
    var tabTextList1 = arrayListOf<Int>()

    override fun getItemCount(): Int = NUM_PAGES

    fun setData(result: ArrayList<Int>){
        tabTextList1 = result
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> {
                val ingFragment = IngFragment()
                var bundle = Bundle()
                bundle.putString("ING", tabTextList1[position].toString())
                ingFragment.arguments = bundle
                ingFragment
            }
            1 -> {
                val endFragment = EndFragment()
                var bundle = Bundle()
                bundle.putString("END", tabTextList1[position].toString())
                Log.e("ADAPTER",tabTextList1[position].toString() )
                endFragment.arguments = bundle
                endFragment
            }
            2 -> {
                val waitFragment = WaitFragment()
                var bundle = Bundle()
                bundle.putString("WAIT", tabTextList1[position].toString())
                waitFragment.arguments = bundle
                waitFragment
            }
            else -> IngFragment()
        }
    }
}
