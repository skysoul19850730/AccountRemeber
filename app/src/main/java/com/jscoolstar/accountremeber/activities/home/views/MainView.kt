package com.jscoolstar.accountremeber.activities.home.views

import com.jscoolstar.accountremeber.activities.BaseView
import com.jscoolstar.accountremeber.activities.home.presenter.IMainPresenter
import com.jscoolstar.accountremeber.dataprovider.dataentity.Account
import com.jscoolstar.accountremeber.dataprovider.dataentity.User

interface MainView :BaseView<IMainPresenter>{
    /** if list is null ,then only notify adapter,else adapter.setlist  */
    fun showMainList(list: List<Account>)
    fun notifyData()
    fun showItemTipDialog(account: Account)
    fun showEditState(showEdit: Boolean)
    fun showUserInfo(user:User)


    fun showUIAccountEdit(account:Account)
    fun showUIAddNewAccount()
    fun showUISearchUI()
    fun showUISettingUI()
    fun showUIPasswordCheck()
    fun showUILogin()

    fun finalFinish()


}