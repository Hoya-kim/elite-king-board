package com.example.elitekingboard.ui.main.view.fragment.login

import androidx.navigation.fragment.findNavController
import com.example.elitekingboard.R
import com.example.elitekingboard.databinding.FragmentLoginBinding
import com.example.elitekingboard.ui.base.BaseFragment

class LoginFragment : BaseFragment<FragmentLoginBinding>(R.layout.fragment_login) {

    override fun init() {
        super.init()
        clickBtnSignUp()
    }

    private fun clickBtnSignUp() {
        binding.btnSignup.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToSignUpFragment())
        }
    }

}
