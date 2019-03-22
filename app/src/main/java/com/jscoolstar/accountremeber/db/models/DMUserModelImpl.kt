package com.jscoolstar.accountremeber.db.models

import android.content.ContentValues
import android.database.Cursor
import android.text.TextUtils
import com.jscoolstar.accountremeber.apps.MApplication
import com.jscoolstar.accountremeber.config.StaticConfig
import com.jscoolstar.accountremeber.db.DBHelper
import com.jscoolstar.accountremeber.db.SQL
import com.jscoolstar.accountremeber.db.entity.DMUser
import com.jscoolstar.accountremeber.utils.MD5
import com.jscoolstar.accountremeber.utils.get
import java.util.*

class DMUserModelImpl() : DMUserModel {
    var dbHelper: DBHelper = DBHelper.getInstance(MApplication.getInstance().getContext());
    val TName = SQL.User.TABLENAME
    var db1 = SQL.User.DB1;

    private fun toDMUser(cursor: Cursor): DMUser {
        var user = DMUser()
        user.userId = cursor.get("id")
        user.userName = cursor.get(db1.C_UserName)
        user.leftTryTimes = cursor.get(db1.C_leftTryTimes)
        user.passwordTip = cursor.get(db1.C_passwordTip)
        user.lastWrongTime = cursor.get(db1.C_LastWrongTime)
        return user
    }

    /**  基础修改信息，只能修改用户名和密码提示 */
    private fun getCountentValues(user: DMUser): ContentValues {
        var contentValues = ContentValues()
        contentValues.put(db1.C_UserName, user.userName)
        contentValues.put(db1.C_passwordTip, user.passwordTip)
        return contentValues
    }

    override fun getUserById(userId: Int): DMUser? {
        var cursor: Cursor? = null
        try {
            cursor = dbHelper.readableDatabase.query(TName, null, "id = ?", arrayOf(userId.toString()), null, null, null)
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    return toDMUser(cursor)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            cursor?.close()
        }
        return null
    }

    override fun getUserList(): ArrayList<DMUser> {
        var userList = ArrayList<DMUser>()
        var cursor: Cursor? = null
        try {
            cursor = dbHelper.readableDatabase.query(TName, null, null, null, null, null, null)
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    userList.add(toDMUser(cursor))
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            cursor?.close()
        }
        return userList

    }

    override fun addUser(dmUser: DMUser): Boolean {
        try {

            if (TextUtils.isEmpty(dmUser.password) || TextUtils.isEmpty(dmUser.userName) || isUserNameExsits(dmUser.userName!!)) {
                return false
            }
            var contentValues = getCountentValues(dmUser)
            contentValues.put(db1.C_leftTryTimes, StaticConfig.MAX_RETRY_TIMES)
            contentValues.put(db1.C_Password, MD5.getMD5(dmUser.password!!))

            var id = dbHelper.writableDatabase.insert(TName, null, contentValues)
            if (id < 0) {
                throw Exception()
            }
            dmUser.userId = id.toInt()
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        } finally {

        }
        return true
    }

    override fun updateUserBaseInfo(dmUser: DMUser): Boolean {
        try {

            if (TextUtils.isEmpty(dmUser.userName) || isUserNameExsits(dmUser.userName!!)) {
                return false
            }
            var contentValues = getCountentValues(dmUser)
            dbHelper.writableDatabase.update(TName, contentValues, "id =?", arrayOf(dmUser.userId.toString()))
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        } finally {

        }
        return true //To change body of created functions use File | Settings | File Templates.
    }

    override fun updateUserPassword(userId: Int, password: String, passwordTip: String?): Boolean {
        try {

            if (password.isEmpty()) {
                return false
            }
            var contentValues = ContentValues()
            contentValues.put("${db1.C_Password}", MD5.getMD5(password))
            contentValues.put("${db1.C_passwordTip}", passwordTip)
            dbHelper.writableDatabase.update(TName, contentValues, "id =?", arrayOf(userId.toString()))
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        } finally {

        }
        return true //To change body of created functions use File | Settings | File Templates.
    }

    override fun getUserReTryTimesAndLastWrongTime(userName: String):Pair<Int,String> {
        var cursor = dbHelper.readableDatabase.query(TName, arrayOf(db1.C_leftTryTimes, db1.C_LastWrongTime), "${db1.C_UserName} =  ?", arrayOf(userName), null, null, null)
        var leftTime = 0
        var lastWrongTime = ""
        if (cursor != null) {
            while (cursor.moveToNext()) {
                leftTime = cursor.get(db1.C_leftTryTimes)
                lastWrongTime = cursor.get(db1.C_LastWrongTime)
            }
        }
        if (leftTime == 0) {
            var lastwrong = lastWrongTime.toLong()
            var date = Date().time
            if (date - lastwrong > StaticConfig.WRONGTIME_KEEP) {//如果已经过了一天
                setReTryTimes(StaticConfig.MAX_RETRY_TIMES, userName, "")
                return Pair(StaticConfig.MAX_RETRY_TIMES,"")
            } else {
                return Pair(0,lastWrongTime)
            }
        }
        return Pair(leftTime,"")
    }

    override fun checkPassword(userName: String, password: String): Pair<Int,Int> {
        var cursor: Cursor? = null
        try {
            var (retryTimes,lastwrong) = getUserReTryTimesAndLastWrongTime(userName)
            if (retryTimes == 0) return Pair(0,0)

            cursor = dbHelper.readableDatabase.query(TName, arrayOf(db1.C_Password,"id"), "${db1.C_UserName} =  ?", arrayOf(userName), null, null, null)
            var passwordDM = ""
            var userid =0
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    passwordDM = cursor.get(db1.C_Password)
                    userid = cursor.get("id")
                }
            }


            var passCheck = MD5.getMD5(password)
            if (passCheck.equals(passwordDM)) {//如果密码对了
                setReTryTimes(StaticConfig.MAX_RETRY_TIMES,userName,"")
                return Pair(userid,0)
            }
            retryTimes--
            setReTryTimes(retryTimes,userName,Date().time.toString())
            return Pair(0,retryTimes)

        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            cursor?.close()
        }
        return Pair(0,1000)
    }

    override fun isUserNameExsits(userName: String): Boolean {
        var cursor = dbHelper.readableDatabase.query(TName, arrayOf(db1.C_UserName), "${db1.C_UserName} =  ?", arrayOf(userName), null, null, null)
        if (cursor != null) {
            while (cursor.moveToNext()) {
                return true
            }
        }
        return false
    }

    private fun setReTryTimes(times: Int, userName: String, lastWrongTime: String) {
        var contentValues = ContentValues()
        contentValues.put(db1.C_leftTryTimes, times)
        contentValues.put(db1.C_LastWrongTime, lastWrongTime)
        dbHelper.writableDatabase.update(TName, contentValues, "${db1.C_UserName}=?", arrayOf(userName))

    }
}