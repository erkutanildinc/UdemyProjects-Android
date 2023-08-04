package com.example.cryptoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptoapp.adapter.CryptoRecylerAdapter
import com.example.cryptoapp.config.APIClient
import com.example.cryptoapp.model.CryptoModel
import com.example.cryptoapp.service.CryptoService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(),CryptoRecylerAdapter.Listener{

    lateinit var cryptoService: CryptoService
    lateinit var recyclerView: RecyclerView
    private var recylerAdapter : CryptoRecylerAdapter? = null
    var cryptoList = mutableListOf<CryptoModel>()
    private var job : Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.cryptoRecylerView)

        cryptoService = APIClient.getClient().create(CryptoService::class.java)
        loadData()

        val layoutManager : RecyclerView.LayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

    }

    private fun loadData() {

        job = CoroutineScope(Dispatchers.IO).launch {
            val response = cryptoService.getCoins()

            withContext(Dispatchers.Main){
                if (response.isSuccessful) {
                    response.body()?.let { list ->
                        cryptoList = list
                        cryptoList?.let {
                            recylerAdapter = CryptoRecylerAdapter(it, this@MainActivity)
                            recyclerView.adapter = recylerAdapter
                        }
                    }
                }
            }
        }

        /*

        cryptoService.getCoins().enqueue(object : Callback<MutableList<CryptoModel>>{
            override fun onResponse(call: Call<MutableList<CryptoModel>>, response: Response<MutableList<CryptoModel>>) {
                if(response.isSuccessful){
                    response.body()?.let { list->
                        cryptoList = list
                        cryptoList?.let {
                            recylerAdapter = CryptoRecylerAdapter(it,this@MainActivity)
                            recyclerView.adapter= recylerAdapter
                        }
                    }
                }
            }

            override fun onFailure(call: Call<MutableList<CryptoModel>>, t: Throwable) {
                t.printStackTrace()
            }
        })
*/
    }

    override fun onItemClick(cryptoModel: CryptoModel) {
        Log.d("Message","Nice")
    }

    override fun onDestroy() {
        super.onDestroy()
        job?.cancel()
    }
}