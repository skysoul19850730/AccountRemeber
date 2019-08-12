package com.jscoolstar.accountremeber.dataprovider

import com.jscoolstar.accountremeber.dataprovider.dataentity.ExtraColumn
import com.jscoolstar.accountremeber.db.entity.DMExtraColumn
import com.jscoolstar.accountremeber.db.models.DMExtraColumnModel
import com.jscoolstar.accountremeber.db.models.DMExtraCoumnModelImpl

class ExtraColumnModelImpl :ExtraColumnModel {

    lateinit var dmColumnModelImpl:DMExtraColumnModel

    constructor(){
        dmColumnModelImpl = DMExtraCoumnModelImpl()
    }


    override fun getColumnsWithAccountID(accountId: Int): ArrayList<ExtraColumn> {
        var dmList = dmColumnModelImpl.getColumnsWithAccountID(accountId)
        var result = arrayListOf<ExtraColumn>()
        for (dm in dmList){
            result.add(dm.toExtraColumn())
        }
        return result
    }

//    override fun deleteColumnWithId(columnId: Int): Boolean {
//       return dmColumnModelImpl.deleteColumnWithId(columnId)
//    }

//    override fun deleteColumns4AccoutnId(accountId: Int): Boolean {
//        return dmColumnModelImpl.deleteColumns4AccoutnId(accountId)
//    }

//    override fun addColumn4AccountId(accountId: Int, column: ExtraColumn): Boolean {
//        return dmColumnModelImpl.addColumn4AccountId(accountId,column.toDMExtraColumn())
//    }

    override fun addColumns4AccountId(accountId: Int, columns: List<ExtraColumn>): Boolean {
        var list = arrayListOf<DMExtraColumn>()
        for(ex in columns){
            list.add(ex.toDMExtraColumn())
        }
        dmColumnModelImpl.deleteColumns4AccoutnId(accountId)
        return dmColumnModelImpl.addColumns4AccountId(accountId,list)
    }
}