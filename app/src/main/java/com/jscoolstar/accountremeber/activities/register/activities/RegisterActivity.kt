package com.jscoolstar.accountremeber.activities.register.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import com.jscoolstar.accountremeber.R
import com.jscoolstar.accountremeber.activities.home.activities.MainActivity
import com.jscoolstar.accountremeber.activities.register.models.RegisterModelImpl
import com.jscoolstar.accountremeber.activities.register.presenters.IRegisterPresenter
import com.jscoolstar.accountremeber.activities.register.presenters.RegisterPresenterImpl
import com.jscoolstar.accountremeber.activities.register.views.RegisterView
import com.jscoolstar.accountremeber.activities.utils.MTextWatch
import kotlinx.android.synthetic.main.register_act.*

class RegisterActivity :AppCompatActivity(),RegisterView{

    override lateinit var presenter: IRegisterPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_act)
        presenter = RegisterPresenterImpl(this, RegisterModelImpl())
        presenter.start()

        et_username.addTextChangedListener(MTextWatch(layout_username))
        et_password.addTextChangedListener(MTextWatch(layout_password))
        et_password2.addTextChangedListener(MTextWatch(layout_password2))
        btn_register.setOnClickListener { presenter.onRegisterClick(et_username.text.toString(),et_password.text.toString(),et_password2.text.toString(),et_passwordtip.text.toString()) }

        toolbar.setNavigationOnClickListener { finish() }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.destory()
    }

    override fun uiShowHome() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun showPasswordWrong(msg: String) {
        layout_password.isErrorEnabled = true
        layout_password.error = msg
    }

    override fun showPasswordWrong2(msg: String) {
        layout_password2.isErrorEnabled = true
        layout_password2.error = msg
    }

    override fun showUserNameWrong(msg: String) {
        layout_username.isErrorEnabled = true
        layout_username.error = msg
    }



    override fun showToast(text: String) {
        Toast.makeText(this,text, Toast.LENGTH_LONG).show()
}

    override fun showToast(textId: Int) {
        Toast.makeText(this,textId,Toast.LENGTH_LONG).show()
    }
}