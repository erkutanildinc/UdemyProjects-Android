package com.example.kotlinflow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
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
                val counter = viewModel.countDownTimerFLow.collectAsState(initial = 10)
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Box(modifier = Modifier.fillMaxSize()){
                        Text(text = counter.value.toString(), fontSize = 26.sp, textAlign = TextAlign.Center, modifier = Modifier.align(Alignment.Center))
                    }
                }
            }
        }
    }
}
