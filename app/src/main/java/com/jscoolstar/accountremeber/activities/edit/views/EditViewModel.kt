package com.jscoolstar.accountremeber.activities.edit.views

import android.content.Intent
import com.jscoolstar.accountremeber.activities.BaseView
import com.jscoolstar.accountremeber.activities.edit.presenters.IEditPresenter
import com.jscoolstar.accountremeber.dataprovider.dataentity.Account
import com.jscoolstar.accountremeber.dataprovider.dataentity.Cate
import com.jscoolstar.accountremeber.dataprovider.dataentity.ExtraColumn

interface EditViewModel : BaseView<IEditPresenter> {
    fun uiOnAddOrUpdateFinished(intent: Intent)

    fun fillUIWithAccount(account: Account)
    fun initExtraColumnUI(extralColumns: ArrayList<ExtraColumn>)
    fun initCatesAdapter(cates: ArrayList<Cate>)
    fun selectSpinnerIndex(position: Int)

    fun showPermissonUI(show:Boolean)
}