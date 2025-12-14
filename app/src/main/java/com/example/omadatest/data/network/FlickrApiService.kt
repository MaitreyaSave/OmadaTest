package com.example.omadatest.data.network

import com.example.omadatest.data.FlickrPhotoResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickrApiService {

    @GET("rest/")
    suspend fun getRecentPhotos(
        @Query("method") method: String = "flickr.photos.getRecent",
        @Query("api_key") apiKey: String = "a0222db495999c951dc33702500fdc4d",
        @Query("format") format: String = "json",
        @Query("nojsoncallback") noJsonCallback: Int = 1,
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 30
    ): FlickrPhotoResponse

    @GET("rest/")
    suspend fun searchPhotos(
        @Query("method") method: String = "flickr.photos.search",
        @Query("api_key") apiKey: String = "a0222db495999c951dc33702500fdc4d",
        @Query("format") format: String = "json",
        @Query("nojsoncallback") noJsonCallback: Int = 1,
        @Query("text") query: String,
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 30
    ): FlickrPhotoResponse
}
