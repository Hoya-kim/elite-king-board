package com.example.elitekingboard.ui.base

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<VB : ViewDataBinding>(private val layoutId: Int) : AppCompatActivity() {
    lateinit var binding: VB
    private val TAG = "BASEACTIVITY"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    protected open fun init() {
        initViewDataBinding()
    }

    protected open fun initViewDataBinding() {
        binding = DataBindingUtil.setContentView(this, layoutId)
    }

    protected open fun toast(context: Context, msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
    }
}