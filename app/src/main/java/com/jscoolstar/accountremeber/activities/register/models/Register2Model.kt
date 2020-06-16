package com.jscoolstar.accountremeber.activities.register.models

interface Register2Model {

    fun isNickNameExsits(nickName:String):Boolean

    fun updateUserPassword4ViewAccount(userId:Int, password:String?,isUseLoginPassword:Boolean):Boolean

    fun setNickName(nickName: String)

    fun isUserUseLoginPassword():Boolean

}