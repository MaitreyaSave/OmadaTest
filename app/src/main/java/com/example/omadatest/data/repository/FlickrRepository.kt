package com.example.omadatest.data.repository

import com.example.omadatest.data.Photo

interface FlickrRepository {
    suspend fun getPhotos(query: String, page: Int): List<Photo>
}
