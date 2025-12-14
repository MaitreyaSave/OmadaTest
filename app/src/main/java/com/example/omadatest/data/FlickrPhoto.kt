package com.example.omadatest.data

data class FlickrPhoto(
    val id: String,
    val owner: String,
    val secret: String,
    val server: String,
    val title: String
) {
    fun imageUrl(): String = "https://live.staticflickr.com/$server/${id}_$secret.jpg"
}

data class FlickrPhotoResponse(
    val photos: Photos
) {
    data class Photos(
        val photo: List<FlickrPhoto>
    )
}
