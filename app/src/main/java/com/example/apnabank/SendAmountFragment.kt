package com.example.apnabank

import android.os.Bundle
import android.os.Handler
import android.os.Looper
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
import java.text.SimpleDateFormat
import java.util.*


class SendAmountFragment : Fragment() {
    private val navigationArgs: SendAmountFragmentArgs by navArgs()
    private val viewModel :  ApnaBankViewModel by activityViewModels {
        ApnaBankViewModelFactory((activity?.application as ApnaBankApplication).database1.customerDao(),(activity?.application as ApnaBankApplication).database2.transactionDao())
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
                val action = SendAmountFragmentDirections.actionSendAmountFragmentToRecieverListFragment(navigationArgs.customerId,binding.amountToSend.text.toString().toInt(),selectedCustomer.customerName)
                findNavController().navigate(action)
            }
            else{
                val v = Calendar.getInstance()
                val tdate = v.time
                val sdf = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
                val date  = sdf.format(tdate)
                viewModel.addNewTransaction(selectedCustomer.customerName,"",binding.amountToSend.text.toString().toInt(),date,"Failed")
                Toast.makeText(this.requireContext(),"Transaction Unsuccessful",Toast.LENGTH_SHORT)
                    .show()
                Handler(Looper.getMainLooper()).postDelayed({
                    activity?.onBackPressed()
                }, 500)

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
            if(customer.gender=="Male"){
                customerImage.setImageResource(R.drawable.man)
            }
            else{
                customerImage.setImageResource(R.drawable.woman)
            }
        }
    }
    private fun payable(): Boolean{
        if(binding.amountToSend.text.toString().toInt()<=selectedCustomer.amount){
            return true
        }
            return false

    }


}