package com.jscoolstar.accountremeber.models.account

import com.jscoolstar.accountremeber.db.entity.ExtraColumn

/**
 * Created by Administrator on 2018/4/2.
 */
interface ExtraColumnModel {
    /**
     * 根据账号id，或者所有该账号的额外属性
     */
    fun getColumnsWithAccountID(accountId: Int): List<ExtraColumn>

    /**
     * 删除某一个属性
     */
    fun deleteColumnWithId(columnId: Int): Boolean

    /**
     * 删除账号下的所有额外属性
     */
    fun deleteColumns4AccoutnId(accountId: Int): Boolean

    /**
     * 为账号增加或修改一个属性
     */
    fun addColumn4AccountId(accountId: Int, column: ExtraColumn): Boolean

    /**
     * 为账号增加或修改一组属性
     */
    fun addColumns4AccountId(accountId: Int, columns: List<ExtraColumn>): Boolean

}