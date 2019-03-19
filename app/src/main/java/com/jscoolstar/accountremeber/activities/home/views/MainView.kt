package com.jscoolstar.accountremeber.activities.home.views

import com.jscoolstar.accountremeber.dataprovider.dataentity.Account

interface MainView {
    fun showMainList()
    fun showEditModel(showEdit:Boolean)
    fun showItemTipDialog(account:Account)
}