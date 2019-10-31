package com.jscoolstar.accountremeber.db.entity

/**
 * @date   2019/10/30 14:54
 * @author sqc
 * @Description  对外model信息类，不涉及用户名，各密码等属性
 */
class DMUser {
    var userId: Int = 0
    var passwordTip: String = ""
    var nickName: String = ""
    var leftTryTimes: Int = 0
    var lastWrongTime: String = ""
    var isAccountViewPasswordSetted: Boolean = false
}