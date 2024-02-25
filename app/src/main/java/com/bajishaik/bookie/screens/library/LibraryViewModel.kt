package com.bajishaik.bookie.screens.library

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bajishaik.bookie.base.repo.BooksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

data class UIState(
    val isLoading : Boolean = false,
    val books : List<File> = emptyList()
)

@HiltViewModel
class LibraryViewModel @Inject constructor(private val booksRepository: BooksRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(UIState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val books = booksRepository.getAllStorageBooks()
            _uiState.update { it.copy(isLoading = false,books = books) }
        }
    }
}