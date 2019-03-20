package com.jscoolstar.accountremeber.activities.home.presenter

import com.jscoolstar.accountremeber.activities.home.models.MainModel
import com.jscoolstar.accountremeber.activities.home.views.MainView
import com.jscoolstar.accountremeber.dataprovider.dataentity.Account

class MainPresneterImpl(private var mainModel: MainModel,  private var mainView: MainView):IMainPresenter {
    init {
        mainView.presenter = this
    }

    override fun start() {
        var list = mainModel.getAllAccounts()
    }

    override fun deleteChoosedAccounts() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun uiEditAccountTask(account: Account) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun uiSearchTask() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun uiSettingTask() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun uiAddAccountTask() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun uiShowDetailTips(account: Account) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}