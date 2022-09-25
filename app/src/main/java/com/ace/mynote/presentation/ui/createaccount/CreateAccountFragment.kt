package com.ace.mynote.presentation.ui.createaccount

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.ace.mynote.R
import com.ace.mynote.databinding.FragmentCreateAccountBinding
import com.ace.mynote.utils.viewModelFactory

class CreateAccountFragment() : Fragment() {

    private lateinit var binding: FragmentCreateAccountBinding

    private val viewModel: CreateAccountViewModel by viewModelFactory {
        CreateAccountViewModel(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCreateAccountBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickListeners()

    }

    private fun setClickListeners() {
        binding.btnCreateAccount.setOnClickListener { createAccount() }
        binding.tvGotoLogin.setOnClickListener {
            it.findNavController()
                .navigate(R.id.action_createAccountFragment_to_loginAccountFragment)
        }
    }

    private fun createAccount() {
        if (validateForm()) {
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()

            viewModel.setAccount(username,password)
            Toast.makeText(requireActivity(), "Account Created", Toast.LENGTH_SHORT).show()
        }
    }

    private fun validateForm(): Boolean {
        val username = binding.etUsername.text.toString()
        val password = binding.etPassword.text.toString()
        val confirmPassword = binding.etConfirmPassword.text.toString()
        var isFormValid = true
        if (username.isEmpty()) {
            isFormValid = false
            binding.tilUsername.isErrorEnabled = true
            binding.tilUsername.error = "Username is Empty"
        }
        if (password.isEmpty()) {
            isFormValid = false
            binding.tilPassword.isErrorEnabled = true
            binding.tilPassword.error = "Password is Empty"
        }
        if (confirmPassword.isEmpty()) {
            isFormValid = false
            binding.tilConfirmPassword.isErrorEnabled = true
            binding.tilConfirmPassword.error = "Password is Empty"
        } else {
            binding.tilUsername.isErrorEnabled = false
            binding.tilPassword.isErrorEnabled = false
            binding.tilConfirmPassword.isErrorEnabled = false
        }
        if (password != confirmPassword) {
            isFormValid = false
            Toast.makeText(context, "Password doesn't match!",Toast.LENGTH_SHORT).show()
        }
        return isFormValid
    }
}