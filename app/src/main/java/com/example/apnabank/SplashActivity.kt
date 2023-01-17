package com.example.apnabank

import android.animation.Animator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.example.apnabank.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
    private var binding : ActivitySplashBinding?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)

        setContentView(binding?.root)
        val animateBounce = AnimationUtils.loadAnimation(this,R.anim.bounce)
//        animateBounce.repeatCount = 4
//        animateBounce.repeatMode =  Animation.RESTART
        val launchIconIv = binding?.launchIcon
        val launchTv = binding?.launchTv
        launchIconIv?.startAnimation(animateBounce)
        launchTv?.startAnimation(animateBounce)
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        },3000)
    }
}