package com.jscoolstar.accountremeber.activities.register.models

import com.jscoolstar.accountremeber.dataprovider.dataentity.User

interface Register2Model {

    fun isNickNameExsits(nickName:String):Boolean

    fun updateUserPassword4ViewAccount(userId:Int, password:String?,isUseLoginPassword:Boolean):Boolean

    fun setNickName(nickName: String)

    fun isUserUseLoginPassword():Boolean

}