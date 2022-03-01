package com.example.elitekingboard.ui.main.view.fragment.login

import android.util.Log
import androidx.lifecycle.Observer
import com.example.elitekingboard.R
import com.example.elitekingboard.data.dto.request.SignUpRequest
import com.example.elitekingboard.databinding.FragmentSignupBinding
import com.example.elitekingboard.ui.base.BaseFragment
import com.example.elitekingboard.ui.main.view.dialog.ProgressDialog
import com.example.elitekingboard.ui.main.view.viewmodel.LoginViewModel
import com.example.elitekingboard.util.Resource
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SignUpFragment : BaseFragment<FragmentSignupBinding>(R.layout.fragment_signup) {
    private val loginViewModel: LoginViewModel by sharedViewModel()
    private val progressDialog: ProgressDialog by lazy { ProgressDialog(requireContext()) }

    override fun init() {
        super.init()
        requestSignUpData()
    }

    private fun requestSignUpData() {

        binding.btnSignUp.setOnClickListener {
            loginViewModel.requestSignUp(
                SignUpRequest(
                    binding.edtNickname.editText!!.text.toString(),
                    binding.edtPassword.editText!!.text.toString(),
                    binding.edtEmail.editText!!.text.toString()
                )
            )
            loginViewModel.requestSignUp(SignUpRequest(
                binding.edtNickname.editText!!.text.toString(),
                binding.edtPassword.editText!!.text.toString(),
                binding.edtEmail.editText!!.text.toString()
            )).observe(viewLifecycleOwner, Observer { resource ->
                when(resource.status) {
                    Resource.Status.SUCCESS -> {
                        progressDialog.dismiss()
                        toast(requireContext(), "이메일로 인증메일을 전송하였습니다.")
//                                resource.data.headers()
                        Log.d("TAG", "requestSignUpData: ")
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
    }

}
