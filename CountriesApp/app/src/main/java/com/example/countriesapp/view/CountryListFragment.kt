package com.example.countriesapp.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.countriesapp.R
import com.example.countriesapp.adapter.CountryAdapter
import com.example.countriesapp.viewmodel.CountryListViewModel

class CountryListFragment : Fragment() {

    private lateinit var countryListViewModel : CountryListViewModel
    private lateinit var countryRecylerView : RecyclerView
    private lateinit var countryErrorMess : TextView
    private lateinit var countryLoadingBar : ProgressBar
    private lateinit var swipeRefresh : SwipeRefreshLayout
    private val countryAdapter = CountryAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_country_list, container, false)
        countryRecylerView = view.findViewById<RecyclerView>(R.id.countryRecylerView)
        countryErrorMess = view.findViewById(R.id.countryErrorMessage)
        countryLoadingBar = view.findViewById(R.id.countryListProgressBar)
        swipeRefresh = view.findViewById(R.id.swipeRefreshLayout)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        countryListViewModel = ViewModelProvider(this).get(CountryListViewModel::class.java)
        countryListViewModel.refreshData()

        countryRecylerView.layoutManager = LinearLayoutManager(context)
        countryRecylerView.adapter = countryAdapter

        swipeRefresh.setOnRefreshListener {
            countryRecylerView.visibility = View.GONE
            countryErrorMess.visibility = View.GONE
            countryLoadingBar.visibility = View.VISIBLE
            countryListViewModel.refreshFromAPI()
            swipeRefresh.isRefreshing = false
        }

        observeLiveData()

    }

    fun observeLiveData(){
        countryListViewModel.countries.observe(viewLifecycleOwner, Observer { countries ->
            countries?.let {
                countryRecylerView.visibility = View.VISIBLE
                countryAdapter.updateCountryList(countries)
            }
        })

        countryListViewModel.countryError.observe(viewLifecycleOwner,Observer{error ->
            error?.let {
                if(error){
                    countryErrorMess.visibility = View.VISIBLE
                }
                else{
                    countryErrorMess.visibility = View.INVISIBLE
                }
            }

        })

        countryListViewModel.countryLoading.observe(viewLifecycleOwner, Observer {loading ->
            loading?.let {
                if(loading){
                    countryLoadingBar.visibility = View.VISIBLE
                    countryRecylerView.visibility = View.GONE
                    countryErrorMess.visibility = View.GONE
                }
                else{
                    countryLoadingBar.visibility = View.GONE
                }
            }
        })


    }

}
