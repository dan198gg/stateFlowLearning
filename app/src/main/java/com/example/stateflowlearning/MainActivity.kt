package com.example.stateflowlearning

import android.os.Bundle
import android.text.Layout.Alignment
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stateflowlearning.ui.theme.StateFlowLearningTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect

class MainActivity : ComponentActivity() {
    val msf= MutableStateFlow(0)
    val sf=msf.asStateFlow()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Main(flow = sf, mutableStateFlow = msf)
        }
    }
}

@Composable
fun Main(flow:StateFlow<Int>,mutableStateFlow: MutableStateFlow<Int>){
    val count by flow.collectAsState()
    val messages = remember {
        mutableStateListOf<Int>()
    }
    LaunchedEffect(key1 = "") {
        flow.collect{
            messages.add(it)
        }
    }
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "$count", fontSize = 25.sp, modifier = Modifier)
        Button(onClick = {mutableStateFlow.value+=1}) {
            Text(text = "Increase", fontSize = 12.sp)
        }
        LazyColumn {
            items(messages){
                Text(text = "$it-collected", modifier = Modifier.padding(0.dp,50.dp))
            }
        }
    }
}