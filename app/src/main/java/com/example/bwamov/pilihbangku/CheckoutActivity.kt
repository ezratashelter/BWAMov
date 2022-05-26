package com.example.bwamov.pilihbangku

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Color.RED
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bwamov.R
import com.example.bwamov.home.tiket.TiketActivity
import com.example.bwamov.model.Checkout
import com.example.bwamov.model.Film
import com.example.bwamov.utils.Preferences

class CheckoutActivity : AppCompatActivity() {

    private var dataList = ArrayList<Checkout>()
    private var total:Int = 0
    private lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        preferences = Preferences(this)
        dataList = intent.getSerializableExtra("data") as ArrayList<Checkout>
        val data = intent.getParcelableExtra<Film>("datas")

        for (a in dataList.indices){
            total += dataList[a].harga!!.toInt()
        }
        dataList.add(Checkout("Total harus dibayar", total.toString()))



        val rv_checkout = findViewById<RecyclerView>(R.id.rv_checkout1)
        rv_checkout.layoutManager= LinearLayoutManager(this)
        rv_checkout.adapter = CheckoutAdapter(dataList){

        }
        val btn_tiket = findViewById<Button>(R.id.btn_tiket)
        btn_tiket.setOnClickListener {
            var intent = Intent(this, CheckoutSuccessActivity::class.java)
            startActivity(intent)

            showNotif(data!!)
        }

    }

    private fun showNotif(datas: Film) {
        val NOTIFICATION_CHANNEL_ID = "channel_bwa_notif"
        val context = this.applicationContext
        var notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O){
            val  channelName = "BWAMOV Notif Channel"
            val importance = NotificationManager.IMPORTANCE_HIGH

            val mChannel = NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, importance)
            notificationManager.createNotificationChannel(mChannel)
        }

        val mIntent = Intent(this, TiketActivity::class.java)
        val bundle = Bundle()
        bundle.putParcelable("id", datas)
        mIntent.putExtras(bundle)

        val pendingIntent = PendingIntent.getActivity(this, 0, mIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val builder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
        builder.setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.ic_logo_mov)
            .setLargeIcon(
                BitmapFactory.decodeResource(
                    this.resources,
                    R.drawable.ic_icon_notif
                )
            )
            .setTicker("Notif bwa starting")
            .setAutoCancel(true)
            .setVibrate(longArrayOf(1000,1000,1000,1000,1000))
            .setLights(Color.RED, 3000,3000)
            .setDefaults(Notification.DEFAULT_SOUND)
            .setContentTitle("Pembayaran Telah di Terima")
            .setContentText("Tiket "+datas.judul+" berhasil kamu dapatkan. Enjoy the movie!")

        notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(115, builder.build())
    }
}