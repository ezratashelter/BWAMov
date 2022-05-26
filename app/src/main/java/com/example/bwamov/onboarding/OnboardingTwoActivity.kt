package com.example.bwamov.onboarding

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.bwamov.R

class OnboardingTwoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding_two)

        val btn_home = findViewById<Button>(R.id.btn_home2)
        btn_home.setOnClickListener {
            val intent = Intent(this@OnboardingTwoActivity, OnboardingThreeActivity::class.java)
            startActivity(intent)
            finishAffinity()
        }
    }
}