package com.ace.mynote.data.local.database.repository

import com.ace.mynote.data.local.database.note.NoteDataSource
import com.ace.mynote.data.local.database.note.NoteEntity
import com.ace.mynote.data.local.database.user.AccountDataSource
import com.ace.mynote.data.local.database.user.AccountEntity
import com.ace.mynote.wrapper.Resource

interface LocalRepository {

    suspend fun getAccountById(id: Long): Resource<AccountEntity?>
    suspend fun createAccount(account: AccountEntity): Resource<Number>
    suspend fun updateAccount(account: AccountEntity): Resource<Number>
    suspend fun getAccount(username: String): Resource<AccountEntity>

    suspend fun getNoteById(id: Int): Resource<NoteEntity?>
    suspend fun insertNote(item: NoteEntity): Resource<Number>
    suspend fun updateNote(item: NoteEntity): Resource<Number>
}

class LocalRepositoryImpl(
    private val accountDataSource: AccountDataSource,
    private val noteDataSource: NoteDataSource
) : LocalRepository {

    override suspend fun getAccountById(id: Long): Resource<AccountEntity?> {
        return proceed {
            accountDataSource.getAccountById(id)
        }
    }

    override suspend fun createAccount(account: AccountEntity): Resource<Number> {
        return proceed {
            accountDataSource.registerAccount(account)
        }
    }

    override suspend fun updateAccount(account: AccountEntity): Resource<Number> {
        return proceed {
            accountDataSource.updateAccount(account)
        }
    }

    override suspend fun getAccount(username: String): Resource<AccountEntity> {
        return proceed {
            accountDataSource.getUser(username)
        }
    }

    override suspend fun getNoteById(id: Int): Resource<NoteEntity?> {
        return proceed {
            noteDataSource.getNoteById(id)
        }
    }

    override suspend fun insertNote(item: NoteEntity): Resource<Number> {
        return proceed {
            noteDataSource.insertNote(item)
        }
    }

    override suspend fun updateNote(item: NoteEntity): Resource<Number> {
        return proceed {
            noteDataSource.updateNote(item)
        }
    }


    private suspend fun <T> proceed(coroutine: suspend () -> T): Resource<T> {
        return try {
            Resource.Success(coroutine.invoke())
        } catch (exception: Exception) {
            Resource.Error(exception)
        }
    }

}