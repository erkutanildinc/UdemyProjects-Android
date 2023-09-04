package com.example.countriesapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.example.countriesapp.view.CountryDetailFragmentArgs
import com.example.countriesapp.R
import com.example.countriesapp.viewmodel.CountryDetailViewModel


class CountryDetailFragment : Fragment() {

    private var countryUuid = 0

    private lateinit var viewModel : CountryDetailViewModel
    private lateinit var countryNameTextView : TextView
    private lateinit var countryCapitalTextView : TextView
    private lateinit var countryRegionTextView : TextView
    private lateinit var countryCurrencyTextView : TextView
    private lateinit var countryLanguageTextView : TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_country_detail, container, false)

        countryNameTextView = view.findViewById(R.id.countryDetailNameTextView)
        countryCapitalTextView = view.findViewById(R.id.countryDetailCapitalTextView)
        countryCurrencyTextView = view.findViewById(R.id.countryDetailCurrencyTextView)
        countryRegionTextView = view.findViewById(R.id.countryDetailRegionTextView)
        countryLanguageTextView = view.findViewById(R.id.countryDetailLanguageTextView)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(CountryDetailViewModel::class.java)
        viewModel.getDataFromRoom()

        arguments?.let {
            countryUuid = CountryDetailFragmentArgs.fromBundle(it).countryUUID
        }

        observeLiveData()
    }

    fun observeLiveData(){

        viewModel.countryLiveData.observe(viewLifecycleOwner, Observer {country->
            country?.let {
                countryNameTextView.text = country.countryName
                countryCapitalTextView.text = country.countryCapital
                countryCurrencyTextView.text = country.countryCurrency
                countryRegionTextView.text = country.countryRegion
                countryLanguageTextView.text = country.countryLanguage
            }
        })
    }
}