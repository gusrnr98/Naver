package com.example.kotlin_naver

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kotlin_naver.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var  binding: ActivityMainBinding
    private lateinit var preferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signUp.setOnClickListener {
            preferences = getSharedPreferences("naverUserInfo", MODE_PRIVATE)
            val editor = preferences.edit()

            editor.putString("userid", binding.editId.text.toString())
            editor.putString("userpw", binding.editPw.text.toString())
            editor.putString("userpwcheck", binding.editPwcheck.text.toString())
            editor.putString("username", binding.editName.text.toString())
            editor.putString("useryear", binding.editYear.text.toString())
            editor.putString("usermonth", binding.editMonth.selectedItem.toString())
            editor.putString("userday", binding.editDay.text.toString())
            editor.putString("usersex", binding.editSex.selectedItem.toString())
            editor.putString("useremail", binding.editEmail.text.toString())
            editor.putString("usercode", binding.editCode.selectedItem.toString())
            editor.putString("userphone", binding.editPhone.text.toString())
            editor.commit()

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}