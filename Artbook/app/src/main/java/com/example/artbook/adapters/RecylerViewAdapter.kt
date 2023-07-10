package com.example.artbook.adapters

import android.content.Intent
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.artbook.ArtAddActivity
import com.example.artbook.Room.ArtModel
import com.example.artbook.databinding.RecylerviewRowItemBinding

class RecylerViewAdapter(val artList : MutableList<ArtModel>) : RecyclerView.Adapter<RecylerViewAdapter.ArtHolder>() {
    class ArtHolder(val binding : RecylerviewRowItemBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtHolder {
        val binding = RecylerviewRowItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ArtHolder(binding)
    }

    override fun getItemCount(): Int {
        return artList.size
    }

    override fun onBindViewHolder(holder: ArtHolder, position: Int) {
        holder.binding.recylerRowItemNameText.text = artList.get(position).artName
        val byteArray = artList.get(position).artImage
        val bitmap = BitmapFactory.decodeByteArray(byteArray,0,byteArray.size)
        holder.binding.recylerRowImageView.setImageBitmap(bitmap)

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context,ArtAddActivity::class.java)
            intent.putExtra("info","old")
            intent.putExtra("nid",artList.get(position).nid)
            holder.itemView.context.startActivity(intent)
        }
    }
}