package com.example.apnabank

import androidx.lifecycle.*
import com.example.apnabank.databases.data.CustomerDao
import com.example.apnabank.databases.data.Customers
import com.example.apnabank.databases.data.TransactionDao
import com.example.apnabank.databases.data.Transactions
import kotlinx.coroutines.launch
class ApnaBankViewModel(private val customerDao: CustomerDao,private val transactionDao : TransactionDao) :  ViewModel() {
    val allCustomers : LiveData<List<Customers>> = customerDao.getCustomers().asLiveData()
    val allTransactions : LiveData<List<Transactions>> = transactionDao.getTransactions().asLiveData()
    private fun insertCustomer(customer: Customers){
        viewModelScope.launch {
            customerDao.insert(customer)
        }
    }
    private fun insertTransaction(transaction : Transactions){
        viewModelScope.launch {
            transactionDao.insert(transaction)
        }
    }
    private  fun getCustomerEntry(name : String,accountNumber : String,ifscCode:String,amount:String,mobileNo:String,email:String,gender:String):Customers{

     return Customers(
         customerName = name,
         accountNumber = accountNumber.toInt(),
         ifscCode = ifscCode,
         gender = gender,
         amount = amount.toDouble(),
         mobileNumber = mobileNo,
         emailId = email)
    }
    private fun getTransactionEntry(senderName: String,receiverName : String,amount : Int,date :String,transactionStatus : String) : Transactions{
        return Transactions(
            senderName = senderName,
            receiverName = receiverName,
            amount = amount,
            date = date,
            transactionStatus = transactionStatus
        )
    }
    fun addNewTransaction(senderName: String,receiverName : String,amount: Int,date: String, transactionStatus: String){
        val newTransaction = getTransactionEntry(senderName,receiverName,amount,date,transactionStatus)

            insertTransaction(newTransaction)


    }
    fun addNewCustomer(name : String,accountNumber : String,ifscCode:String,amount:String,mobileNo:String,email:String,gender:String){
        val newCustomer= getCustomerEntry(name,accountNumber,ifscCode,amount,mobileNo,email,gender)
        insertCustomer(newCustomer)
    }
    fun isEntryValid(name : String,accountNumber : String,ifscCode:String,amount:String,gender:String,mobileNo: String):Boolean{
        if(name.isBlank()||accountNumber.isBlank()||ifscCode.isBlank()||amount.isBlank()||gender.isBlank()||mobileNo.isBlank()){
            return false
        }
        return true

    }
    fun senderCustomer(id:Int): LiveData<Customers>{
        return customerDao.getCustomer(id).asLiveData()
    }
    fun recieverCustomer(id:Int):LiveData<Customers>{
        return customerDao.getCustomer(id).asLiveData()
    }
    fun recieverList(id:Int):LiveData<List<Customers>>{
        return customerDao.getRecievers(id).asLiveData()
    }
    fun makepayment(sender : Customers,deduct : Int){
        val updatedSender = sender.copy(amount = sender.amount-deduct)
        updateCustomer(updatedSender)
    }
    fun receivePayment(receiver : Customers,addAmount : Int){
        val updatedReceiver = receiver.copy(amount = receiver.amount+addAmount)
        updateCustomer(updatedReceiver)
    }
    fun customerName(id: Int): String{
        return customerDao.getCusName(id)
    }
    private fun updateCustomer(customer: Customers){
        viewModelScope.launch {
            customerDao.update(customer)
        }
    }
}
class ApnaBankViewModelFactory(private val customerDao: CustomerDao,private val transactionDao : TransactionDao): ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ApnaBankViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ApnaBankViewModel(customerDao,transactionDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}