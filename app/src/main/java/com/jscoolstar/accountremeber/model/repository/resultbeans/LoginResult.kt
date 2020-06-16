package com.jscoolstar.accountremeber.model.repository.resultbeans

import com.jscoolstar.accountremeber.model.beans.User

class LoginResult(
    var user:User?=null,
    var errorType:Int = 0,
    var leftTimes:Int = 5,
    var nextTryTime:String =""
)