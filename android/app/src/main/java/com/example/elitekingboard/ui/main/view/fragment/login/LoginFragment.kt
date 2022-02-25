package com.example.elitekingboard.ui.main.view.fragment.login

import android.content.Intent
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.elitekingboard.R
import com.example.elitekingboard.data.dto.request.LoginRequest
import com.example.elitekingboard.data.dto.response.LoginResponse
import com.example.elitekingboard.data.preference.MySharedPreferences
import com.example.elitekingboard.databinding.FragmentLoginBinding
import com.example.elitekingboard.ui.base.BaseFragment
import com.example.elitekingboard.ui.main.view.activity.MainActivity
import com.example.elitekingboard.ui.main.view.dialog.ProgressDialog
import com.example.elitekingboard.ui.main.view.viewmodel.LoginViewModel
import com.example.elitekingboard.util.Resource
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class LoginFragment : BaseFragment<FragmentLoginBinding>(R.layout.fragment_login) {
    private var loginResponse = LoginResponse()
    private var loginRequest = LoginRequest()
    private val loginViewModel: LoginViewModel by sharedViewModel()
    private val progressDialog: ProgressDialog by lazy { ProgressDialog(requireContext()) }

    override fun init() {
        super.init()
        clickBtnSignUp()
        clickBtnLogin()
        checkAutoLogin()
        checkBox()
    }

    private fun clickBtnSignUp() {
        binding.btnSignup.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToSignUpFragment())
        }
    }

    private fun clickBtnLogin() {
        binding.btnLogin.setOnClickListener {
            requestLoginApi()
        }
    }

    private fun requestLoginApi() {
        loginViewModel.loginResponse.observe(viewLifecycleOwner, Observer { resource ->
            when(resource.status) {
                Resource.Status.SUCCESS -> {
                    when(resource.data?.code()) {
                        200 -> {
                            MySharedPreferences.setUserId(requireContext(), binding.edtId.editText!!.text.toString())
                            MySharedPreferences.setUserPass(requireContext(), binding.edtPassword.editText!!.text.toString())
//                           MySharedPreferences.setJwt(requireContext(), loginResponse.result.jwt)
                            startActivity(Intent(requireContext(), MainActivity::class.java))
                            requireActivity().finish()
                        }
                    }
                }
                Resource.Status.LOADING -> {
                    progressDialog.show()
                }
                Resource.Status.ERROR -> {
                    progressDialog.dismiss()
                    toast(requireContext(), "${resource.message}")
                }
            }
        })
    }

    // 자동 로그인
    private fun checkAutoLogin() {
        if (MySharedPreferences.getCheck(requireContext()) &&
            MySharedPreferences.getUserId(requireContext()).isNotBlank() &&
            MySharedPreferences.getUserPass(requireContext()).isNotBlank()
        ) {
            binding.edtId.editText!!.setText(MySharedPreferences.getUserId(requireContext()))
            binding.edtPassword.editText!!.setText(MySharedPreferences.getUserPass(requireContext()))
            binding.checkBox.isChecked = true
            loginRequest.email = binding.edtId.editText!!.text.toString()
            loginRequest.password = binding.edtPassword.editText!!.text.toString()
            requestLoginApi()
        }
    }

    private fun checkBox() {
        binding.checkBox.setOnCheckedChangeListener { compoundButton, checked ->
            if (checked) {
                MySharedPreferences.setCheck(requireContext(), binding.checkBox.isChecked)
            } else {
                MySharedPreferences.setCheck(requireContext(), binding.checkBox.isChecked)
                MySharedPreferences.clearUser(requireContext())
            }
        }
    }

}
