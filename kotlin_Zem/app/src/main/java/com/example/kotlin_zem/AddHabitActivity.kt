package com.example.kotlin_zem

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.example.kotlin_zem.Fragment.AddHabitStep1Fragment
import com.example.kotlin_zem.Fragment.AddHabitStep3Fragment
import com.example.kotlin_zem.databinding.ActivityAddHabitBinding

class AddHabitActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddHabitBinding
    private val fragmentManager = supportFragmentManager
    private val transaction: FragmentTransaction = fragmentManager.beginTransaction()
    private val fragmentStep3 = AddHabitStep3Fragment()
    private val fragmentStep1 = AddHabitStep1Fragment()

    var mContext: Context? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddHabitBinding.inflate(layoutInflater)
        setContentView(binding.root)

        transaction.replace(R.id.addhabitstepview, fragmentStep1).commit()

        mContext = this

    }

    fun habitMainActivity(){
        val intent = Intent(this, HabitMainActivity::class.java)
        startActivity(intent)
    }

    fun a(){
        transaction.replace(R.id.addhabitstepview, fragmentStep3).commit()
    }
}