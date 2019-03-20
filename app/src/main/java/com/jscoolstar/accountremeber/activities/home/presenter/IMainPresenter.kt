package com.jscoolstar.accountremeber.activities.home.presenter

import com.jscoolstar.accountremeber.activities.BasePresenter
import com.jscoolstar.accountremeber.dataprovider.dataentity.Account

interface IMainPresenter :BasePresenter{

    fun deleteChoosedAccounts()


    fun uiEditAccountTask(account: Account)
    fun uiSearchTask()
    fun uiSettingTask()
    fun uiAddAccountTask()
    fun uiShowDetailTips(account: Account)
}