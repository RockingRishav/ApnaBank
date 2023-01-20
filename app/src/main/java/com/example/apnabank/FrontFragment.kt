package com.example.apnabank

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentContainer
import androidx.navigation.findNavController
import com.example.apnabank.databinding.FragmentFrontBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FrontFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FrontFragment : Fragment() {
    private var _binding : FragmentFrontBinding? = null
    private val binding get()  = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater,R.layout.fragment_front,container,false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.currentTimeText.format24Hour = "hh:mm:ss a"
        binding.addCustomer.setOnClickListener {
            val action = FrontFragmentDirections.actionFrontFragmentToAddCustomerFragment()
            view.findNavController().navigate(action)
        }
        binding.viewCustomer.setOnClickListener {
            val action = FrontFragmentDirections.actionFrontFragmentToCustomerListFragment("no")
            view.findNavController().navigate(action)
        }
        binding.sendMoney.setOnClickListener {
            val action = FrontFragmentDirections.actionFrontFragmentToCustomerListFragment("yes")
            view.findNavController().navigate(action)
        }
        binding.transactionHistory.setOnClickListener {
            val action = FrontFragmentDirections.actionFrontFragmentToTransactionFragment(-1,0,"","",null)
            view.findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}