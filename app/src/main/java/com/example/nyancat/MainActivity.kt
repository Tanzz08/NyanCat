package com.example.nyancat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import com.example.nyancat.ui.theme.NyanCatTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Ambil instance ViewModelFactory
        val factory = ViewModelFactory.getInstance(applicationContext)

        // Buat ViewModel menggunakan ViewModelProvider dan factory
        val viewModel = ViewModelProvider(this, factory)[NyanCatViewModel::class.java]

        // Set content menggunakan Compose
        setContent {
            NyanCatTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Panggil NyanCatApp dan passing ViewModel
                    NyanCatApp(viewModel = viewModel, activity = this)
                }
            }
        }
    }
}
