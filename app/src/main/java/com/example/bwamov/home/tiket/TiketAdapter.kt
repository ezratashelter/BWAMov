package com.example.bwamov.home.tiket

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bwamov.R
import com.example.bwamov.model.Checkout

class TiketAdapter(private var data: List<Checkout>,
                   private val listener: (Checkout) -> Unit)
    : RecyclerView.Adapter<TiketAdapter.ViewHolder>() {

    lateinit var contextAdapter : Context

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        contextAdapter = parent.context
        var inflatedView = layoutInflater.inflate(R.layout.row_item_checkout_white, parent, false)
        return ViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(data[position], listener, contextAdapter)
    }

    override fun getItemCount(): Int = data.size

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        private val tvTitle = view.findViewById<TextView>(R.id.tv_kursi)

        fun bindItem(data: Checkout, listener: (Checkout) -> Unit, context: Context){
            tvTitle.setText("Seat No. "+data.kursi)

            itemView.setOnClickListener{
                listener(data)
            }
        }

    }

}