package com.jscoolstar.accountremeber.activities.login.activities

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.WindowManager
import android.widget.EditText
import android.widget.Toast
import com.jscoolstar.accountremeber.R
import com.jscoolstar.accountremeber.activities.home.activities.MainActivity
import com.jscoolstar.accountremeber.activities.login.models.LoginModelImpl
import com.jscoolstar.accountremeber.activities.login.presenter.ILoginPresenter
import com.jscoolstar.accountremeber.activities.login.presenter.LoginPresenterImpl
import com.jscoolstar.accountremeber.activities.login.views.LoginViewModel
import kotlinx.android.synthetic.main.loginact.*

class LoginActivity:AppCompatActivity(),LoginViewModel {
    override lateinit var presenter: ILoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        setContentView(R.layout.loginact)
        presenter = LoginPresenterImpl(this,LoginModelImpl())
        presenter.start()
        btn_login.setOnClickListener { presenter.onLoginClick(et_username.text.toString(),et_password.text.toString()) }
        tv_register.setOnClickListener { presenter.onRegisterClick() }
        et_username.addTextChangedListener(MTextWatch(layout_username))
        et_password.addTextChangedListener(MTextWatch(layout_password))
    }

    private class MTextWatch : TextWatcher{
        var mEt: TextInputLayout
        constructor(et:TextInputLayout){
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

    override fun uiShowRegister() {
        showToast("去注册")
    }

    override fun uiShowHome() {
        layout_password.isErrorEnabled = false
        et_password.setText("")
        layout_username.isErrorEnabled = false
        et_username.setText("")
        startActivity(Intent(this,MainActivity::class.java))
    }

    override fun showUIWithUser(userName: String?) {
        if(userName!=null && !userName.isEmpty()){
            et_username.setText(userName)
        }
    }

    override fun showPasswordWrong(msg: String) {

        layout_password.isErrorEnabled = true
        layout_password.error = msg

    }

    override fun showUserNameWrong(msg: String) {
        layout_username.isErrorEnabled = true
        layout_username.error = msg
    }



    override fun showToast(text: String) {
        Toast.makeText(this,text,Toast.LENGTH_LONG).show()
    }

    override fun showToast(textId: Int) {
        Toast.makeText(this,textId,Toast.LENGTH_LONG).show()
    }
}