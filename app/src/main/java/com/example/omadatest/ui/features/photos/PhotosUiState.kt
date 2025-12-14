package com.example.omadatest.ui.features.photos

import com.example.omadatest.data.Photo

sealed class PhotosUiState {
    object Loading : PhotosUiState()

    data class Success(
        val photos: List<Photo>,
        val isLoadingMore: Boolean,
        val canLoadMore: Boolean
    ) : PhotosUiState()

    data class Error(
        val message: String
    ) : PhotosUiState()
}



