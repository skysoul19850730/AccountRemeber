package com.jscoolstar.accountremeber.activities

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.jscoolstar.accountremeber.R
import com.jscoolstar.accountremeber.activities.adapters.AccountAdapter
import com.jscoolstar.accountremeber.activities.edit.EditActivity
import com.jscoolstar.accountremeber.db.entity.DMAccount
import com.jscoolstar.accountremeber.utils.AESUtil
import com.jscoolstar.jscoolstarlibrary.widgets.dialog.JSMaterialDialogClickListerner
import com.jscoolstar.jscoolstarlibrary.widgets.dialog.JSMaterialDialogUtil
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), HomeToolbar.HomeBarClickListerner, MainActViewModel, HomeToolbar4Edit.HomeBar4EditClickListerner {


    val mSeed = "850730test"

    lateinit var mPressent: MainActPressent


    override fun toAdd() {
        var mIntent = Intent(this, EditActivity::class.java)
        startActivityForResult(mIntent, 10)
    }

    override fun toSearch() {
    }

    override fun toSetting() {
    }

    override fun toEdit() {
        mAdapter.mInEdit = !mAdapter.mInEdit
        toolbar4edit.visibility = (if (mAdapter.mInEdit) View.VISIBLE else View.GONE)
    }

    override fun cancle() {
        toEdit()
    }

    override fun delete() {
        mPressent.deleteAccounts(mAdapter.selectAccounts)
        mAdapter.selectAccounts.clear()
        initData()
    }


    lateinit var mAdapter: AccountAdapter

    var list: List<DMAccount> = ArrayList<DMAccount>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mPressent = MainActPressent(this)
        toolbar.listerner = this
        toolbar4edit.listerner = this
        var lManager = LinearLayoutManager(this)
        lManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = LinearLayoutManager(this)
        mAdapter = AccountAdapter(this, list)
        mAdapter.listerner = object : AccountAdapter.AccountListClickListerner {
            override fun onItemLongClick(account: DMAccount) {
                gotoEdit(account)
            }

            override fun onItemClick(account: DMAccount) {
                doItemClick(account)
            }

        }
        recyclerView.adapter = mAdapter

        initData()
    }

    fun doItemClick(account: DMAccount){
        var msg = account.tip ?: "没有设置密码提示"
        JSMaterialDialogUtil.INSTANCE.build(this).showDialog("密码提示",null,msg,"取消","进入详情",object :JSMaterialDialogClickListerner{
            override fun onCancelClick(dialogInterface: DialogInterface, which: Int) {
            }

            override fun onConfirmClick(dialogInterface: DialogInterface, which: Int) {
                gotoEdit(account)
            }
        })
    }
    fun gotoEdit(account: DMAccount){
        var mIntent = Intent(this@MainActivity, EditActivity::class.java)
        mIntent.putExtra("account", account)
        startActivityForResult(mIntent, 10)
    }

    fun initData() {
        list = mPressent.getList()
        mAdapter.setList(list)
    }

    fun log(content: String) {
        Log.d("sqc", content)
    }

    fun testAES() {
        var content = "isfafafasfafa love you中文呢"
        Log.d("sqc", "加密前字符串：" + content)

        var seed = AESUtil.generateKey()
        seed = "我的生日+6"
        log("随机seed：" + seed)

        var encrype = AESUtil.encrypt(seed, content)
        log("加密后：" + encrype)

        var decrypte = AESUtil.decrypt(seed, encrype)
        log("解密后：" + decrypte)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 10) {
            if (resultCode == Activity.RESULT_OK) {
                var tempAccount = data?.getParcelableExtra<DMAccount>("account")
                if (tempAccount != null) {
                    mPressent.addOneAccount(tempAccount!!)
                    initData()
                }
            }
        }
    }
}
