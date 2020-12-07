package com.bignerdranch.android.capstone2.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import com.bignerdranch.android.capstone2.ServiceLocator
import com.bignerdranch.android.capstone2.getOrAwaitValue
import com.bignerdranch.android.capstone2.getOrAwaitValueTest
import com.bignerdranch.android.capstone2.model.Photo
import com.bignerdranch.android.capstone2.network.FlickrApi
import com.bignerdranch.android.capstone2.repository.FlickrRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
//import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(JUnit4::class)
class PhotoViewModelTest{
//
//    lateinit var retrofit: Retrofit
//    private lateinit var flickrApi: FlickrApi
//    private lateinit var flickrRepository: FlickrRepository
//
//    @Mock
//    private lateinit var viewModel: PhotoViewModel
//
//    @get:Rule
//    var instantTaskExecutorRule = InstantTaskExecutorRule()
//
//
//    @Before
//    fun setup(){
//        viewModel = PhotoViewModel()
//            retrofit = Retrofit.Builder()
//            .baseUrl("https://www.flickr.com/")
//            .client(ServiceLocator.getOkhttpClient())
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//        flickrApi = retrofit.create(FlickrApi::class.java)
//        flickrRepository = FlickrRepository(flickrApi)
//
//
//
//        }
//
//        @Test
//        fun insertValidInfo(){
//            val value = viewModel.fetchPhoto("24.912726", "46.736645").getOrAwaitValueTest()
//            assertThat(value.isNotEmpty())
//
//        }



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