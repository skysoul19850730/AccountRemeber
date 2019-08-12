package com.jscoolstar.accountremeber.activities.edit.presenters

import com.jscoolstar.accountremeber.activities.edit.views.EditViewModel
import com.jscoolstar.accountremeber.apps.MApplication
import com.jscoolstar.accountremeber.dataprovider.AccountModelImpl
import com.jscoolstar.accountremeber.dataprovider.CateModel
import com.jscoolstar.accountremeber.dataprovider.CateModelImpl
import com.jscoolstar.accountremeber.dataprovider.dataentity.Account
import com.jscoolstar.accountremeber.dataprovider.dataentity.Cate
import com.jscoolstar.accountremeber.dataprovider.dataentity.User

class EditPresenterImpl(val viewModel: EditViewModel, val cateModel: CateModel) : IEditPresenter {


    override fun start() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun destory() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getUser(): User? {
        return MApplication.getInstance().user
    }

    override fun getDefaultCate(): Cate {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addCateWithName(name: String) {
        var cate: Cate? = Cate()
        if (name.length == 0) cate = null
        else {
            cate!!.cateName = name
            cate!!.userId = MApplication.getInstance().user!!.userId
            if (CateModelImpl().addCate(cate!!)) {
            } else {
                cate = null
            }
        }
        return viewModel.uiOnAddCateSuc(cate)
    }

    override fun saveAccount(account: Account) {
        var result = AccountModelImpl().addAccount(MApplication.getInstance().user!!.userId,account!!)
        viewModel.uiOnAddOrUpdateFinished(result)
    }
}