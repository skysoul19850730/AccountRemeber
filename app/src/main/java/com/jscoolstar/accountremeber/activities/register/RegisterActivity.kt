package com.jscoolstar.accountremeber.activities.register

import androidx.core.widget.addTextChangedListener
import com.jscoolstar.accountremeber.R
import com.jscoolstar.accountremeber.appbases.BaseVMActivity
import kotlinx.android.synthetic.main.register_act.*
import org.koin.androidx.viewmodel.ext.android.getViewModel

class RegisterActivity:BaseVMActivity<RegisterViewModel>(useDataBinding = false) {

    var flag = 0

    override fun getLayoutResId(): Int {
        return R.layout.register_act
    }

    override fun initVM():RegisterViewModel = getViewModel()

    override fun initView() {
        et_username.addTextChangedListener{
            flag++
            et_passwordtip.setText("test:$flag")
        }
    }

    override fun initData() {
    }

    override fun startObserve() {
    }
}