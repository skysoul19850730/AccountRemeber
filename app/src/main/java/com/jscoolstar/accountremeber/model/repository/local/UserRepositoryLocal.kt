package com.jscoolstar.accountremeber.model.repository.local

import android.text.TextUtils
import android.util.Log
import com.jscoolstar.accountremeber.config.StaticConfig
import com.jscoolstar.accountremeber.dbroom.converts.MDPasswordInfo
import com.jscoolstar.accountremeber.dbroom.daos.UserDao
import com.jscoolstar.accountremeber.dbroom.entities.DMUser
import com.jscoolstar.accountremeber.dbroom.entities.NoLoginUser
import com.jscoolstar.accountremeber.model.SSResult
import com.jscoolstar.accountremeber.model.api.BaseRepository
import com.jscoolstar.accountremeber.model.beans.User
import com.jscoolstar.accountremeber.model.repository.UserRepository
import com.jscoolstar.accountremeber.model.repository.resultbeans.LoginResult
import com.jscoolstar.accountremeber.utils.MD5
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import java.util.*

class UserRepositoryLocal(private val userDao: UserDao) : UserRepository, BaseRepository() {

    private val errorMessage = "操作出现异常"

    private suspend fun <T : Any> safeDBCall(call: suspend () -> SSResult<T>?): SSResult<T> {
        return safeApiCall({
            coroutineScope {
                call()
            }
        }, errorMessage)
    }

//    suspend fun <T:Any> safeDBCall2(call:suspend () -> T?):T?{
//        return try {
//            coroutineScope {
//                call()
//            }
//        }catch (e:Exception){
//            null
//        }
//    }

    override suspend fun getUserById(userId: Int): SSResult<User> {
        return safeDBCall {
            userDao.getUserById(userId)?.run {
                SSResult.Success(User(this))
            }
        }
    }


    override suspend fun getNoLoginUserList(): SSResult<List<NoLoginUser>> {
        return safeDBCall {
            userDao.getUserNameList()?.run {
                SSResult.Success(this)
            }
        }
    }

    override suspend fun updateUserPassword4ViewAccount(userId: Int, password: String, passwordTip: String): SSResult<Boolean> {
        return safeDBCall {
            userDao.getUserById(userId)?.run {

                updatePasswordView(password, passwordTip)
                userDao.update(this)
                SSResult.Success(true)
            }
        }
    }

    override suspend fun registerWith(userName: String, password: String, passwordTip: String): SSResult<User> {

        return safeDBCall {
            userDao.insert(DMUser(0, userName, MDPasswordInfo(password), passwordTip = passwordTip)).run {
                getUserById(this.toInt())
            }

        }

    }

    override suspend fun updateUserBaseInfo(user: User): SSResult<Int> {
        return safeDBCall {
            userDao.getUserById(user.userId)?.run {
                nickName = user.nickName
                userDao.update(this).run {

                    SSResult.Success(this)
                }
            }
        }
    }


    override suspend fun updateUserPassword4Login(userId: Int, password: String, passwordTip: String): SSResult<Boolean> {
        return safeDBCall {
            userDao.getUserById(userId)?.run {

                updatePasswordLogin(password, passwordTip)
                userDao.update(this)
                SSResult.Success(true)
            }
        }
    }

    override suspend fun checkPasswordWithType(type: Int, userName: String, password: String): SSResult<LoginResult> {

        return safeDBCall {
            delay(3000)
            var dmUser = userDao.getUserByName(userName)
            if (dmUser == null) {
                SSResult.Success(LoginResult(errorType = -1))
            } else {
                var (leftTimes, lastWrong) = checkRetryTimes(dmUser)
                if (leftTimes == 0) {
                    SSResult.Success(LoginResult(nextTryTime = lastWrong))
                } else {
                    var password2Compare = dmUser.password.password
                    if (type == 1 && !dmUser.isSamePassword) {
                        password2Compare = dmUser.passwordView.password
                    }
                    if (password2Compare == MD5.getMD5(password)) {
                        dmUser.resetLeftTryTimes(StaticConfig.MAX_RETRY_TIMES, "")
                        userDao.update(dmUser)
                        SSResult.Success(LoginResult(user = User(dmUser)))
                    } else {
                        dmUser.leftTryTimes = dmUser.leftTryTimes - 1
                        dmUser.lastWrongTime = Date().time.toString()
                        userDao.update(dmUser)
                        var (leftTimes, lastWrong) = checkRetryTimes(dmUser)
                        if (leftTimes == 0) {
                            SSResult.Success(LoginResult(nextTryTime = lastWrong))
                        }
                        SSResult.Success(LoginResult(leftTimes = leftTimes))
                    }
                }
            }
        }

    }

    private fun checkRetryTimes(dmUser: DMUser): Pair<Int, String> {
        if (dmUser.leftTryTimes > 0) return Pair(dmUser.leftTryTimes, dmUser.lastWrongTime)
        var lastwrong = dmUser.lastWrongTime.toLong()
        var date = Date().time
        if (date - lastwrong > StaticConfig.WRONGTIME_KEEP) {//如果已经过了一天
            dmUser.resetLeftTryTimes(StaticConfig.MAX_RETRY_TIMES, "")
            userDao.update(dmUser)
            return Pair(StaticConfig.MAX_RETRY_TIMES, "")
        } else {
            var waitHour = (lastwrong + StaticConfig.WRONGTIME_KEEP - date) / 1000 / 60 / 60
            return Pair(0, waitHour.toString())
        }
    }

    override suspend fun isNickNameExist(nickName: String): SSResult<Boolean> {
        return safeDBCall {
            userDao.getNickName(nickName)?.run {
                SSResult.Success(this == nickName)
            }
        }
    }
}