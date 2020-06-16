package com.jscoolstar.accountremeber.activities.edit.views

import android.content.Intent
import com.jscoolstar.accountremeber.activities.BaseView
import com.jscoolstar.accountremeber.activities.edit.presenters.IEditPresenter
import com.jscoolstar.accountremeber.model.beans.Account
import com.jscoolstar.accountremeber.model.beans.Cate
import com.jscoolstar.accountremeber.model.beans.ExtraColumn

interface EditViewModel : BaseView<IEditPresenter> {
    fun uiOnAddOrUpdateFinished(intent: Intent)

    fun fillUIWithAccount(account: Account)
    fun initExtraColumnUI(extralColumns: ArrayList<ExtraColumn>)
    fun initCatesAdapter(cates: ArrayList<Cate>)
    fun selectSpinnerIndex(position: Int)

    fun showPermissonUI(show:Boolean)
}