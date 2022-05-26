package com.example.bwamov.home.dashboard

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.bwamov.DetailActivity
import com.example.bwamov.R
import com.example.bwamov.model.Film
import com.example.bwamov.utils.Preferences
import com.google.firebase.database.*
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class DashboardFragment : Fragment() {

    private lateinit var preferences: Preferences
    private lateinit var mDatabase: DatabaseReference

    private val dataList = ArrayList<Film>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val tv_nama = view!!.findViewById<TextView>(R.id.tv_nama)
        val tv_saldo = view!!.findViewById<TextView>(R.id.tv_saldo)
        val iv_profile = view!!.findViewById<ImageView>(R.id.iv_profile)
        val rv_now_playing = view!!.findViewById<RecyclerView>(R.id.rv_now_playing)
        val rv_coming_soon = view!!.findViewById<RecyclerView>(R.id.rv_coming_soon)

        preferences = Preferences(activity!!.applicationContext)
        mDatabase = FirebaseDatabase.getInstance().getReference("Film")

        tv_nama.setText(preferences.getValues("nama"))
        if (preferences.getValues("saldo").equals("")){
            currency(preferences.getValues("saldo")!!.toDouble(), tv_saldo)
        }

        Glide.with(this)
            .load(preferences.getValues("url"))
            .apply(RequestOptions.circleCropTransform())
            .into(iv_profile)

        rv_now_playing.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rv_coming_soon.layoutManager = LinearLayoutManager(context)
        getData()
    }

    private fun getData() {
        mDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataList.clear()
                for (getdataSnapshot in dataSnapshot.children){
                    var film = getdataSnapshot.getValue(Film::class.java)
                    dataList.add(film!!)
                }
                val rv_now_playing = view!!.findViewById<RecyclerView>(R.id.rv_now_playing)
                rv_now_playing.adapter = NowPlayingAdapter(dataList){
                    var intent = Intent(context, DetailActivity::class.java).putExtra("data", it)
                    startActivity(intent)
                }
                val rv_coming_soon = view!!.findViewById<RecyclerView>(R.id.rv_coming_soon)
                rv_coming_soon.adapter = ComingSoonAdapter(dataList){
                    var intent = Intent(context, DetailActivity::class.java).putExtra("data", it)
                    startActivity(intent)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(context, ""+databaseError.message, Toast.LENGTH_LONG).show()
            }

        })
    }

    private fun currency(harga: Double, textView: TextView){
        val localID = Locale ("in","ID")
        val formatRupiah = NumberFormat.getCurrencyInstance(localID)
        textView.setText(formatRupiah.format(harga))
    }

}