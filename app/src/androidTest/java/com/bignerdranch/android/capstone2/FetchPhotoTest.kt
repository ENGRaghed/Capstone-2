package com.bignerdranch.android.capstone2

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import com.bignerdranch.android.capstone2.model.Photo
import com.bignerdranch.android.capstone2.viewmodel.PhotoViewModel
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class FetchPhotoTest {


    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var photoViewModel: PhotoViewModel

    @Before
    fun setUp() {
        photoViewModel = PhotoViewModel()
    }

    @Test
    fun successfetchPhoto() = runBlocking{
        val responseCall: LiveData<List<Photo>> = photoViewModel.fetchPhoto("24.912726", "46.736645")
        val x = responseCall.getOrAwaitValue()
        assert(x.isNotEmpty())
    }

    @Test
    fun fetchPhoto() = runBlocking{
        val responseCall: LiveData<List<Photo>> = photoViewModel.fetchPhoto("","")
        val x = responseCall.value
        assert(x.isNullOrEmpty())
    }

}