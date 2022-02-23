package com.example.elitekingboard.ui.main.view.dialog

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.ColorDrawable
import android.view.Window
import com.example.elitekingboard.R
import com.example.elitekingboard.databinding.DialogLoadingBinding
import com.example.elitekingboard.ui.base.BaseDialog

class ProgressDialog(context: Context) : BaseDialog<DialogLoadingBinding>(context, R.layout.dialog_loading) {

    override fun init() {
        super.init()
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        with(binding.pbLoading.drawable as AnimationDrawable) {
            start()
        }
    }

}
