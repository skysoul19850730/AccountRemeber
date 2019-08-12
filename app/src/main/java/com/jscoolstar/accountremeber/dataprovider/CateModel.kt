package com.jscoolstar.accountremeber.dataprovider

import com.jscoolstar.accountremeber.dataprovider.dataentity.Cate


interface CateModel {

    fun getAllCaesByUserid(userId: Int): List<Cate>
    fun addCate(cate: Cate): Boolean
    fun updateCate(cate: Cate): Boolean
    fun getCateById(id: Int): Cate?
    fun getCateByName(userId: Int,name:String):Cate?
}