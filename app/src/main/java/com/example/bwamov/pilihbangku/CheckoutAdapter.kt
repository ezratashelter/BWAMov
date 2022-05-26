package com.example.bwamov.pilihbangku

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bwamov.R
import com.example.bwamov.model.Checkout
import java.text.NumberFormat
import java.util.*

class CheckoutAdapter(private var data: List<Checkout>,
                      private val listener: (Checkout) -> Unit)
    : RecyclerView.Adapter<CheckoutAdapter.ViewHolder>() {

    lateinit var contextAdapter : Context

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        contextAdapter = parent.context
        var inflatedView = layoutInflater.inflate(R.layout.row_item_checkout, parent, false)
        return ViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(data[position], listener, contextAdapter)
    }

    override fun getItemCount(): Int = data.size

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        private val tvKursi = view.findViewById<TextView>(R.id.tv_kursi)
        private val tvHarga = view.findViewById<TextView>(R.id.tv_harga)

        fun bindItem(data: Checkout, listener: (Checkout) -> Unit, context: Context){

            val localID = Locale ("in", "ID")
            val formatRupiah = NumberFormat.getCurrencyInstance(localID)
            tvHarga.setText(formatRupiah.format(data.harga!!.toDouble()))

            if(data.kursi!!.startsWith("Total")){
                tvKursi.setText(data.kursi)
                tvKursi.setCompoundDrawables(null, null, null, null)
            } else {
                tvKursi.setText("Seat No. "+data.kursi)
            }

            itemView.setOnClickListener{
                listener(data)
            }
        }

    }

}