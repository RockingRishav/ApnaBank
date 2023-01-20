package com.example.apnabank.databases.data

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.NumberFormat

@Entity(tableName = "transactions")
data class Transactions(
    @PrimaryKey(autoGenerate = true) val id : Int = 0,
    @NonNull @ColumnInfo(name = "sender_name")val senderName : String,
    @ColumnInfo(name = "receiver_name") val receiverName : String,
    @NonNull val amount :Int,
    @NonNull val date : String,
    @NonNull val transactionStatus : String

    )
fun Transactions.formattedAmount(): String = NumberFormat.getCurrencyInstance().format(amount)