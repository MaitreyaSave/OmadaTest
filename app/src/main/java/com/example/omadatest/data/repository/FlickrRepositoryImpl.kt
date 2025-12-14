package com.example.omadatest.data.repository

import com.example.omadatest.data.Photo
import com.example.omadatest.data.mapper.toDomain
import com.example.omadatest.data.network.FlickrApiService
import javax.inject.Inject

class FlickrRepositoryImpl @Inject constructor(
    private val apiService: FlickrApiService
) : FlickrRepository {

    override suspend fun getPhotos(query: String, page: Int): List<Photo> {
        val response = if (query.isBlank()) {
            apiService.getRecentPhotos(page = page)
        } else {
            apiService.searchPhotos(query = query, page = page)
        }
        return response.photos.photo.map { it.toDomain() }
    }
}