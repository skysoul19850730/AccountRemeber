package com.jscoolstar.accountremeber.binding

import android.text.TextUtils
import android.view.View
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("errorMsg")
fun TextInputLayout.errorMsg(text: String?) {
    if(TextUtils.isEmpty(text)){
        isErrorEnabled = false
    }else{
        isCounterEnabled = true
        error = text
    }
}