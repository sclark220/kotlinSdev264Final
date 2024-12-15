package com.example.sdev264finalproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sdev264finalproject.ui.theme.Sdev264FinalProjectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            Sdev264FinalProjectTheme(){
                val viewModel = viewModel<GameViewModel>()
                val state by viewModel.state.collectAsStateWithLifecycle()
                GameScreen(state = state,  onEvent = viewModel::onEvent)
            }
        }
    }
}