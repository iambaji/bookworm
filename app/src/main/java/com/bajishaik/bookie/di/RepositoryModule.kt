package com.bajishaik.bookie.di

import com.bajishaik.bookie.base.repo.BooksRepository
import com.bajishaik.bookie.base.storage.BookieFileStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    fun provideBooksRepository(storage: BookieFileStorage) : BooksRepository{
        return BooksRepository(storage,Dispatchers.IO)
    }
}