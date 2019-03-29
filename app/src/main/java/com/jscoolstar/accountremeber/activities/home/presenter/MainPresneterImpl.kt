package com.jscoolstar.accountremeber.activities.home.presenter

import android.app.Activity
import android.content.Intent
import android.os.Handler
import com.jscoolstar.accountremeber.R
import com.jscoolstar.accountremeber.activities.home.models.MainModel
import com.jscoolstar.accountremeber.activities.home.views.MainView
import com.jscoolstar.accountremeber.apps.MApplication
import com.jscoolstar.accountremeber.dataprovider.dataentity.Account
import com.jscoolstar.accountremeber.dataprovider.dataentity.User
import com.jscoolstar.accountremeber.db.SQL

class MainPresneterImpl(private var mainModel: MainModel, private var mainView: MainView?) : IMainPresenter {
    companion object {
        val REQUESTCODE_CHECKPASSWORD = 1000
        val REQUESTCODE_DETAILEDIT = 1001
        val REQUESTCODE_ADDACCOUNT = 1002
    }

    init {
        mainView?.presenter = this
    }


    var mEditState = false
    var list = arrayListOf<Account>()

    var mCurAccountClicked: Account? = null


    var finishFlag = false

    override fun dealFinish() {
        if (finishFlag) {
            mainView?.finalFinish()
            return
        }
        finishFlag = true
        Handler().postDelayed({ finishFlag = false }, 500)
    }

    override fun destory() {
        mainView = null
    }

    override fun onEditClick() {
        mEditState = !mEditState
        list.forEach { it.isChecked = false }
        mainView?.showEditState(mEditState)
    }

    override fun onItemLongClick(position: Int) {
        uiPreIntoDetail(list.get(position))
    }

    override fun uiPreIntoDetail(account: Account) {
        var user = mainModel.getCurrectUser()
        var (retry, last) = mainModel.getUserReTryTimesAndLastWrongTime(user!!.userName!!)
        if (retry == 0) {
            mainView?.showToast(R.string.password_wrong_moretimes)
            return
        }
        mCurAccountClicked = account
        mainView?.showUIPasswordCheck()
    }

    override fun onItemClick(position: Int) {
        if (!mEditState)
            mainView?.showItemTipDialog(list.get(position))
        else {
            var account = list.get(position)
            account.isChecked = !account.isChecked
            mainView?.notifyData()
        }
    }

    override fun start() {
        list.clear()
        list.addAll(mainModel.getAllAccounts(mainModel.getCurrectUser()!!.userId))
        mainView?.showMainList(list)

        var user = MApplication.getInstance().user
        if(user==null){
            mainView?.showUILogin()
        }else{
            mainView?.showUserInfo(user)
        }
    }

    override fun deleteChoosedAccounts() {
        var list4checked = arrayListOf<Account>()
        for (account in list) {
            if (account.isChecked) {
                list4checked.add(account)
            }
        }
        if (list4checked.size == 0) {
            return
        }

        mainModel.deleteAccounts(list4checked)
        list.removeAll(list4checked)
        mainView?.notifyData()
    }

    override fun uiEditAccountTask(account: Account) {
        mainView?.showUIAccountEdit(account)
    }

    override fun uiSearchTask() {
        mainView?.showUISearchUI()
    }

    override fun uiSettingTask() {
        mainView?.showUISettingUI()
    }

    override fun uiAddAccountTask() {
        mainView?.showUIAddNewAccount()
    }

    override fun uiBackupTask() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun uiRatingTask() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun uiShareTask() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun uiUserTask() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun login_out() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQUESTCODE_CHECKPASSWORD -> {
                if (resultCode == Activity.RESULT_OK) {
                    if (mCurAccountClicked != null)
                        mainView?.showUIAccountEdit(mCurAccountClicked!!)
                }
            }
        }
    }
}