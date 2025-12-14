package com.example.omadatest.data

import com.example.omadatest.network.FlickrApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FlickrRepository @Inject constructor(
    private val api: FlickrApiService
) {

    suspend fun getPhotos(query: String?, page: Int = 1): List<FlickrPhoto> {
        return try {
            val response = if (query.isNullOrBlank()) {
                api.getRecentPhotos(page = page)
            } else {
                api.searchPhotos(query = query, page = page)
            }
            response.photos.photo
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}