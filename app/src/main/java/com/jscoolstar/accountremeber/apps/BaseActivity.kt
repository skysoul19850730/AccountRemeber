package com.jscoolstar.accountremeber.apps

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.jscoolstar.accountremeber.activities.BasePresenter
import com.jscoolstar.accountremeber.activities.BaseView
import com.jscoolstar.accountremeber.utils.log

abstract class BaseActivity<T> : AppCompatActivity(), BaseView<T> {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        log("onCreate")
    }


    override fun showToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show()
    }

    override fun showToast(textId: Int) {
        Toast.makeText(this, textId, Toast.LENGTH_LONG).show()
    }

    abstract fun getLayoutId():Int
}