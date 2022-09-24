package com.ace.mynote.presentation.ui.loginaccount

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import com.ace.mynote.R
import com.ace.mynote.databinding.FragmentLoginAccountBinding
import com.ace.mynote.di.ServiceLocator
import com.ace.mynote.utils.viewModelFactory

class LoginAccountFragment : Fragment() {

    private lateinit var binding: FragmentLoginAccountBinding
    private var listener : OnAppKeyConfirmedListener? = null

    fun setListener(listener: OnAppKeyConfirmedListener){
        this.listener = listener
    }

    private val viewModel: LoginAccountViewModel by viewModelFactory {
        LoginAccountViewModel(ServiceLocator.provideLocalRepository(requireContext()))
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

    private fun checkPassword() {
        if (validateForm()) {
            val appKey = binding.etPassword.text.toString().trim()
            val isAppKeyCorrect = viewModel.checkIsAppKeyCorrect(appKey)
            listener?.onAppKeyConfirmed(isAppKeyCorrect)
            if(isAppKeyCorrect){
                findNavController().navigate(R.id.action_loginAccountFragment_to_homePageFragment)
            }else{
                Toast.makeText(requireContext(), "Incorrect Password", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setClickListeners() {
        binding.btnLogin.setOnClickListener { checkPassword() }
        binding.tvGotoLogin.setOnClickListener {
            it.findNavController().navigate(R.id.action_loginAccountFragment_to_createAccountFragment)
        }
    }

    private fun validateForm(): Boolean {
        val appKey = binding.etPassword.text.toString()
        var isFormValid = true
        if (appKey.isEmpty()) {
            isFormValid = false
            binding.tilPassword.isErrorEnabled = true
            binding.tilPassword.error = "Password is Empty!"
        } else {
            binding.tilPassword.isErrorEnabled = false
        }
        return isFormValid
    }
}

interface OnAppKeyConfirmedListener{
    fun onAppKeyConfirmed(isAppKeyCorrect : Boolean)
}
