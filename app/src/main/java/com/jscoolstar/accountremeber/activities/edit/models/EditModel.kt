package com.jscoolstar.accountremeber.activities.edit.models

import com.jscoolstar.accountremeber.activities.BaseModel
import com.jscoolstar.accountremeber.model.beans.Account
import com.jscoolstar.accountremeber.model.beans.Cate

interface EditModel :BaseModel{

    fun getCatesOfCurrectUser():ArrayList<Cate>
    fun addCate(cate: Cate):Boolean
    fun addOrUpdateAccount(account: Account):Boolean
    fun judgePermissonIfRigth(password: String):Boolean

}