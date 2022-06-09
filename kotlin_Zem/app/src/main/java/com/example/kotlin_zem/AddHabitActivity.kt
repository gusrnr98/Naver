package com.example.kotlin_zem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.GridLayoutManager
import com.example.kotlin_zem.Adapter.HabitAdapter
import com.example.kotlin_zem.DataClass.HabitItem
import com.example.kotlin_zem.Fragment.AddHabitStep1Fragment
import com.example.kotlin_zem.databinding.ActivityAddHabitBinding

class AddHabitActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddHabitBinding
    private val fragmentManager = supportFragmentManager
    private val transaction: FragmentTransaction = fragmentManager.beginTransaction()
    private val fragmentStep1 = AddHabitStep1Fragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddHabitBinding.inflate(layoutInflater)
        setContentView(binding.root)

        transaction.replace(R.id.addhabitstepview, fragmentStep1).commit()
    }
}