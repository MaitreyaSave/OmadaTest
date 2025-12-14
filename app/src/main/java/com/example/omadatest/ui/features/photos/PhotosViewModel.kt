package com.example.omadatest.ui.features.photos

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.omadatest.data.FlickrRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class PhotosViewModel @Inject constructor(
    private val repository: FlickrRepository
) : ViewModel() {


    init {
        loadPhotos()
    }

    fun loadPhotos() {
        viewModelScope.launch {
            val list = repository.getPhotos("")
            Log.d("PhotosVM", "list size: ${list.size}")
        }
    }

}

class PhotosViewModelFactory @Inject constructor(
    private val repository: FlickrRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PhotosViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PhotosViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}