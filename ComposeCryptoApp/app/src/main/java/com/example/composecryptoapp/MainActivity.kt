package com.example.composecryptoapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composecryptoapp.configs.APIClient
import com.example.composecryptoapp.model.CryptoModel
import com.example.composecryptoapp.service.CryptoService
import com.example.composecryptoapp.ui.theme.ComposeCryptoAppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeCryptoAppTheme {
                MainScreen()
            }
        }
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun MainScreen(){

     var job : Job? = null
     val cryptoService: CryptoService = APIClient.getClient().create(CryptoService::class.java)
     var cryptoList = remember { mutableStateListOf<CryptoModel>() }

    job = CoroutineScope(Dispatchers.IO).launch {
        val response = cryptoService.getCoins()
        withContext(Dispatchers.Main){
            if(response.isSuccessful){
                response.body()?.let {
                    cryptoList.addAll(it)
                }
            }
        }
    }

    LazyCryptoList(cryptoList = cryptoList)
}

@Composable
fun LazyCryptoList(cryptoList : MutableList<CryptoModel>){
    LazyColumn(contentPadding = PaddingValues(5.dp)){
        items(cryptoList) {crypto ->
            CryptoRow(crypto = crypto)
        }
    }
}

@Composable
fun CryptoRow(crypto : CryptoModel){
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = crypto.currency, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 10.dp))
        Text(text = crypto.price, fontStyle = FontStyle.Italic, modifier = Modifier.padding(horizontal = 10.dp))
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeCryptoAppTheme {
        MainScreen()
        CryptoRow(crypto = CryptoModel("Btc", price = "1500"))
    }
}