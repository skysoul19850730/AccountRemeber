package com.jscoolstar.accountremeber.apps

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.jscoolstar.accountremeber.dataprovider.UserModelImpl
import com.jscoolstar.accountremeber.dataprovider.dataentity.User
import com.jscoolstar.accountremeber.utils.SharedPreferencesManager
import com.skysoul.utils.logs.Print

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

    private var activities = ArrayList<Activity>()


    fun addActivity(activity:Activity){
        activities.add(activity)
    }


    override fun onCreate() {
        super.onCreate()
        mApplication = this
        context = this
        Print.Init(true,null)
    }


    fun getContext(): Context {
        return context
    }

}