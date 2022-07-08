package com.example.kotlin_zem

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget
import com.example.kotlin_zem.Fragment.TabFragment
import com.example.kotlin_zem.database.ZemEndDB
import com.example.kotlin_zem.databinding.ActivityHabitMainBinding


class HabitMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHabitMainBinding
    private val fragmentManager = supportFragmentManager
    private val transaction: FragmentTransaction = fragmentManager.beginTransaction()
    private val tabFragment = TabFragment()
    var dbe: ZemEndDB? =null
    var zemcon = 0
    var tabstate = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHabitMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val gifImage = GlideDrawableImageViewTarget(binding.imageView)
        Glide.with(this).load(R.drawable.zemgif).into(gifImage)

        dbe = ZemEndDB.getInstance(this)
        run()

        val bundle = Bundle()
        val dataId = intent.getLongExtra("GETID",0)

        tabstate = intent.getIntExtra("TABSTATE",2)
        Log.e("tabstate",tabstate.toString())

        bundle.putLong("GETID",dataId)

        tabFragment.arguments = bundle
        transaction.replace(R.id.tabFramelayout,tabFragment).commit()
        binding.addBt.setOnClickListener{addbtClick()}
    }

    fun gettabState(): Int{
        return tabstate
    }

    fun addbtClick(){
        var intent = Intent( this, AddHabitActivity::class.java)
        startActivity(intent)
    }

    fun run(){
        var a = Thread(
            Runnable {
                zemcon = dbe?.ZemEndDao()?.sumZemcon()!!

                Log.e("ZEMCON", zemcon.toString())

                binding.zemconcount.text = zemcon.toString()
            }
        )
        a.start()
        a.join()
    }


}