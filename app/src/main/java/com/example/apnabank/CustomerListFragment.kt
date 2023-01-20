package com.example.apnabank

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apnabank.databinding.FragmentAddCustomerBinding
import com.example.apnabank.databinding.FragmentCustomerListBinding


/**
 * A simple [Fragment] subclass.
 * Use the [CustomerListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CustomerListFragment : Fragment() {
    private val navigationArgs: CustomerListFragmentArgs by navArgs()
    private val viewModel :  ApnaBankViewModel by activityViewModels {
        ApnaBankViewModelFactory((activity?.application as ApnaBankApplication).database1.customerDao(),(activity?.application as ApnaBankApplication).database2.transactionDao())
    }
    private var _binding : FragmentCustomerListBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       _binding = DataBindingUtil.inflate(inflater,R.layout.fragment_customer_list,container,false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(navigationArgs.check=="yes"){
            binding.senderLayout.visibility = View.VISIBLE
        }
        else{
            binding.senderLayout.visibility = View.GONE

        }
        val adapter = CustomerListAdapter{
           val action = CustomerListFragmentDirections.actionCustomerListFragmentToSendAmountFragment(it.id,navigationArgs.check)
            findNavController().navigate(action)

        }
        binding.recyclerView.adapter = adapter
        viewModel.allCustomers.observe(this.viewLifecycleOwner) { customers ->
            customers.let {
                adapter.submitList(it)
            }
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(this.context)
    }
}