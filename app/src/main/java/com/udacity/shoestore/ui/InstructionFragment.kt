package com.udacity.shoestore.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.udacity.shoestore.LoginViewModel
import com.udacity.shoestore.R
import com.udacity.shoestore.databinding.FragmentInstructionBinding

class InstructionFragment : Fragment() {

    private lateinit var binding: FragmentInstructionBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_instruction, container, false)

        initViewModel()
        initObservers()

        (activity as AppCompatActivity).supportActionBar?.hide()

        return binding.root
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(requireActivity()).get(LoginViewModel::class.java)

        binding.instructionViewModel = viewModel
    }

    private fun initObservers() {
        viewModel.eventNextInstructionDetailPress.observe(viewLifecycleOwner) {
            if (it) {
                goToShoeList()
                viewModel.goToShoeListComplete()
            }
        }
    }

    private fun goToShoeList() {
        findNavController().navigate(R.id.action_instructionFragment_to_shoeListFragment)
    }
}