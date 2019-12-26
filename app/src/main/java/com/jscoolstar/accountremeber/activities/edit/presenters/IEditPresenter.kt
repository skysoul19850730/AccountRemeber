package com.jscoolstar.accountremeber.activities.edit.presenters

import android.content.Intent
import com.jscoolstar.accountremeber.activities.BasePresenter
import com.jscoolstar.accountremeber.dataprovider.dataentity.Account
import com.jscoolstar.accountremeber.dataprovider.dataentity.Cate
import com.jscoolstar.accountremeber.dataprovider.dataentity.ExtraColumn
import com.jscoolstar.accountremeber.dataprovider.dataentity.User

interface IEditPresenter : BasePresenter {
    fun getUser(): User
    fun getDefaultCate(): Cate
    fun saveAccount(backme: (account: Account) -> Account)

    fun deleteExtraColumn(position:Int,methodFresh:()->Unit)
    fun addExtraColumn(extra: ExtraColumn, methodFresh:()->Unit)

    fun judgePermissonIfShow()
    fun judgePermissonIfRigth(password: String)

    fun addCateWithName(name: String, addResult: (suc: Boolean) -> Unit)
    fun initDatas(intent: Intent)
    fun selectCateAt(position: Int)
}