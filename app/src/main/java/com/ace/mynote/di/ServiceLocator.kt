package com.ace.mynote.di

import android.content.Context
import com.ace.mynote.data.local.database.AccountDatabase
import com.ace.mynote.data.local.database.NoteDatabase
import com.ace.mynote.data.local.database.repository.LocalRepository
import com.ace.mynote.data.local.database.repository.LocalRepositoryImpl
import com.ace.mynote.data.local.database.user.AccountDao
import com.ace.mynote.data.local.database.user.AccountDataSource
import com.ace.mynote.data.local.database.user.AccountDataSourceImpl

object ServiceLocator {

    fun provideAccountDatabase(context: Context): AccountDatabase {
        return AccountDatabase.getInstance(context)
    }

    fun provideAccountDao(context: Context): AccountDao {
        return provideAccountDatabase(context).accountDao
    }

    fun provideUserDataSource(context: Context): AccountDataSource {
        return AccountDataSourceImpl(provideAccountDao(context))
    }

    fun provideServiceLocator(context: Context): LocalRepository {
        return LocalRepositoryImpl(
            provideUserDataSource(context),
        )
    }
}