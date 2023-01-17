package com.example.apnabank

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.apnabank.databases.data.Customers
import com.example.apnabank.databinding.FragmentCustomerListBinding
import com.example.apnabank.databinding.FragmentSendAmountBinding


class SendAmountFragment : Fragment() {
    private val navigationArgs: SendAmountFragmentArgs by navArgs()
    private val viewModel :  ApnaBankViewModel by activityViewModels {
        ApnaBankViewModelFactory((activity?.application as ApnaBankApplication).database.customerDao())
    }
    private var _binding : FragmentSendAmountBinding? = null
    private val binding get() = _binding!!
    lateinit var selectedCustomer : Customers

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater,R.layout.fragment_send_amount,container,false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(navigationArgs.check=="yes"){
            binding.sendAmountLayout.visibility = View.VISIBLE
        }
        else{
            binding.sendAmountLayout.visibility = View.INVISIBLE

        }
        val id = navigationArgs.customerId
        viewModel.senderCustomer(id).observe(this.viewLifecycleOwner){item ->
            selectedCustomer = item
            bind(selectedCustomer)
        }
        binding.paymentButton.setOnClickListener {
            if(payable()){
                viewModel.makepayment(selectedCustomer,binding.amountToSend.text.toString().toInt())
                val action = SendAmountFragmentDirections.actionSendAmountFragmentToRecieverListFragment(navigationArgs.customerId,binding.amountToSend.text.toString().toInt())
                findNavController().navigate(action)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun bind(customer : Customers){
        binding.apply {
            username.text = customer.customerName
            userphonenumber.text = customer.mobileNumber
            email.text = customer.emailId
            accountNo.text = customer.accountNumber.toString()
            ifscCode.text = customer.ifscCode
            balance.text = customer.amount.toString()
        }
    }
    private fun payable(): Boolean{
        if(binding.amountToSend.text.toString().toInt()<=selectedCustomer.amount){
            return true
        }
            return false

    }


}