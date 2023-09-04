package com.example.countriesapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.countriesapp.R
import com.example.countriesapp.model.Country
import com.example.countriesapp.util.downloadFromUrl
import com.example.countriesapp.util.placeHolderProgressBar
import com.example.countriesapp.view.CountryListFragment
import com.example.countriesapp.view.CountryListFragmentDirections

class CountryAdapter(val list : MutableList<Country>) : RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {

    lateinit var countryName : TextView
    lateinit var countryRegion : TextView
    lateinit var countryFlag : ImageView

    class CountryViewHolder(var view: View) : RecyclerView.ViewHolder(view) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.country_row_item,parent,false)
        return CountryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        countryName = holder.view.findViewById(R.id.countryNameTextView)
        countryRegion = holder.view.findViewById(R.id.countryRegionTextView)
        countryFlag = holder.view.findViewById(R.id.countryFlagImage)

        countryName.text = list.get(position).countryName
        countryRegion.text = list.get(position).countryRegion
        countryFlag.downloadFromUrl(list.get(position).countryFlagUrl!!, placeHolderProgressBar(holder.view.context))

        holder.view.setOnClickListener {
            val action = CountryListFragmentDirections.actionCountryListFragmentToCountryDetailFragment()
            Navigation.findNavController(it).navigate(action)
        }
    }

    fun updateCountryList(newCountryList : MutableList<Country>){
        list.clear()
        list.addAll(newCountryList)
        notifyDataSetChanged()
    }

}