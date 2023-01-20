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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apnabank.databases.data.Customers
import com.example.apnabank.databinding.FragmentCustomerListBinding
import com.example.apnabank.databinding.FragmentRecieverListBinding
import java.text.SimpleDateFormat
import java.util.*


class RecieverListFragment : Fragment() {
    private val navArgs : RecieverListFragmentArgs by navArgs()

    private var iD : Int? = null
    private val viewModel :  ApnaBankViewModel by activityViewModels {
        ApnaBankViewModelFactory((activity?.application as ApnaBankApplication).database1.customerDao(),(activity?.application as ApnaBankApplication).database2.transactionDao())
    }
    private var _binding : FragmentRecieverListBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater,R.layout.fragment_reciever_list,container,false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = RecieverListAdapter{
            val v = Calendar.getInstance()
            val tdate = v.time
            val sdf = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
            val date  = sdf.format(tdate)
            viewModel.addNewTransaction(navArgs.senderName,it.customerName,navArgs.amount,date,"Success")
            Toast.makeText(this.requireContext(),"Amount Paid to "+"${it.customerName}",Toast.LENGTH_SHORT)
                .show()
           val action = RecieverListFragmentDirections.actionRecieverListFragmentToTransactionFragment(it.id,navArgs.amount,navArgs.senderName,it.customerName,"yes")

            Handler(Looper.getMainLooper()).postDelayed({
                findNavController().navigate(action)
            }, 500)

        }

        binding.recyclerView.adapter = adapter
        viewModel.recieverList(navArgs.senderId).observe(this.viewLifecycleOwner){
            recievers -> recievers.let {
                adapter.submitList(it)
        }
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(this.context)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding =null
    }
}