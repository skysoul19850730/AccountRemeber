package com.jscoolstar.accountremeber.apps

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jscoolstar.accountremeber.utils.log

abstract class AppActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MApplication.getInstance().addActivity(this)
        log("onCreate")
    }

    override fun onDestroy() {
        super.onDestroy()
        MApplication.getInstance().removeCurrentActivity(this)
    }
}