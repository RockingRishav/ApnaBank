package com.example.apnabank.databases.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CustomerDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(customer : Customers)
    @Update
    suspend fun update(customer :  Customers)
    @Delete
    suspend fun delete(customer : Customers)
    @Query("SELECT * FROM customers WHERE id = :id")
    fun getCustomer(id: Int):Flow<Customers>

    @Query("SELECT * FROM customers ORDER BY id ASC")
    fun getCustomers(): Flow<List<Customers>>
    @Query("SELECT * FROM customers WHERE id != :id")
    fun getRecievers(id: Int): Flow<List<Customers>>
    @Query("SELECT customer_name FROM customers WHERE id = :id")
    fun getCusName(id: Int): String


}