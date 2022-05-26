package com.example.bwamov

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bwamov.home.dashboard.PlaysAdapter
import com.example.bwamov.model.Film
import com.example.bwamov.model.Plays
import com.example.bwamov.pilihbangku.PilihBangkuActivity
import com.google.firebase.database.*

class DetailActivity : AppCompatActivity() {

    private lateinit var mDatabase : DatabaseReference
    private var dataList = ArrayList<Plays>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val data = intent.getParcelableExtra<Film>("data")

        mDatabase = FirebaseDatabase.getInstance().getReference("Film")
            .child(data?.judul.toString())
            .child("play")

        val tv_kursi = findViewById<TextView>(R.id.tv_kursi)
        val tv_genre = findViewById<TextView>(R.id.tv_genre)
        val tv_desc = findViewById<TextView>(R.id.tv_desc)
        val tv_rate = findViewById<TextView>(R.id.tv_rate1)
        val iv_poster = findViewById<ImageView>(R.id.iv_poster)
        val rv_who_played = findViewById<RecyclerView>(R.id.rv_who_played)

        tv_kursi.text = data?.judul
        tv_genre.text = data?.genre
        tv_desc.text = data?.desc
        tv_rate.text = data?.rating

        Glide.with(this)
            .load(data?.poster)
            .into(iv_poster)

        rv_who_played.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        getData()

        val btn_pilih_bangku = findViewById<Button>(R.id.btn_pilih_bangku)
        btn_pilih_bangku.setOnClickListener {
            var intent = Intent(this@DetailActivity, PilihBangkuActivity::class.java).putExtra("data",data)
            startActivity(intent)
        }
    }

    private fun getData() {
        mDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                dataList.clear()

                for (getdataSnapshot in p0.children){
                    var Film = getdataSnapshot.getValue(Plays::class.java)
                    dataList.add(Film!!)
                }
                val rv_who_played = findViewById<RecyclerView>(R.id.rv_who_played)
                rv_who_played.adapter = PlaysAdapter(dataList){

                }
            }

            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(this@DetailActivity, ""+p0.message, Toast.LENGTH_LONG).show()
            }

        })
    }
}