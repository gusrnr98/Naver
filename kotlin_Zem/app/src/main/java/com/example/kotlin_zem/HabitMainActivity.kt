package com.example.kotlin_zem

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlin_zem.Adapter.PagerAdapter
import com.example.kotlin_zem.Fragment.EndFragment
import com.example.kotlin_zem.Fragment.IngFragment
import com.example.kotlin_zem.Fragment.WaitFragment
import com.example.kotlin_zem.databinding.ActivityHabitMainBinding
import com.google.android.material.tabs.TabLayoutMediator



class HabitMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHabitMainBinding
    val tabTextList1 = arrayListOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHabitMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        for(i in 1..3){
            var range = 0..3
            var result = range.random()
            tabTextList1.add(result)
        }
        Log.e("list",tabTextList1.toString())
        tabadd(tabTextList1)

        binding.addBt.setOnClickListener{addbtClick()}
    }

    fun tabadd(result: ArrayList<Int>){
        var pagerAdapter = PagerAdapter(this)
        binding.habitstate.adapter = pagerAdapter
        pagerAdapter.setData(tabTextList1)
        TabLayoutMediator(binding.habittabLayout,binding.habitstate){ tab, position ->
            tab.text = result[position].toString()
        }.attach()
    }

    fun addbtClick(){
        var intent = Intent( this, AddHabitActivity::class.java)
        startActivity(intent)
    }

}