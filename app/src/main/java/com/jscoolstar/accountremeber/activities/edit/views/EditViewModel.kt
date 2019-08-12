package com.jscoolstar.accountremeber.activities.edit.views

import com.jscoolstar.accountremeber.activities.BaseView
import com.jscoolstar.accountremeber.activities.edit.presenters.IEditPresenter
import com.jscoolstar.accountremeber.dataprovider.dataentity.Cate

interface EditViewModel:BaseView<IEditPresenter> {
    fun uiOnAddOrUpdateFinished(added:Boolean)
    fun uiOnAddCateSuc(cate: Cate?)

}