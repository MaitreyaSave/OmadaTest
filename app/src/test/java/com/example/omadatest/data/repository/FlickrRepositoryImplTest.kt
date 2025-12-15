package com.example.omadatest.data.repository

import com.example.omadatest.data.FlickrPhotoDto
import com.example.omadatest.data.FlickrPhotoResponse
import com.example.omadatest.data.FlickrPhotoResponse.Photos
import com.example.omadatest.data.Photo
import com.example.omadatest.data.network.FlickrApiService
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class FlickrRepositoryImplTest {

    private lateinit var apiService: FlickrApiService
    private lateinit var repository: FlickrRepositoryImpl

    @Before
    fun setup() {
        apiService = mock()
        repository = FlickrRepositoryImpl(apiService)
    }

    @Test
    fun `getPhotos returns mapped photos for recent`() = runTest {
        // GIVEN
        val flickrPhotoDto = FlickrPhotoDto("1", "owner1", "secret1", "server1", "Title1")
        val photos = Photos(
            page = 1,
            pages = 1,
            perpage = 1,
            total = "1",
            photo = listOf(flickrPhotoDto)
        )
        val response = FlickrPhotoResponse(photos)

        whenever(apiService.getRecentPhotos(page = 1)).thenReturn(response)

        // WHEN
        val result: List<Photo> = repository.getPhotos(query = "", page = 1)

        // THEN
        assertEquals(1, result.size)
        val photo = result[0]
        assertEquals("1", photo.id)
        assertEquals("Title1", photo.title)
        assertEquals("https://live.staticflickr.com/server1/1_secret1.jpg", photo.imageUrl)
    }

    @Test
    fun `getPhotos returns mapped photos for search`() = runTest {
        // GIVEN
        val flickrPhotoDto = FlickrPhotoDto("2", "owner2", "secret2", "server2", "Title2")
        val photos = Photos(
            page = 1,
            pages = 1,
            perpage = 1,
            total = "1",
            photo = listOf(flickrPhotoDto)
        )
        val response = FlickrPhotoResponse(photos)

        whenever(apiService.searchPhotos(query = "test", page = 1)).thenReturn(response)

        // WHEN
        val result: List<Photo> = repository.getPhotos(query = "test", page = 1)

        // THEN
        assertEquals(1, result.size)
        val photo = result[0]
        assertEquals("2", photo.id)
        assertEquals("Title2", photo.title)
        assertEquals("https://live.staticflickr.com/server2/2_secret2.jpg", photo.imageUrl)
    }
}