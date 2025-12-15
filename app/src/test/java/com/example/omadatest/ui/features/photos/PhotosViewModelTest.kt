package com.example.omadatest.ui.features.photos

import com.example.omadatest.data.Photo
import com.example.omadatest.data.repository.FlickrRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever


@OptIn(ExperimentalCoroutinesApi::class)
class PhotosViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var repository: FlickrRepository
    private lateinit var viewModel: PhotosViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mock()
        viewModel = PhotosViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial load emits Success`() = runTest {
        // GIVEN
        val fakePhotos = listOf(Photo("1", "title1", "url1"))
        whenever(repository.getPhotos(query = "", page = 1)).thenReturn(fakePhotos)

        // WHEN
        // Create VM — loadPhotos is called in init
        val viewModel = PhotosViewModel(repository)
        advanceUntilIdle()

        // THEN
        val state = viewModel.uiState.value
        assert(state is PhotosUiState.Success)
        assert((state as PhotosUiState.Success).photos.size == 1)
    }

    @Test
    fun `initial load emits Error on repository failure`() = runTest {
        // GIVEN
        val errorMessage = "Network failure"
        whenever(
            repository.getPhotos(
                query = "",
                page = 1
            )
        ).thenThrow(RuntimeException(errorMessage))

        // WHEN
        val viewModel = PhotosViewModel(repository)
        advanceUntilIdle()

        // THEN
        val state = viewModel.uiState.value
        assert(state is PhotosUiState.Error)
        assert((state as PhotosUiState.Error).message == errorMessage)
    }

    @Test
    fun `loadNextPage appends new photos`() = runTest {
        // GIVEN
        val firstPage = listOf(Photo("1", "title1", "url1"))
        val secondPage = listOf(Photo("2", "title2", "url2"))

        whenever(repository.getPhotos(query = "", page = 1)).thenReturn(firstPage)
        whenever(repository.getPhotos(query = "", page = 2)).thenReturn(secondPage)

        // WHEN
        val viewModel = PhotosViewModel(repository)
        advanceUntilIdle()

        viewModel.loadNextPage()
        advanceUntilIdle()

        // THEN
        val state = viewModel.uiState.value
        assert(state is PhotosUiState.Success)
        assert((state as PhotosUiState.Success).photos == firstPage + secondPage)
    }

    @Test
    fun `loadNextPage does not load if no more photos`() = runTest {
        // GIVEN
        val firstPage = listOf(Photo("1", "title1", "url1"))
        whenever(repository.getPhotos(query = "", page = 1)).thenReturn(firstPage)
        whenever(repository.getPhotos(query = "", page = 2)).thenReturn(emptyList())

        // WHEN
        val viewModel = PhotosViewModel(repository)
        advanceUntilIdle()

        viewModel.loadNextPage()
        advanceUntilIdle()

        // THEN
        val state = viewModel.uiState.value
        assert(state is PhotosUiState.Success)
        assert((state as PhotosUiState.Success).photos == firstPage)
    }

}
