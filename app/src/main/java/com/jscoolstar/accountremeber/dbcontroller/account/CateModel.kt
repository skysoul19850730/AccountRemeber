package com.jscoolstar.accountremeber.dbcontroller.account

import com.jscoolstar.accountremeber.db.entity.DMCate

interface CateModel {
    fun getAllCates():List<DMCate>
    fun addOneCate(cate:DMCate):Boolean
    fun getCateByID(id:Int):DMCate?
}