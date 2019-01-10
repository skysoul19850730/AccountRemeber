package com.jscoolstar.accountremeber.dbcontroller.entity

import com.jscoolstar.accountremeber.db.entity.DMUser

class DCUser {
    var userId:Int = 0
    var userName:String?=null
    var password:String?=null
    var passwordTip:String?=null
    var leftTryTimes:Int = 0

    constructor(user:DMUser){
        userId = user.userId
        userName = user.userName
        password = user.password
        passwordTip = user.passwordTip
        leftTryTimes = user.leftTryTimes
    }
}