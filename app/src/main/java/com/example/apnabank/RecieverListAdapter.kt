package com.example.apnabank

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.apnabank.databases.data.Customers
import com.example.apnabank.databases.data.formattedAmount
import com.example.apnabank.databinding.ListCustomersBinding

class RecieverListAdapter(private val onRecieverClicked : (Customers) -> Unit) : ListAdapter<Customers, RecieverListAdapter.RecieverViewHolder>(DiffCallback) {
     override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecieverViewHolder {
          return RecieverViewHolder(
               ListCustomersBinding.inflate(
                    LayoutInflater.from(
                         parent.context
                    )
               )
          )
     }

     override fun onBindViewHolder(holder: RecieverViewHolder, position: Int) {
          val current = getItem(position)
          holder.crdView.setOnClickListener {
               onRecieverClicked(current)

          }
          holder.bind(current)

     }
     class RecieverViewHolder(private var binding: ListCustomersBinding) : RecyclerView.ViewHolder(binding.root){
          val crdView = binding.itemView
          fun bind(reciever: Customers){

               binding.apply {
                    customerName.text = reciever.customerName
                    customerEmail.text = reciever.emailId
                    customerPhone.text = reciever.mobileNumber
                    customerAmount.text= reciever.formattedAmount()
                    if(reciever.gender == "Male"){
                         genderIv.setImageResource(R.drawable.man)
                    }
                    else{
                         genderIv.setImageResource(R.drawable.woman)
                    }
               }
          }
     }
     companion object {
          private val DiffCallback = object : DiffUtil.ItemCallback<Customers>() {
               override fun areItemsTheSame(oldCustomer: Customers, newCustomer: Customers): Boolean {
                    return oldCustomer === newCustomer
               }

               override fun areContentsTheSame(oldCustomer: Customers, newCustomer: Customers): Boolean {
                    return oldCustomer.customerName == newCustomer.customerName
               }
          }
     }
}