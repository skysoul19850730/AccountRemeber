package com.jscoolstar.accountremeber.activities.home.models

import com.jscoolstar.accountremeber.dataprovider.dataentity.Account
import com.jscoolstar.accountremeber.dataprovider.dataentity.User

interface MainModel {


    fun getAllAccounts(userId:Int):List<Account>
    fun deleteAccounts(accounts:List<Account>):Boolean


    fun getCurrectUser():User?

    fun getUserReTryTimesAndLastWrongTime(userName: String):Pair<Int,String>

}