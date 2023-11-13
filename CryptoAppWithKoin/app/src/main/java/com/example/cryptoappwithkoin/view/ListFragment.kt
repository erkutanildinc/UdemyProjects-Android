package com.example.cryptoappwithkoin.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cryptoappwithkoin.databinding.FragmentListBinding
import com.example.cryptoappwithkoin.model.CryptoModel
import com.example.cryptoappwithkoin.service.CryptoAPI
import com.example.cryptoappwithkoin.viewmodel.CryptoViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class ListFragment() : Fragment(), RecyclerViewAdapter.Listener {

    private var _binding : FragmentListBinding? = null
    private val binding get() = _binding!!
    private var crpytoAdapter = RecyclerViewAdapter(arrayListOf(),this)
    private val viewModel by viewModel<CryptoViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListBinding.inflate(inflater,container,false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.layoutManager = layoutManager
        //viewModel = ViewModelProvider(this).get(CryptoViewModel::class.java)

        viewModel.getDataFromApi()

        observeLiveData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(cryptoModel: CryptoModel) {
        Toast.makeText(requireContext(),"Clicked On: ${cryptoModel.currency}",Toast.LENGTH_LONG).show()
    }

    private fun observeLiveData(){
        viewModel.cryptoList.observe(viewLifecycleOwner, Observer {cryptos->
            binding.recyclerView.visibility = View.VISIBLE
            crpytoAdapter = RecyclerViewAdapter(ArrayList(cryptos.data ?: arrayListOf()),this@ListFragment)
            binding.recyclerView.adapter = crpytoAdapter
        })

        viewModel.cryptoError.observe(viewLifecycleOwner, Observer {error->
            error?.let {
                if (it.data == true){
                    binding.cryptoErrorText.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.GONE
                }
                else{
                    binding.cryptoErrorText.visibility = View.GONE
                }
            }
        })

        viewModel.cryptoLoading.observe(viewLifecycleOwner, Observer {loading->
            loading?.let {
                if(it.data == true){
                    binding.cryptoProgressBar.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.GONE
                    binding.cryptoErrorText.visibility = View.GONE
                }
                else{
                    binding.cryptoProgressBar.visibility = View.GONE
                }
            }
        })
    }
}