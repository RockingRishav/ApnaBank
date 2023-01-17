package com.example.apnabank

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Ignore
import com.example.apnabank.databases.data.Customers
import com.example.apnabank.databases.data.formattedAmount
import com.example.apnabank.databinding.FragmentCustomerListBinding
import com.example.apnabank.databinding.ListCustomersBinding

class CustomerListAdapter(private val onCustomerClicked: (Customers) -> Unit) : ListAdapter<Customers, CustomerListAdapter.CustomerViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerViewHolder {
        return CustomerViewHolder(
            ListCustomersBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }
    override fun onBindViewHolder(holder: CustomerViewHolder, position: Int) {
        val current = getItem(position)
        holder.crdView.setOnClickListener {
            onCustomerClicked(current)
        }
        holder.bind(current)
    }
    class CustomerViewHolder(private var binding: ListCustomersBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val crdView = binding.itemView
        fun bind(customer: Customers) {
            binding.apply {
                customerName.text = customer.customerName
                customerEmail.text = customer.emailId
                customerPhone.text = customer.mobileNumber
                customerAmount.text= customer.formattedAmount()

                if(customer.gender == "Male"){
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