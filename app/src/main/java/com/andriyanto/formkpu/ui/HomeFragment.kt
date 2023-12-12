package com.andriyanto.formkpu.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.andriyanto.formkpu.R
import com.andriyanto.formkpu.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding : FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonInformasi.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_informasiFragment)
        }

        binding.buttonForm.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_formFragment)
        }

        binding.buttonData.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_dataFragment)
        }
    }

}