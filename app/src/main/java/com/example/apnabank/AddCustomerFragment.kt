package com.example.apnabank

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.apnabank.databases.data.Customers
import com.example.apnabank.databinding.FragmentAddCustomerBinding


/**
 * A simple [Fragment] subclass.
 * Use the [AddCustomerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddCustomerFragment : Fragment() {
    private var _binding : FragmentAddCustomerBinding? = null
    private val binding get() = _binding!!
    private val viewModel :  ApnaBankViewModel by activityViewModels {
        ApnaBankViewModelFactory((activity?.application as ApnaBankApplication).database.customerDao())
    }
    private lateinit var customer : Customers
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater,R.layout.fragment_add_customer,container,false)

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val genders = resources.getStringArray(R.array.Gender)
        val arrayAdapter  = ArrayAdapter(this.requireContext(),R.layout.dropdown_item,genders)
         binding.genderTv.setAdapter(arrayAdapter)
        binding.addCustomer.setOnClickListener {
            addNewCustomer()
        }
        binding.cancel.setOnClickListener {
           val action = AddCustomerFragmentDirections.actionAddCustomerFragmentToFrontFragment()
           findNavController().navigate(action)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun isEntryValid(): Boolean{
        return viewModel.isEntryValid(
            binding.customerName.text.toString(),
            binding.accountNo.text.toString(),
            binding.ifscCode.text.toString(),
            binding.amount.text.toString(),
            binding.genderTv.text.toString(),
            binding.phoneNo.text.toString()
        )
    }
    private fun addNewCustomer(){
        if(isEntryValid()){
            viewModel.addNewCustomer(
                binding.customerName.text.toString(),
                binding.accountNo.text.toString(),
                binding.ifscCode.text.toString(),
                binding.amount.text.toString(),
                binding.phoneNo.text.toString(),
                binding.email.text.toString(),
                binding.genderTv.text.toString()
            )
            val action = AddCustomerFragmentDirections.actionAddCustomerFragmentToFrontFragment()
            findNavController().navigate(action)
        }
    }

}