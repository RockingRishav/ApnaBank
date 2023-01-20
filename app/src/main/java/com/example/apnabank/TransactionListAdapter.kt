package com.example.apnabank

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.apnabank.databases.data.Customers
import com.example.apnabank.databases.data.Transactions
import com.example.apnabank.databases.data.formattedAmount
import com.example.apnabank.databinding.ListTransactionsBinding

class TransactionListAdapter(private val onTransactionClicked : (Transactions) -> Unit) : ListAdapter<Transactions, TransactionListAdapter.TransactionViewHolder>(
    DiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        return TransactionViewHolder(
            ListTransactionsBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }
    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val current = getItem(position)
        holder.crdView.setOnClickListener {
            onTransactionClicked(current)
        }
        holder.bind(current)
    }

    class TransactionViewHolder(private var binding: ListTransactionsBinding) : RecyclerView.ViewHolder(binding.root){
        val crdView = binding.crdView
        fun bind(transaction: Transactions){
            binding.apply {
                name1.text = transaction.senderName
                name2.text = transaction.receiverName
                balance.text = transaction.formattedAmount()
                transactionStatus.text = transaction.transactionStatus
                date.text = transaction.date
                if(transaction.transactionStatus == "Failed"){
                    arrow.setImageResource(R.drawable.ic_baseline_cancel_24)
                    transactionStatus.setTextColor(Color.parseColor("#CC4027"))
                }
                else{
                    arrow.setImageResource(R.drawable.ic_baseline_arrow_forward_24)
                    transactionStatus.setTextColor(Color.parseColor("#41E41D"))
                }

            }
        }
    }
    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Transactions>() {
            override fun areItemsTheSame(oldItem: Transactions, newItem: Transactions): Boolean {
                return oldItem === newItem
            }
            override fun areContentsTheSame(oldItem: Transactions, newItem: Transactions): Boolean {
                return oldItem.senderName == newItem.senderName
            }
        }
    }
}