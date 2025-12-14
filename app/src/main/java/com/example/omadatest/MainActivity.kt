package com.example.omadatest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.example.omadatest.app.OmadaApplication
import com.example.omadatest.ui.features.photos.PhotosScreen
import com.example.omadatest.ui.features.photos.PhotosViewModel
import com.example.omadatest.ui.features.photos.PhotosViewModelFactory
import com.example.omadatest.ui.theme.OmadaTestTheme
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var viewModelFactory: PhotosViewModelFactory

    private lateinit var viewModel: PhotosViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        (application as OmadaApplication).appComponent.inject(this)
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this, viewModelFactory)[PhotosViewModel::class.java]

        enableEdgeToEdge()
        setContent {
            OmadaTestTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    PhotosScreen(
                        viewModel = viewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

