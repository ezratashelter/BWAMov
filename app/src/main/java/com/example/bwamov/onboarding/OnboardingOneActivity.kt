package com.example.bwamov.onboarding

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.bwamov.R
import com.example.bwamov.sign.signin.SignInActivity
import com.example.bwamov.utils.Preferences

class OnboardingOneActivity : AppCompatActivity() {

    lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding_one)

        preferences = Preferences(this)

        if (preferences.getValues("onboarding").equals("1")){
            finishAffinity()

            val intent = Intent(this@OnboardingOneActivity, SignInActivity::class.java)
            startActivity(intent)
        }
        val btn_home = findViewById<Button>(R.id.btn_home)
        btn_home.setOnClickListener {
            val intent = Intent(this@OnboardingOneActivity, OnboardingTwoActivity::class.java)
            startActivity(intent)
        }
        val btn_daftar = findViewById<Button>(R.id.btn_daftar)
        btn_daftar.setOnClickListener {
            preferences.setValues("onboarding", "1")
            finishAffinity()

            val intent = Intent(this@OnboardingOneActivity, SignInActivity::class.java)
            startActivity(intent)
        }
    }
}