package com.jscoolstar.accountremeber.models.account

import com.jscoolstar.accountremeber.db.entity.Cate

interface CateModel {
    fun getAllCates():List<Cate>
    fun addOneCate(cate:Cate):Boolean
    fun getCateByID(id:Int):Cate?
}