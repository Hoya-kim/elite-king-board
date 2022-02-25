package com.example.elitekingboard.ui.main.view.fragment.login

import androidx.lifecycle.Observer
import com.example.elitekingboard.R
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
            loginViewModel.signUpResponse.observe(viewLifecycleOwner, Observer { resource ->
                when(resource.status) {
                    Resource.Status.SUCCESS -> {
                        when(resource.data?.code()) {
                            200 -> {
//                                resource.data.headers()
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
    }

}
