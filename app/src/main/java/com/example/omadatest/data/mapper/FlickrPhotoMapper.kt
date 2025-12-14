package com.example.omadatest.data.mapper

import com.example.omadatest.data.FlickrPhotoDto
import com.example.omadatest.data.Photo
import com.example.omadatest.data.network.FLICKR_IMAGE_BASE_URL

fun FlickrPhotoDto.toDomain(): Photo {
    return Photo(
        id = id,
        title = title,
        imageUrl = "$FLICKR_IMAGE_BASE_URL/$server/${id}_$secret.jpg"
    )
}