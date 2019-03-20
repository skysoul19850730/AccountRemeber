package com.jscoolstar.accountremeber.activities.home.models

import com.jscoolstar.accountremeber.dataprovider.dataentity.Account

interface MainModel {


    fun getAllAccounts(userId:Int):List<Account>
    fun deleteAccounts(accounts:List<Account>):Boolean


    fun getCurrectUserId():Int

}