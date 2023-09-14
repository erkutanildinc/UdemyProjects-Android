package com.example.countriesapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.example.countriesapp.view.CountryDetailFragmentArgs
import com.example.countriesapp.R
import com.example.countriesapp.databinding.FragmentCountryDetailBinding
import com.example.countriesapp.util.downloadFromUrl
import com.example.countriesapp.util.placeHolderProgressBar
import com.example.countriesapp.viewmodel.CountryDetailViewModel


class CountryDetailFragment : Fragment() {

    private var countryUuid = 0
    private lateinit var dataBinding : FragmentCountryDetailBinding

    private lateinit var viewModel : CountryDetailViewModel
    private lateinit var countryNameTextView : TextView
    private lateinit var countryCapitalTextView : TextView
    private lateinit var countryRegionTextView : TextView
    private lateinit var countryCurrencyTextView : TextView
    private lateinit var countryLanguageTextView : TextView
    private lateinit var countryImageView : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dataBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_country_detail,container,false)
/*
        countryNameTextView = view.findViewById(R.id.countryDetailNameTextView)
        countryCapitalTextView = view.findViewById(R.id.countryDetailCapitalTextView)
        countryCurrencyTextView = view.findViewById(R.id.countryDetailCurrencyTextView)
        countryRegionTextView = view.findViewById(R.id.countryDetailRegionTextView)
        countryLanguageTextView = view.findViewById(R.id.countryDetailLanguageTextView)
        countryImageView = view.findViewById(R.id.countryDetailmageView)
*/
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            countryUuid = CountryDetailFragmentArgs.fromBundle(it).countryUUID
        }

        viewModel = ViewModelProvider(this).get(CountryDetailViewModel::class.java)
        viewModel.getDataFromRoom(countryUuid)

        observeLiveData()
    }

    fun observeLiveData(){

        viewModel.countryLiveData.observe(viewLifecycleOwner, Observer {country->
            country?.let {
                dataBinding.selectedCountry = it
                /*
                countryNameTextView.text = country.countryName
                countryCapitalTextView.text = "Capital: " + country.countryCapital
                countryCurrencyTextView.text = "Currency: "+ country.countryCurrency
                countryRegionTextView.text = "Region: "+country.countryRegion
                countryLanguageTextView.text = "Language: "+ country.countryLanguage
                context?.let {
                    countryImageView.downloadFromUrl(country.imageUrl!!, placeHolderProgressBar(it))
                }
                */

            }
        })
    }
}