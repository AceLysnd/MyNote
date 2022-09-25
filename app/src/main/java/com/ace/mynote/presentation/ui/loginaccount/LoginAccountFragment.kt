package com.ace.mynote.presentation.ui.loginaccount

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.ace.mynote.R
import com.ace.mynote.databinding.FragmentLoginAccountBinding
import com.ace.mynote.utils.viewModelFactory

class LoginAccountFragment : Fragment() {

    private lateinit var binding: FragmentLoginAccountBinding

    private val viewModel: LoginAccountViewModel by viewModelFactory {
        LoginAccountViewModel(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentLoginAccountBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickListeners()
    }

    private fun setClickListeners() {
        binding.btnLogin.setOnClickListener { checkAccount() }
        binding.tvGotoRegister.setOnClickListener {
            it.findNavController().navigate(R.id.action_loginAccountFragment_to_createAccountFragment)
        }
    }

    private fun checkAccount(){
        if (validateForm()) {
            val username = binding.etUsername.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            val isUsernameCorrect = viewModel.checkIsUsernameCorrect(username)
            val isPasswordCorrect = viewModel.checkIsPasswordCorrect(password)
            if(isUsernameCorrect && isPasswordCorrect){
                findNavController().navigate(R.id.action_loginAccountFragment_to_homePageFragment)
            }else{
                Toast.makeText(requireContext(), "Password or Username Incorrect", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validateForm(): Boolean {
        val username = binding.etUsername.text.toString()
        val password = binding.etPassword.text.toString()
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
        } else {
            binding.tilUsername.isErrorEnabled = false
            binding.tilPassword.isErrorEnabled = false
        }
        return isFormValid
    }
}