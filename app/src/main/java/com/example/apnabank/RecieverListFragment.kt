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
import com.example.apnabank.databases.data.Customers
import com.example.apnabank.databinding.FragmentCustomerListBinding
import com.example.apnabank.databinding.FragmentRecieverListBinding


class RecieverListFragment : Fragment() {
    private val navArgs : RecieverListFragmentArgs by navArgs()

    private var iD : Int? = null
    private val viewModel :  ApnaBankViewModel by activityViewModels {
        ApnaBankViewModelFactory((activity?.application as ApnaBankApplication).database.customerDao())
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
           val action = RecieverListFragmentDirections.actionRecieverListFragmentToTransactionFragment(navArgs.senderId,it.id,navArgs.amount)
            findNavController().navigate(action)
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