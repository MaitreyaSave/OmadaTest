package com.example.omadatest.ui.features.photos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.omadatest.data.repository.FlickrRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class PhotosViewModel @Inject constructor(
    private val repository: FlickrRepository
) : ViewModel() {


    private val _uiState =
        MutableStateFlow<PhotosUiState>(PhotosUiState.Loading)
    val uiState: StateFlow<PhotosUiState> = _uiState

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query

    private var currentPage = 1
    private var isRequestInFlight = false
    private var canLoadMore = true
    private var firstTimeLoadingExperience = true

    init {
        loadPhotos(query = "")
    }

    fun onQueryChange(newQuery: String) {
        _query.value = newQuery
    }

    fun search() {
        loadPhotos(_query.value)
    }

    fun clearQuery() {
        _query.value = ""
        loadPhotos("")
    }


    private fun loadPhotos(query: String) {
        if (isRequestInFlight) return

        _query.value = query
        currentPage = 1
        canLoadMore = true

        viewModelScope.launch {
            isRequestInFlight = true
            _uiState.value = PhotosUiState.Loading

            // Force delay to show loading state first time
            if (firstTimeLoadingExperience) {
                kotlinx.coroutines.delay(2000)
                firstTimeLoadingExperience = false
            }


            runCatching {
                repository.getPhotos(
                    query = query,
                    page = currentPage
                )
            }.onSuccess { photos ->
                canLoadMore = photos.isNotEmpty()

                _uiState.value = PhotosUiState.Success(
                    photos = photos,
                    isLoadingMore = false,
                    canLoadMore = canLoadMore
                )
            }.onFailure { throwable ->
                _uiState.value = PhotosUiState.Error(
                    message = throwable.message ?: "Something went wrong"
                )
            }

            isRequestInFlight = false
        }
    }

    fun loadNextPage() {
        val currentState = _uiState.value
        if (currentState !is PhotosUiState.Success) return
        if (!canLoadMore || isRequestInFlight) return

        viewModelScope.launch {
            isRequestInFlight = true
            currentPage++

            _uiState.value = currentState.copy(
                isLoadingMore = true
            )

            runCatching {
                repository.getPhotos(
                    query = _query.value,
                    page = currentPage
                )
            }.onSuccess { newPhotos ->
                canLoadMore = newPhotos.isNotEmpty()

                _uiState.value = currentState.copy(
                    photos = currentState.photos + newPhotos,
                    isLoadingMore = false,
                    canLoadMore = canLoadMore
                )
            }.onFailure { throwable ->
                _uiState.value = PhotosUiState.Error(
                    message = throwable.message ?: "Pagination failed"
                )
            }

            isRequestInFlight = false
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