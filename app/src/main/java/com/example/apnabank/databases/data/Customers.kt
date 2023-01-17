package com.example.apnabank.databases.data

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.NumberFormat

@Entity(tableName = "customers")
data class Customers(@PrimaryKey(autoGenerate = true)  val id: Int = 0,
                     @NonNull @ColumnInfo(name = "customer_name") val customerName: String,
                     @NonNull @ColumnInfo(name = "account_Number") val accountNumber: Int,
                     @NonNull @ColumnInfo(name = "ifsc_code") val ifscCode:String,
                     @NonNull val gender: String, @NonNull var amount: Double,
                     @ColumnInfo(name = "mobile_number") val mobileNumber : String,
                     @ColumnInfo(name = "email_id") val emailId : String)
fun Customers.formattedAmount(): String = NumberFormat.getCurrencyInstance().format(amount)
