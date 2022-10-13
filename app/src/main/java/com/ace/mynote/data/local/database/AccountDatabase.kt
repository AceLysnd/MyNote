package com.ace.mynote.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ace.mynote.data.local.database.user.AccountDao
import com.ace.mynote.data.local.database.user.AccountEntity

@Database(entities = [AccountEntity::class], version = 4, exportSchema = false)
abstract class AccountDatabase : RoomDatabase() {

    abstract val accountDao : AccountDao

    companion object {

        @Volatile
        var INSTANCE: AccountDatabase? = null

        fun getInstance(context: Context): AccountDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AccountDatabase::class.java,
                        "app_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}