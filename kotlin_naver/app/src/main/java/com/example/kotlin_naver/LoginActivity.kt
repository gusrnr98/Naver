package com.example.kotlin_naver

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.kotlin_naver.databinding.ActivityLoginBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener

class LoginActivity : AppCompatActivity(), loginFragment1.OnDataPassListener, login2.OnDataPassListener {
    private lateinit var binding: ActivityLoginBinding
    lateinit var preferences: SharedPreferences
    var userid = ""
    var userpw=""
    var userphone=""
    var position: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragmentManager = supportFragmentManager
        val fragment1: Fragment = loginFragment1()
        val fragment2: Fragment = login2()
        val fragment3: Fragment = loginFragment3()
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        super.onCreate(savedInstanceState)

        preferences =getSharedPreferences("naverUserInfo", MODE_PRIVATE)
        transaction.replace(R.id.frameLayout,fragment1).commit()

        binding.tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val transaction: FragmentTransaction = fragmentManager.beginTransaction()
                position = tab.position
                var select: Fragment = fragment1
                if(position==0){
                    select = fragment1
                }
                else if(position==1){
                    select = fragment2
                }
                else if(position==2){
                    select = fragment3
                }
                transaction.replace(R.id.frameLayout,select).commit()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })

        userid = preferences.getString("userid", "")!!
        userpw = preferences.getString("userpw","")!!
        userphone = preferences.getString("userphone","")!!

        binding.textViewSignup.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }
    override fun onDataPass(id: String, pw: String) {
        if(id.equals(userid) && pw.equals(userpw)){
            val builder = AlertDialog.Builder(this)

            builder.setTitle("로그인성공")
            builder.setMessage("입력아이디 : ${id}  저장된 아이디 ${userid} \n입력비밀번호 : ${pw}  저장된 비밀번호 : ${userpw}")
            val toast = Toast.makeText(applicationContext, "성공", Toast.LENGTH_SHORT)

            toast.show()
            builder.show()
        }
        else{
            val builder = AlertDialog.Builder(this)

            builder.setTitle("로그인실패")
            builder.setMessage("아이디와 비밀번호를 다시 확인하세요")

            builder.show()
        }
    }
    override fun onDataPassF2(phone: String) {
        if(phone.equals(userphone)){
            val builder = AlertDialog.Builder(this)

            builder.setTitle("로그인성공")
            builder.setMessage("입력 번호 : ${phone}  저장된 번호 ${userphone}")
            val toast = Toast.makeText(applicationContext, "성공", Toast.LENGTH_SHORT)

            toast.show()
            builder.show()
        }
        else{
            val builder = AlertDialog.Builder(this)

            builder.setTitle("로그인실패")
            builder.setMessage("번호를 다시 확인하세요")

            builder.show()
        }
    }
}