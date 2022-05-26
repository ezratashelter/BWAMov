package com.example.bwamov.onboarding

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.bwamov.R
import com.example.bwamov.sign.signin.SignInActivity

class OnboardingThreeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding_three)

        val btn_home = findViewById<Button>(R.id.btn_home3)
        btn_home.setOnClickListener {
            val intent = Intent(this@OnboardingThreeActivity, SignInActivity::class.java)
            startActivity(intent)
            finishAffinity()
        }
    }
}