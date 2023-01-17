package com.example.apnabank

import android.app.Application
import com.example.apnabank.databases.data.CustomerDataBase

class ApnaBankApplication: Application() {
    val database : CustomerDataBase by lazy { CustomerDataBase.getDatabase(this)}
}