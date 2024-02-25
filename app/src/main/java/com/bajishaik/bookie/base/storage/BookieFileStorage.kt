package com.bajishaik.bookie.base.storage

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import androidx.core.database.getStringOrNull
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject

class BookieFileStorage @Inject constructor(@ApplicationContext private val context : Context) {

    private val rootFolder by lazy { context.filesDir }

    fun getBooksFolder() : File {
        val booksFolder = File(rootFolder,"books")
        return booksFolder
    }

    fun getBookFileToWrite(bookName : String) : File{
        val bookFile = File(getBooksFolder(),bookName)
        createFileIfNotExists(bookFile)
        return bookFile
    }

    fun importBook(uri: Uri) {
        val contentResolver = context.contentResolver
        contentResolver.query(uri,null,null,null,null)?.use {
            it.moveToFirst()
            val name = it.getStringOrNull(it.getColumnIndex(OpenableColumns.DISPLAY_NAME)) ?: System.currentTimeMillis().toString()
            val outputFIle = getBookFileToWrite(name)
            contentResolver.openInputStream(uri).use { input->
                input?.bufferedReader()?.use {
                    outputFIle.writeText(it.readText())
                }
            }
        }
    }

    private fun createFileIfNotExists(file : File){
        if(!file.exists()){
            file.parentFile?.mkdirs()
            file.createNewFile()
        }
    }
}