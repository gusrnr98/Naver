package com.example.kotlin_challenge

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.kotlin_challenge.databinding.ActivityAddChallengeBinding
import com.example.kotlin_challenge.fragment.AddChallengeStep1Fragment
import com.example.kotlin_challenge.fragment.AddChallengeStep2Fragment

class AddChallengeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddChallengeBinding
    private val fragmentManager = supportFragmentManager
    private val transaction: FragmentTransaction = fragmentManager.beginTransaction()
    private val fragmentStep1 = AddChallengeStep1Fragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("AddChallengeActivity","onCreate")

        binding = ActivityAddChallengeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        transaction.replace(R.id.addChallengeLayout, fragmentStep1).commit()

    }

    fun mainActivity(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}