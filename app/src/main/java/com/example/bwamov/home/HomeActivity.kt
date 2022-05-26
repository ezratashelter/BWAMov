package com.example.bwamov.home

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.bwamov.R
import com.example.bwamov.home.dashboard.DashboardFragment
import com.example.bwamov.home.settings.SettingFragment
import com.example.bwamov.home.tiket.TiketFragment

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val fragmentHome = DashboardFragment()
        val fragmentTiket = TiketFragment()
        val fragmentSetting = SettingFragment()

        setFragment(fragmentHome)

        val iv_menu1 = findViewById<ImageView>(R.id.iv_menu1)
        val iv_menu2 = findViewById<ImageView>(R.id.iv_menu2)
        val iv_menu3 = findViewById<ImageView>(R.id.iv_menu3)

        iv_menu1.setOnClickListener {
        setFragment(fragmentHome)
            changeIcon(iv_menu1, R.drawable.ic_home_1)
            changeIcon(iv_menu2, R.drawable.ic_tiket)
            changeIcon(iv_menu3, R.drawable.ic_profile)

        }
        iv_menu2.setOnClickListener {
            setFragment(fragmentTiket)
            changeIcon(iv_menu1, R.drawable.ic_home)
            changeIcon(iv_menu2, R.drawable.ic_tiket_1)
            changeIcon(iv_menu3, R.drawable.ic_profile)

        }
        iv_menu3.setOnClickListener {
            setFragment(fragmentSetting)
            changeIcon(iv_menu1, R.drawable.ic_home)
            changeIcon(iv_menu2, R.drawable.ic_tiket)
            changeIcon(iv_menu3, R.drawable.ic_profile_1)

        }
    }

    private fun setFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransacion = fragmentManager.beginTransaction()
        fragmentTransacion.replace(R.id.layout_frame, fragment)
        fragmentTransacion.commit()
    }

    private fun changeIcon(imageView: ImageView, int: Int){
        imageView.setImageResource(int)
    }
}