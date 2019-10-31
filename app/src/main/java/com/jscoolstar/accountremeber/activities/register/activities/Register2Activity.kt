package com.jscoolstar.accountremeber.activities.register.activities

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.RadioGroup
import com.jscoolstar.accountremeber.R
import com.jscoolstar.accountremeber.activities.home.activities.MainActivity
import com.jscoolstar.accountremeber.activities.register.models.Register2ModelImpl
import com.jscoolstar.accountremeber.activities.register.presenters.IRegisterPresenter2
import com.jscoolstar.accountremeber.activities.register.presenters.RegisterPresenter2Impl
import com.jscoolstar.accountremeber.activities.register.views.Register2View
import com.jscoolstar.accountremeber.apps.BaseActivity
import com.jscoolstar.jscoolstarlibrary.widgets.dialog.JSMaterialDialogClickListerner
import com.jscoolstar.jscoolstarlibrary.widgets.dialog.JSMaterialDialogUtil
import kotlinx.android.synthetic.main.register2_act.*
import kotlinx.android.synthetic.main.register_act.view.*

class Register2Activity:BaseActivity<IRegisterPresenter2>(), Register2View {


    override lateinit var presenter: IRegisterPresenter2
    override fun getLayoutId(): Int {
        return R.layout.register2_act
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = RegisterPresenter2Impl(this,Register2ModelImpl())
        tv_ok.setOnClickListener { presenter.onOkClick(et_username.text.toString(),et_password.text.toString(),switch_password.isChecked) }

        switch_password.setOnCheckedChangeListener { _, isChecked ->
            freshPasswordUI()
        }

    }
    private fun freshPasswordUI(){
        if(switch_password.isChecked){
            layout_password.visibility = View.GONE
        }else{
            layout_password.visibility = View.VISIBLE
        }
    }
    override fun uiShowHome() {
       finish()
    }

    override fun finish() {
        super.finish()
        startActivity(Intent(this,MainActivity::class.java))
    }

    override fun uiSwithOpen(open: Boolean) {
        switch_password.isChecked = open
        freshPasswordUI()
    }

    override fun uiShowNoAccountPasswordTipDialot(method:() -> Unit) {
        JSMaterialDialogUtil.INSTANCE.build(this).showDialog(R.string.register2_dialog_nopassword_title
        ,null,R.string.register2_dialog_nopassword_content,R.string.dialog_cancel,R.string.dialog_ok,object :JSMaterialDialogClickListerner{
            override fun onCancelClick(dialogInterface: DialogInterface, which: Int) {
            }

            override fun onConfirmClick(dialogInterface: DialogInterface, which: Int) {
                method()
            }
        })
    }

}