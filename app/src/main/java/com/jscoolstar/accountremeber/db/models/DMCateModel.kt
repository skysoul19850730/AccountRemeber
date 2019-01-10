package com.jscoolstar.accountremeber.db.models

import com.jscoolstar.accountremeber.db.entity.DMCate

interface DMCateModel {

    fun getAllCaesByUserid(userId:Int):List<DMCate>
    fun addCate(cate:DMCate):Boolean
    fun getCateById(id:Int):DMCate?
}