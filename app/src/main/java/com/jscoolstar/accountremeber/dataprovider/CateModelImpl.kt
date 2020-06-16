package com.jscoolstar.accountremeber.dataprovider

import com.jscoolstar.accountremeber.model.beans.Cate
import com.jscoolstar.accountremeber.db.models.DMCateModel
import com.jscoolstar.accountremeber.db.models.DMCateModelImpl

class CateModelImpl : CateModel {

    lateinit var dmCateModelImpl: DMCateModel

    constructor() {
        dmCateModelImpl = DMCateModelImpl()
    }


    override fun getAllCaesByUserid(userId: Int): ArrayList<Cate> {

        var result = arrayListOf<Cate>()

        var dmResult = dmCateModelImpl.getAllCaesByUserid(userId)
        for (dm in dmResult) {
            result.add(dm.toCate())
        }
        return result

    }

    override fun addCate(cate: Cate): Boolean {
        return dmCateModelImpl.addCate(cate.toDMCate())
    }

    override fun updateCate(cate: Cate): Boolean {
        return dmCateModelImpl.updateCate(cate.toDMCate())
    }

    override fun getCateById(id: Int): Cate? {
        return dmCateModelImpl.getCateById(id)?.toCate()
    }

    override fun getCateByName(userId: Int, name: String): Cate? {
        return dmCateModelImpl.getCateByName(userId,name)?.toCate()
    }
}