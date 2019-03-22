package com.jscoolstar.accountremeber.apps

import android.app.Application
import android.content.Context
import com.jscoolstar.accountremeber.dataprovider.UserModelImpl
import com.jscoolstar.accountremeber.dataprovider.dataentity.User
import com.jscoolstar.accountremeber.utils.SharedPreferencesManager

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

    var user: User? = null
        get() {
            if (field != null) return field
            else {
                var lastId = SharedPreferencesManager.getInt(SharedPreferencesManager.userid)
                if(lastId==0)return null
                else return UserModelImpl().getUserById(lastId)
            }
        }
}