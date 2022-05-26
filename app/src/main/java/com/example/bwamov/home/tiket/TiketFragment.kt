package com.example.bwamov.home.tiket

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bwamov.R
import com.example.bwamov.home.dashboard.ComingSoonAdapter
import com.example.bwamov.model.Film
import com.example.bwamov.utils.Preferences
import com.google.firebase.database.*

class TiketFragment : Fragment() {

    private lateinit var preferences: Preferences
    private lateinit var mDatabase : DatabaseReference
    private var dataaList = ArrayList<Film>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tiket, container, false)
    }

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        preferences = Preferences(context!!)
        mDatabase = FirebaseDatabase.getInstance().getReference("Film")

        var rc_tiket = view?.findViewById<RecyclerView>(R.id.rc_tiket)

        rc_tiket?.layoutManager = LinearLayoutManager(context)
        getData()

    }

    private fun getData() {
        mDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                dataaList.clear()
                for (getdataSnapshot in snapshot.children){
                    val film = getdataSnapshot.getValue(Film::class.java)
                    dataaList.add(film!!)
                }
                var rc_tiket = view?.findViewById<RecyclerView>(R.id.rc_tiket)
                rc_tiket?.adapter = ComingSoonAdapter(dataaList){
                    var intent = Intent (context, TiketActivity::class.java).putExtra("data", it)
                    startActivity(intent)
                }
                var tv_total = view?.findViewById<TextView>(R.id.tv_total)
                tv_total?.setText("${dataaList.size} Movies")
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, ""+error.message, Toast.LENGTH_LONG).show()
            }

        })
    }
}