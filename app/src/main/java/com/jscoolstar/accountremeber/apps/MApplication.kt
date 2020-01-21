package com.jscoolstar.accountremeber.apps

import android.app.Activity
import android.app.Application
import android.content.Context
import com.jscoolstar.accountremeber.utils.log
import com.jscoolstar.accountremeber.utils.logE
import com.skysoul.utils.logs.Print
import java.lang.Exception

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
        logE("addActivity：$activity");
        activities.add(activity)
    }
    fun removeCurrentActivity(activity: Activity) {
        try {
            logE("removeCurrentActivity：$activity");
            activities.remove(activity)
        } catch (e: Exception) {
            e.printStackTrace()
        }

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

    fun exitProgram(){
        log("exitProgram：");
        try {
            for(act in activities){
                log("exitProgram ：removeActivityList : $act");
                act.finish()
            }
            KillApp()
        }catch (e :Exception){
            e.printStackTrace()
        }
    }

    private fun KillApp(){
        android.os.Process.killProcess(android.os.Process.myPid())
    }

}