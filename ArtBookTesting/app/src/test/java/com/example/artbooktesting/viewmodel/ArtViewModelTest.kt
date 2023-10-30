package com.example.artbooktesting.viewmodel

import androidx.lifecycle.ViewModel
import com.example.artbooktesting.repo.ArtRepository
import com.example.artbooktesting.repo.FakeArtRepository
import org.junit.Before

class ArtViewModelTest {

    private lateinit var viewModel : ArtViewModel

    @Before
    fun setup(){
        viewModel= ArtViewModel(FakeArtRepository())
    }

}
