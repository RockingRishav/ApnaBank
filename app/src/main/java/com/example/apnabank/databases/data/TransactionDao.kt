package com.example.apnabank.databases.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(transaction: Transactions)
    @Query("SELECT * FROM transactions ORDER BY id ASC")
    fun getTransactions(): Flow<List<Transactions>>
}