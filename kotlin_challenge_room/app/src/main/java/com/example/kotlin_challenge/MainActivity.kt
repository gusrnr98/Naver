package com.example.kotlin_challenge

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.room.Room
import com.example.kotlin_challenge.database.Challenge
import com.example.kotlin_challenge.database.ChallengeAdapter
import com.example.kotlin_challenge.database.ChallengeDB
import com.example.kotlin_challenge.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var db:ChallengeDB? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.e("title","Main")
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        val navController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController)

    }
    override fun onDestroy() {
        Log.e("main","dis")
        super.onDestroy()
    }

    fun addActivity(){
        val intent = Intent(this, AddChallengeActivity::class.java)
        startActivity(intent)
    }
}