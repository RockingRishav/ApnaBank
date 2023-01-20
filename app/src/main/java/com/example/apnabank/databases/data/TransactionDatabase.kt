package com.example.apnabank.databases.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Transactions::class], version = 2, exportSchema = false)
abstract class TransactionDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao
    companion object {
        @Volatile
        private var INSTANCE : TransactionDatabase? = null
        fun getDatabase(context: Context):TransactionDatabase {
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TransactionDatabase::class.java,
                    "transaction_database")
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return  instance
            }

        }

    }

}