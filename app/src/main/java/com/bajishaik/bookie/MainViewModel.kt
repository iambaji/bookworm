package com.bajishaik.bookie

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bajishaik.bookie.base.repo.BooksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class UIState(
    val isLoading : Boolean = false
)
@HiltViewModel
class MainViewModel @Inject constructor(private val booksRepository: BooksRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(UIState())
    val uiState = _uiState.asStateFlow()

    fun importBook(uri : Uri) = viewModelScope.launch {
        _uiState.update { it.copy(isLoading = true) }
        booksRepository.importBook(uri)
        _uiState.update { it.copy(isLoading = false) }
    }

}