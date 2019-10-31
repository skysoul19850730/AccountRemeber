package com.jscoolstar.accountremeber.activities.edit.presenters

import android.content.Intent
import android.text.TextUtils
import com.jscoolstar.accountremeber.R
import com.jscoolstar.accountremeber.activities.edit.models.EditModel
import com.jscoolstar.accountremeber.activities.edit.views.EditViewModel
import com.jscoolstar.accountremeber.apps.MApplication
import com.jscoolstar.accountremeber.apps.UserInfoManager
import com.jscoolstar.accountremeber.dataprovider.AccountModelImpl
import com.jscoolstar.accountremeber.dataprovider.CateModel
import com.jscoolstar.accountremeber.dataprovider.CateModelImpl
import com.jscoolstar.accountremeber.dataprovider.dataentity.Account
import com.jscoolstar.accountremeber.dataprovider.dataentity.Cate
import com.jscoolstar.accountremeber.dataprovider.dataentity.ExtraColumn
import com.jscoolstar.accountremeber.dataprovider.dataentity.User

class EditPresenterImpl(val viewModel: EditViewModel, val editModel: EditModel) : IEditPresenter {
    var account: Account? = null
    var extralColumns: ArrayList<ExtraColumn> = arrayListOf()
    var cates: ArrayList<Cate> = arrayListOf()
    var cateSelectIndex = 0
    var isEditState = true

    override fun initDatas(intent: Intent) {
        account = intent.getParcelableExtra("account")
        if (account != null) {
            viewModel.fillUIWithAccount(account!!)
        } else {
            account = Account()
            isEditState = false
        }
        extralColumns = account?.extraColumnList ?: ArrayList()
        viewModel.initExtraColumnUI(extralColumns)
        cates = editModel.getCatesOfCurrectUser()
        viewModel.initCatesAdapter(cates)
        var cate = account!!.cate
        if (cate == null) {
            cate = cates.get(0)
        }
        dealCurrectCate(cate)
    }

    private fun dealCurrectCate(cate: Cate) {
        for ((index, c) in cates.withIndex()) {
            if (c.id == cate.id) {
                viewModel.selectSpinnerIndex(index)
                cateSelectIndex = index
                return
            }
        }
    }

    override fun judgePermissonIfShow() {
        viewModel.showPermissonUI(editModel.getCurrectUser().isAccountViewPasswordSetted)
    }

    override fun judgePermissonIfRigth(password: String) {
        if (editModel.judgePermissonIfRigth(password)) {
            viewModel.showPermissonUI(false)
        } else {
            viewModel.showToast(R.string.edit_password_wrong)
            viewModel.showPermissonUI(true)
        }
    }


    override fun addCateWithName(name: String, addResult: (suc: Boolean) -> Unit) {
        if (name.length == 0) {
            viewModel.showToast(R.string.edit_catename_not_null)
            return
        }
        var cate: Cate = Cate()
        cate.cateName = name
        cate.userId = editModel.getCurrectUser().userId
        if (CateModelImpl().addCate(cate!!)) {
            addResult(true)
            cates.add(cate)
            dealCurrectCate(cate)
            return
        } else {
            viewModel.showToast(R.string.edit_catename_exist)
            addResult(false)
        }
    }

    override fun deleteExtraColumn(position: Int, methodFresh: () -> Unit) {
        extralColumns.removeAt(position)
        methodFresh()
    }

    override fun addExtraColumn(extra: ExtraColumn, methodFresh: () -> Unit) {
        extralColumns.add(extra)
        methodFresh()
    }

    override fun start() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun destory() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getUser(): User {
        return UserInfoManager.getInstance().getUser()
    }

    override fun getDefaultCate(): Cate {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun saveAccount(backme: (acc: Account) -> Account) {
        var acc2 = backme(account!!.clone())
        acc2.extraColumnList = extralColumns
        acc2.cate = cates.get(cateSelectIndex)
        var result = AccountModelImpl().addAccount(UserInfoManager.getInstance().getUser().userId, acc2)
        if (result)
        {
            var intent = Intent()
            intent.putExtra("account",acc2)
            intent.putExtra("isEditState",isEditState)
            viewModel.uiOnAddOrUpdateFinished(intent)
        }else{
            viewModel.showToast(R.string.edit_account_added_fail)
        }
    }

}