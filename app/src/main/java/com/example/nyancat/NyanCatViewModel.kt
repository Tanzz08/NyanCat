package com.example.nyancat

import androidx.lifecycle.ViewModel
import com.example.nyancat.data.CatRepository

class NyanCatViewModel(
    private val catRepository: CatRepository

) : ViewModel() {
    fun getCat() = catRepository.getCat()
}