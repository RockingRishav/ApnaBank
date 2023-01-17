package com.example.apnabank.databases.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
@Database(entities = [Customers::class], version = 1, exportSchema = false)
abstract class CustomerDataBase : RoomDatabase() {
    abstract fun customerDao() : CustomerDao
    companion object {
        @Volatile
        private var INSTANCE : CustomerDataBase? = null
        fun getDatabase(context: Context):CustomerDataBase {
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CustomerDataBase::class.java,
                    "customer_database")
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return  instance
            }

        }

    }
}