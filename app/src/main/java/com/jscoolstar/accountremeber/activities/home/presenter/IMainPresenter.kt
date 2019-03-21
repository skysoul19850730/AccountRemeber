package com.jscoolstar.accountremeber.activities.home.presenter

import android.content.Intent
import com.jscoolstar.accountremeber.activities.BasePresenter
import com.jscoolstar.accountremeber.dataprovider.dataentity.Account

interface IMainPresenter :BasePresenter{

    fun deleteChoosedAccounts()
    fun onEditClick()
    fun onItemLongClick(position:Int)
    fun onItemClick(position: Int)

    fun uiPreIntoDetail(account: Account)

    fun uiEditAccountTask(account: Account)
    fun uiSearchTask()
    fun uiSettingTask()
    fun uiAddAccountTask()

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
}