package com.example.nyancat

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.example.nyancat.data.CatRepository
import com.example.nyancat.data.local.entity.CatEntity
import com.example.nyancat.ui.common.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NyanCatViewModel(
    private val catRepository: CatRepository

) : ViewModel() {
    private val _cats = MutableStateFlow<Result<List<CatEntity>>>(Result.Loading)
    val cats: StateFlow<Result<List<CatEntity>>> = _cats


    private val catData = MutableLiveData<CatEntity>()



    init {
        viewModelScope.launch {
            catRepository.getCat().collect { result ->
                _cats.value = result
            }
            catRepository.getBengCats().collect { result ->
                _cats.value = result
            }
            catRepository.getAshoCats().collect { result ->
                _cats.value = result
            }
        }
    }


    fun setCatData(cat: CatEntity) {
        catData.value = cat
    }

}