package com.jscoolstar.accountremeber.apps

import android.app.Application
import android.content.Context

/**
 * Created by Administrator on 2018/4/4.
 */
class MApplication : Application() {
    companion object INSTANCE {
        private lateinit var mApplication: MApplication
        private lateinit var context: Context
        fun getInstance(): MApplication {
            return mApplication
        }
    }

    override fun onCreate() {
        super.onCreate()
        mApplication = this
        context = this
    }


    fun getContext(): Context {
        return context
    }
}