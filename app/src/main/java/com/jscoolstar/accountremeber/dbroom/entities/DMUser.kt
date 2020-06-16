package com.jscoolstar.accountremeber.dbroom.entities

import androidx.annotation.NonNull
import androidx.room.*
import com.jscoolstar.accountremeber.dbroom.converts.MD5Convert
import com.jscoolstar.accountremeber.dbroom.converts.MDPasswordInfo

@Entity(tableName = "user",indices = [Index(value = ["userName"],unique = true),Index(value = ["nickName"],unique = true)])
@TypeConverters(MD5Convert::class)
data class DMUser(
        @PrimaryKey(autoGenerate = true)
        var id: Int = 0,
        var userName: String = "",
        var password: MDPasswordInfo,
        var nickName: String = userName,
        var passwordTip: String="",
        var leftTryTimes: Int =5,
        var lastWrongTime: String ="",

        var passwordView:MDPasswordInfo =MDPasswordInfo(""),
        var passwordViewTip :String ="",
        var isSamePassword:Boolean = true

) {
        fun updateBaseInfo(nickName2Update: String) {
                nickName = nickName2Update
        }

        fun updatePasswordLogin(password2Update: String,passwordTip: String) {
                password = MDPasswordInfo(password2Update)
                this.passwordTip = passwordTip
        }

        fun updatePasswordView(password2Update: String,passwordViewTip: String) {
                passwordView = MDPasswordInfo(password2Update)
                this.passwordViewTip = passwordViewTip
                isSamePassword = false//如果设置了view密码，则代码不使用登陆密码
        }
        fun updateUserLoginPassword(userLoginPassword:Boolean){
                if(userLoginPassword){
                        passwordView = MDPasswordInfo("")
                        passwordViewTip = ""
                        isSamePassword = true
                }else{
                        isSamePassword = false
                }
        }
        fun resetLeftTryTimes(leftTryTimes: Int,lastWrongTime: String){
                this.leftTryTimes = leftTryTimes
                this.lastWrongTime = lastWrongTime
        }
}

///**
// *author:skysoul
// *create time:2020/4/6 11:00 PM
// *description:app中使用的user安全信息，不含密码
// */
//@TypeConverters(MD5Convert::class)
//data class SimpleUser(
//        var id: Int = 0,
//        var userName: String = "",
//        var nickName: String = "",
//        var passwordTip: String="",
//        var passwordViewTip :String ="",
//        var passwordView:MDPasswordInfo? =null,
//        var isSamePassword:Boolean = true
//)

/**
 *author:skysoul
 *create time:2020/4/6 11:10 PM
 *description:未登录时的用户切换列表，可以包含用户名，昵称，头像等信息
 */
data class NoLoginUser(
        var nickName: String = "",
        var userName: String = ""
)



data class DMWrongUser(
        var userName: String,
        var leftTryTimes: Int,
        var lastWrongTime: String
)





