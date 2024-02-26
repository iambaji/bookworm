package com.bajishaik.bookie.screens.library

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bajishaik.bookie.R
import com.bajishaik.bookie.databinding.FragmentLibraryBinding
import com.bajishaik.bookie.screens.compose.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LibraryFragment : Fragment() {

    private lateinit var binding : FragmentLibraryBinding

    private val viewModel by viewModels<LibraryViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply{
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                AppTheme {
                    Surface(tonalElevation = 5.dp) {
                        LibraryScreen()
                    }
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpObservers()
    }

    private fun setUpObservers(){
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.uiState.map { it.events }.distinctUntilChanged().onEach {
                    viewModel.eventHandled()
                    when(it){
                        is UIEvents.GotoReader -> findNavController().navigate(R.id.readerFragment)
                        UIEvents.Initial -> {}
                    }
                }.launchIn(this)
            }
        }
    }

    @Preview(name = "Library")
    @Composable
    fun LibraryScreen(){
        val uiState by viewModel.uiState.collectAsState()
        Column(modifier = Modifier.fillMaxSize()) {
            BookList(books = uiState.books.map { it.name }, onClick = {
                viewModel.onBooksItemClick(it)
            })
        }
    }

    @Composable
    fun BookList(books : List<String>, onClick : (Int) -> Unit){
        LazyColumn(modifier = Modifier.fillMaxWidth()){
            items(books){
                Text(
                    text = it,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onClick(0) }
                        .padding(5.dp)
                        .background(MaterialTheme.colorScheme.background)
                        .padding(10.dp, 20.dp)
                )
            }
        }
    }

    @Preview
    @Composable
    fun BookListPreview(){
        BookList(books = listOf("Test 1", "Test 2", "Book 3"), onClick = {})
    }
}