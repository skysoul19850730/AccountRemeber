package com.jscoolstar.accountremeber.activities.settings.models

/**
 * @date   2019/10/29 17:50
 * @author sqc
 * @Description 查看账户时的密码model
 */
interface AccountViewPasswordModel {

    fun setViewPassword(password:String)
    fun hasViewPassword():Boolean
    fun isOldViewPasswordCorrect(oldPassword:String)

}