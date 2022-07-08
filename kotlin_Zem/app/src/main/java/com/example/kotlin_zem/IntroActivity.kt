package com.example.kotlin_zem

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget
import com.example.kotlin_zem.databinding.ActivityIntroBinding

class IntroActivity : AppCompatActivity() {
    private lateinit var binding: ActivityIntroBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val gifImage = GlideDrawableImageViewTarget(binding.imageView)
        Glide.with(this).load(R.drawable.zem123).into(gifImage)

        var handler = Handler()
        handler.postDelayed({
            var intent = Intent( this, MainActivity::class.java)
            startActivity(intent)
        },2000)
    }
}