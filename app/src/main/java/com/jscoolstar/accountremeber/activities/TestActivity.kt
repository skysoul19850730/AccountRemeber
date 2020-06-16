package com.jscoolstar.accountremeber.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.jscoolstar.accountremeber.R
import com.jscoolstar.accountremeber.dbroom.AccountDatabase
import com.jscoolstar.accountremeber.dbroom.converts.MDPasswordInfo
import com.jscoolstar.accountremeber.dbroom.daos.AccountDao
import com.jscoolstar.accountremeber.dbroom.daos.CateDao
import com.jscoolstar.accountremeber.dbroom.daos.ExtrasDao
import com.jscoolstar.accountremeber.dbroom.daos.UserDao
import com.jscoolstar.accountremeber.dbroom.entities.*
import com.jscoolstar.accountremeber.utils.log
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class TestActivity : AppCompatActivity() {

    lateinit var database: AccountDatabase

    lateinit var userDao: UserDao
    lateinit var cateDao: CateDao
    lateinit var accountDao: AccountDao
    lateinit var extDao: ExtrasDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_test)
        database = AccountDatabase.getInstance(this)
        userDao = database.userDao()
        cateDao = database.cateDao()
        accountDao = database.accountDao()
        extDao = database.extrasDao()
        GlobalScope.launch {
            //            userDao.insert(DMUser(0, "qcshen", "sqc", MDPasswordInfo("112313")))
//        }
            var temp = cateDao.getAllCaesByUserid(1)
            cates.addAll(temp)
        }
    }

    var cates: ArrayList<DMCate> = arrayListOf()
    fun addCate(v: View) {
        var cate = DMCate(0, "cate${Date().time}", 1)
        cates.add(cate)
        GlobalScope.launch {

            cateDao.insert(cate)
        }

    }

    fun addAccount(v: View) {
        GlobalScope.launch {
            var a = AccountWithCateAndExtras()
            a.account = DMAccount(0, "android", "xx", "xx", "x"
                    , "x", "x", "x", cates[0].id.toInt(), 1)
            var id = accountDao.insert(a.account!!)

            a.cate = cateDao.getCateById(cates[0].id.toInt())
            a.extrasColumns = arrayListOf(
                    DMExtraColumn(0, id.toInt(), "key1${Date().time}", "1"),
                    DMExtraColumn(0, id.toInt(), "key${Date().time}", "2")
            )
            extDao.insert(a.extrasColumns!!)
        }
    }

    fun deleteCate(v: View) {

    }

    fun getAccount(v: View) {
        GlobalScope.launch {
            var data = accountDao.getDetailsById(1)
            log(data.toString())
        }
    }
}