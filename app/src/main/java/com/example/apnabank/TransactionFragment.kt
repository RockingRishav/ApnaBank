package com.example.apnabank

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apnabank.databases.data.Customers
import com.example.apnabank.databinding.FragmentTransactionBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class TransactionFragment : Fragment() {
    private val navigationArgs : TransactionFragmentArgs by navArgs()
    private var _binding : FragmentTransactionBinding? = null
    private val binding get() = _binding!!
    lateinit var receiverCus : Customers

    private val viewModel :  ApnaBankViewModel by activityViewModels {
        ApnaBankViewModelFactory((activity?.application as ApnaBankApplication).database1.customerDao(),(activity?.application as ApnaBankApplication).database2.transactionDao())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater,R.layout.fragment_transaction,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var cnt =0
        if(navigationArgs.doTransaction=="yes"){
            viewModel.recieverCustomer(navigationArgs.receiverId).observe(this.viewLifecycleOwner){item ->
                receiverCus = item
                if(cnt==0){
                    viewModel.receivePayment(receiverCus,navigationArgs.amount)
                }
                cnt =1

            }
        }

        val adapter = TransactionListAdapter{

        }
        binding.recyclerView.adapter = adapter
        viewModel.allTransactions.observe(this.viewLifecycleOwner){transactions ->
            transactions.let {
                adapter.submitList(it)
            }

        }
        binding.recyclerView.layoutManager = LinearLayoutManager(this.context)

 }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}