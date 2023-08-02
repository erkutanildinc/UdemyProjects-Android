package com.example.cryptoapp.adapter

import android.location.GnssAntennaInfo.Listener
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptoapp.R
import com.example.cryptoapp.model.CryptoModel

class CryptoRecylerAdapter(private val cryptoList : MutableList<CryptoModel>,private val listener : Listener) : RecyclerView.Adapter<CryptoRecylerAdapter.RowHolder>() {

    interface Listener{
        fun onItemClick(cryptoModel : CryptoModel)
    }

    class RowHolder(view : View) : RecyclerView.ViewHolder(view){
        fun bind(cryptoModel: CryptoModel,listener : Listener){
            var cryptoName = itemView.findViewById<TextView>(R.id.cryptoNameTextView)
            var cryptoPrice = itemView.findViewById<TextView>(R.id.cryptoPriceTextView)
            cryptoName.text = cryptoModel.currency
            cryptoPrice.text = cryptoModel.price

            itemView.setOnClickListener {
                listener.onItemClick(cryptoModel)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recylerview_layout,parent,false)
        return RowHolder(view)
    }

    override fun getItemCount(): Int {
        return cryptoList.size
    }

    override fun onBindViewHolder(holder: RowHolder, position: Int) {
        holder.bind(cryptoList[position],listener)
    }


}