package com.example.artbooktesting.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.ViewModel
import com.example.artbooktesting.MainCoroutineRule
import com.example.artbooktesting.repo.ArtRepository
import com.example.artbooktesting.repo.FakeArtRepository
import com.example.artbooktesting.util.Status
import com.google.common.truth.Truth.assertThat
import getOrAwaitValueTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@ExperimentalCoroutinesApi
class ArtViewModelTest {

    @get:Rule
    var instantTaskExecutorRule : TestRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel : ArtViewModel

    @Before
    fun setup(){
        viewModel= ArtViewModel(FakeArtRepository())
    }

    @Test
    fun `insert art without year returns error`(){
        viewModel.makeArt("Mona Lisa","Da vinci","")
        val value = viewModel.insertMessage.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert art without name returns error`(){
        viewModel.makeArt("","Da vinci","1500")
        val value = viewModel.insertMessage.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert art without artistName returns error`(){
        viewModel.makeArt("Mona Lisa","","1500")
        val value = viewModel.insertMessage.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.ERROR)
    }
}
