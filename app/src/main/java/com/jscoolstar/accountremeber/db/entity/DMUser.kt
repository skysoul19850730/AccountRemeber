package com.jscoolstar.accountremeber.db.entity

class DMUser {
    var userId: Int = 0
    var userName: String = ""
    var password: String = ""
    var passwordTip: String = ""
    var leftTryTimes: Int = 0
    var lastWrongTime: String = ""
}