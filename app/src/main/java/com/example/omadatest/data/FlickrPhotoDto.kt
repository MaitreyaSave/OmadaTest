package com.example.omadatest.data

data class FlickrPhotoDto(
    val id: String,
    val owner: String,
    val secret: String,
    val server: String,
    val title: String
)

data class FlickrPhotoResponse(
    val photos: Photos
) {
    data class Photos(
        val page: Int,
        val pages: Int,
        val perpage: Int,
        val total: String,
        val photo: List<FlickrPhotoDto>
    )
}

