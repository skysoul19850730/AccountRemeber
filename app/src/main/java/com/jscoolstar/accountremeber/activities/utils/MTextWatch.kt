package com.jscoolstar.accountremeber.activities.utils

import com.google.android.material.textfield.TextInputLayout
import android.text.Editable
import android.text.TextWatcher

class MTextWatch : TextWatcher {
    var mEt: TextInputLayout

    constructor(et: TextInputLayout) {
        mEt = et
    }

    override fun afterTextChanged(s: Editable?) {
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        mEt.isErrorEnabled = false
    }
}