package com.ace.mynote.ui.loginaccount

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ace.mynote.R
import com.ace.mynote.data.local.database.user.AccountEntity
import com.ace.mynote.databinding.FragmentLoginAccountBinding
import com.ace.mynote.di.ServiceLocator
import com.ace.mynote.utils.viewModelFactory
import com.ace.mynote.wrapper.Resource

class LoginAccountFragment : Fragment() {

    private var _binding: FragmentLoginAccountBinding? = null
    private val binding get() = _binding!!


    private val viewModel: LoginAccountViewModel by viewModelFactory {
        LoginAccountViewModel(ServiceLocator.provideServiceLocator(requireContext()))
    }


    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = requireContext().getSharedPreferences(LOGIN_SHARED_PREF,
            Context.MODE_PRIVATE
        )

        binding.btnLogin.setOnClickListener { checkLogin() }

        if (isLoginInfoValid()) {
            checkLogin()
            goToHome()
        }

        binding.tvGotoRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginAccountFragment_to_createAccountFragment)
        }
    }

    private fun goToHome() {
        findNavController().navigate(R.id.action_loginAccountFragment_to_homePageFragment)
    }

    private fun checkLogin() {
        if (validateInput()) {
            val username = binding.etUsername.text.toString()
            viewModel.getUser(username)

            viewModel.getUser.observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Success -> checkAccount(it.payload)
                    is Resource.Error ->
                        Toast.makeText(
                            requireContext(),
                            "error getting data",
                            Toast.LENGTH_SHORT
                        ).show()
                    else -> {}
                }
            }
        }
    }

    private fun validateInput(): Boolean {
        var isValid = true
        val username = binding.etUsername.text.toString()
        val password = binding.etPassword.text.toString()

        if (username.isEmpty()) {
            isValid = false
            binding.etUsername.error = getString(R.string.username_is_empty)
        }
        if (password.isEmpty()) {
            isValid = false
            binding.etPassword.error = getString(R.string.password_is_empty)
        }
        return isValid
    }

    private fun checkAccount(account: AccountEntity?) {
        account?.let {
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()

            val userLoggedIn = username == account.username && password == account.password
            if (userLoggedIn) {
                goToHome()
            } else {
                Toast.makeText(
                    context,
                    getString(R.string.username_or_password_incorrect),
                    Toast.LENGTH_SHORT
                ).show()
            }
            saveLoginInfo(userLoggedIn, account.username, account.accountId)
        }
    }

    private fun saveLoginInfo(loginInfo: Boolean, username: String, accountId: Long) {
        sharedPreferences.edit {
            putBoolean(LOGGED_IN_KEY, loginInfo)
            putString(USERNAME, username)
            putLong(ACCOUNT_ID, accountId)
        }
    }

    private fun isLoginInfoValid(): Boolean {
        return sharedPreferences.getBoolean(LOGGED_IN_KEY, false)
    }

    companion object {
        const val LOGIN_SHARED_PREF = "login_shared_pref"
        const val LOGGED_IN_KEY = "logged_in"
        const val USERNAME = "username"
        const val ACCOUNT_ID = "account_id"
    }
}
