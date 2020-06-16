package com.jscoolstar.accountremeber.activities.edit.models

import com.jscoolstar.accountremeber.dataprovider.AccountModelImpl
import com.jscoolstar.accountremeber.dataprovider.CateModelImpl
import com.jscoolstar.accountremeber.dataprovider.UserModelImpl
import com.jscoolstar.accountremeber.model.beans.Account
import com.jscoolstar.accountremeber.model.beans.Cate

class EditModelImpl:EditModel {
    override fun getCatesOfCurrectUser(): ArrayList<Cate> {
        return CateModelImpl().getAllCaesByUserid(getCurrectUser().userId)
    }

    override fun addCate(cate: Cate): Boolean {
        return CateModelImpl().addCate(cate)
    }

    override fun addOrUpdateAccount(account: Account): Boolean {
        return AccountModelImpl().addAccount(getCurrectUser().userId,account)
    }

    override fun judgePermissonIfRigth(password: String): Boolean {
        return UserModelImpl().isAccountViewPasswordCorrect(getCurrectUser().userId,password)
    }
}