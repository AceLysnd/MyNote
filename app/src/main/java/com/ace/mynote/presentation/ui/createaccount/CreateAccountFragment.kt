package com.ace.mynote.presentation.ui.createaccount

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import com.ace.mynote.R
import com.ace.mynote.databinding.FragmentCreateAccountBinding
import com.ace.mynote.di.ServiceLocator
import com.ace.mynote.utils.viewModelFactory

class CreateAccountFragment : Fragment() {

    private lateinit var binding: FragmentCreateAccountBinding
    private var listener: OnAppKeyChangedListener? = null

    private val viewModel: CreateAccountViewModel by viewModelFactory {
        CreateAccountViewModel(ServiceLocator.provideLocalRepository(requireContext()))
    }

    fun setListener(listener: OnAppKeyChangedListener){
        this.listener = listener
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
        binding.btnCreateAccount.setOnClickListener { changeAppKey() }
        binding.tvGotoLogin.setOnClickListener {
            it.findNavController().navigate(R.id.action_createAccountFragment_to_loginAccountFragment)
        }
    }

    private fun changeAppKey() {
        if (validateForm()) {
            val newAppKey = binding.etPassword.text.toString().trim()
            context?.let { viewModel.setAppKey(newAppKey) }
            listener?.onAppKeyChanged()

        }
    }

    private fun validateForm(): Boolean {
        val appKey = binding.etPassword.text.toString()
        val confirmedAppKey = binding.etConfirmPassword.text.toString()
        var isFormValid = true

        if (appKey.isEmpty()) {
            isFormValid = false
            binding.tilPassword.isErrorEnabled = true
            binding.tilPassword.error = "getString(R.string.error_empty_app_key)"
        } else {
            binding.tilPassword.isErrorEnabled = false
        }
        if (confirmedAppKey.isEmpty()) {
            isFormValid = false
            binding.tilConfirmPassword.isErrorEnabled = true
            binding.tilConfirmPassword.error = "getString(R.string.error_empty_confirmed_app_key)"
        } else {
            binding.tilConfirmPassword.isErrorEnabled = false
        }
        if (appKey != confirmedAppKey) {
            isFormValid = false
            Toast.makeText(
                context,
                "getString(R.string.error_app_key_not_match)",
                Toast.LENGTH_SHORT
            ).show()
        }
        return isFormValid
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is OnAppKeyChangedListener){
            listener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }
}

interface OnAppKeyChangedListener{
    fun onAppKeyChanged()
}