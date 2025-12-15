package com.example.omadatest.data.network

import com.example.omadatest.data.FlickrPhotoResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickrApiService {

    @GET("rest/")
    suspend fun getRecentPhotos(
        @Query("method") method: String = FLICKER_METHOD_GET_RECENT,
        @Query("api_key") apiKey: String = FLICKER_API_KEY,
        @Query("format") format: String = FLICKER_FORMAT_JSON,
        @Query("nojsoncallback") noJsonCallback: Int = 1,
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = FLICKER_IMAGES_PER_PAGE
    ): FlickrPhotoResponse

    @GET("rest/")
    suspend fun searchPhotos(
        @Query("method") method: String = FLICKER_METHOD_SEARCH,
        @Query("api_key") apiKey: String = FLICKER_API_KEY,
        @Query("format") format: String = FLICKER_FORMAT_JSON,
        @Query("nojsoncallback") noJsonCallback: Int = 1,
        @Query("text") query: String,
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = FLICKER_IMAGES_PER_PAGE
    ): FlickrPhotoResponse
}
