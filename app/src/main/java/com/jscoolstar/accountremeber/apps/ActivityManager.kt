package com.jscoolstar.accountremeber.apps

import android.content.Context
import android.content.Intent
import com.jscoolstar.accountremeber.activities.home.activities.MainActivity
import com.jscoolstar.accountremeber.activities.login.activities.LoginActivity

object ActivityManager {


    fun goHome(context: Context){
        context.startActivity(Intent(context,MainActivity::class.java))
    }
    fun goLogin(context: Context){
        context.startActivity(Intent(context,LoginActivity::class.java))
    }

}