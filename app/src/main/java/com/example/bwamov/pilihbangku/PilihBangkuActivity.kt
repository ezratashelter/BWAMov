package com.example.bwamov.pilihbangku

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.bwamov.R
import com.example.bwamov.model.Checkout
import com.example.bwamov.model.Film

class PilihBangkuActivity : AppCompatActivity() {

    var statusA3:Boolean = false
    var statusA4:Boolean = false
    var total:Int = 0

    private var dataList = ArrayList<Checkout>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pilih_bangku)

        val data = intent.getParcelableExtra<Film>("data")
        val tv_kursi = findViewById<TextView>(R.id.tv_kursi)
        tv_kursi.text = data?.judul

        val a3 = findViewById<ImageView>(R.id.a3)

        a3.setOnClickListener {
            if (statusA3) {
                a3.setImageResource(R.drawable.ic_empty)
                statusA3 = false
                total -= 1
                beliTIket(total)
            } else {
                a3.setImageResource(R.drawable.ic_selected)
                statusA3 = true
                total += 1
                beliTIket(total)

                val data = Checkout("A3", "35000")
                dataList.add(data)
            }
        }
        val a4 = findViewById<ImageView>(R.id.a4)
        a4.setOnClickListener {
            if (statusA4) {
                a4.setImageResource(R.drawable.ic_empty)
                statusA4 = false
                total -= 1
                beliTIket(total)
            } else {
                a4.setImageResource(R.drawable.ic_selected)
                statusA4 = true
                total += 1
                beliTIket(total)

                val data = Checkout("A4", "35000")
                dataList.add(data)
            }
        }
        val btn_beli_tiket = findViewById<Button>(R.id.btn_beli_tiket)
        btn_beli_tiket.setOnClickListener {
            var intent = Intent (this@PilihBangkuActivity, CheckoutActivity::class.java).putExtra("data", dataList).putExtra("datas", data)
            startActivity(intent)
        }


    }

    private fun beliTIket(total: Int) {
        if (total == 0){
            val btn_beli_tiket = findViewById<Button>(R.id.btn_beli_tiket)
            btn_beli_tiket.setText("Beli TIket")
            btn_beli_tiket.visibility = View.INVISIBLE
        } else{
            val btn_beli_tiket = findViewById<Button>(R.id.btn_beli_tiket)
            btn_beli_tiket.setText("Beli TIket ("+total+")")
            btn_beli_tiket.visibility = View.VISIBLE
        }
    }
}