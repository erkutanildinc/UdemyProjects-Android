package com.example.kotlinflow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kotlinflow.ui.theme.KotlinFlowTheme
import kotlinx.coroutines.flow.collect

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KotlinFlowTheme {
                // A surface container using the 'background' color from the theme
                val viewModel : MyViewModel by viewModels()
                SecondScreen(viewModel = viewModel)
            }
        }
    }


    @Composable
    fun FirstScreen(viewModel: MyViewModel) {
        val counter = viewModel.countDownTimerFLow.collectAsState(initial = 10)

    }

    @Composable
    fun SecondScreen(viewModel: MyViewModel) {
        //val liveDataValue = viewModel.liveData.observeAsState()
        val stateFlowValue =  viewModel.stateFlow.collectAsState()
        val sharedFlowValue = viewModel.sharedFlow.collectAsState(initial = "")

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Box(modifier = Modifier.fillMaxSize()){
                Column(modifier = Modifier.align(Alignment.Center)) {
                    Text(text = stateFlowValue.value)
                    Button(onClick = {
                        viewModel.changeStateFlow()
                    }) {
                        Text(text = "State Flow Button")
                    }
                    
                    Spacer(modifier = Modifier.padding(10.dp))

                    Text(text = sharedFlowValue.value)
                    Button(onClick = {
                        viewModel.changeSharedFlow()
                    }) {
                        Text(text = "Shared Flow Button")
                    }
                }
            }

            
        }
    }




}

