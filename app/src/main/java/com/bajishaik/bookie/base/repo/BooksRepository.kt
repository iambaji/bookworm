package com.bajishaik.bookie.base.repo

import android.net.Uri
import com.bajishaik.bookie.base.storage.BookieFileStorage
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.net.URI
import javax.inject.Inject

class BooksRepository @Inject constructor(
    private val bookieFileStorage: BookieFileStorage,
    private val dispatcher : CoroutineDispatcher = Dispatchers.IO) {

    suspend fun getAllStorageBooks() : List<File> = withContext(dispatcher){
        val books = bookieFileStorage.getBooksFolder().listFiles()
        if (books != null) {
            return@withContext books.toList()
        }
        return@withContext emptyList()
    }

    suspend fun importBook(uri: Uri) = withContext(dispatcher){
        bookieFileStorage.importBook(uri)
    }
}