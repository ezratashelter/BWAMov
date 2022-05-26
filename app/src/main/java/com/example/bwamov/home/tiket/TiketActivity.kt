package com.example.bwamov.home.tiket

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bwamov.R
import com.example.bwamov.model.Checkout
import com.example.bwamov.model.Film

class TiketActivity : AppCompatActivity() {

    private var dataList = ArrayList<Checkout>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tiket)

        var data = intent.getParcelableExtra<Film>("data")

        val tv_title = findViewById<TextView>(R.id.tv_title4)
        val tv_genre = findViewById<TextView>(R.id.tv_genre1)
        val tv_rate = findViewById<TextView>(R.id.tv_rate2)
        val iv_poster_image = findViewById<ImageView>(R.id.iv_poster_image1)
        val rc_checkout = findViewById<RecyclerView>(R.id.rv_checkout1)


        tv_title.text = data?.judul
        tv_genre.text = data?.genre
        tv_rate.text = data?.rating

        Glide.with(this)
            .load(data?.poster)
            .into(iv_poster_image)

        rc_checkout.layoutManager = LinearLayoutManager(this)
        dataList.add(Checkout("C1", ""))
        dataList.add(Checkout("C2", ""))

        rc_checkout.adapter = TiketAdapter(dataList){

        }
    }
}