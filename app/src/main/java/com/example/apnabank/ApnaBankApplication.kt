package com.example.apnabank

import android.app.Application
import com.example.apnabank.databases.data.CustomerDataBase
import com.example.apnabank.databases.data.TransactionDatabase

class ApnaBankApplication: Application() {
    val database1 : CustomerDataBase by lazy { CustomerDataBase.getDatabase(this)}
    val database2 : TransactionDatabase by lazy { TransactionDatabase.getDatabase(this) }
}