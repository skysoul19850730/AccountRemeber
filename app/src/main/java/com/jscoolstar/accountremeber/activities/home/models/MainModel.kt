package com.jscoolstar.accountremeber.activities.home.models

import com.jscoolstar.accountremeber.model.beans.Account
import com.jscoolstar.accountremeber.model.beans.User

interface MainModel {


    fun getAllAccounts(userId:Int):List<Account>
    fun deleteAccounts(accounts:List<Account>):Boolean


    fun getCurrectUser(): User?

    fun getUserReTryTimesAndLastWrongTime(userName: String):Pair<Int,String>

}