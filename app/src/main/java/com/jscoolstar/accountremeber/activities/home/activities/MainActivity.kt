package com.jscoolstar.accountremeber.activities.home.activities

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Toast
import com.jscoolstar.accountremeber.R
import com.jscoolstar.accountremeber.activities.HomeToolbar
import com.jscoolstar.accountremeber.activities.HomeToolbar4Edit
import com.jscoolstar.accountremeber.activities.adapters.AccountAdapter
import com.jscoolstar.accountremeber.activities.edit.EditActivity
import com.jscoolstar.accountremeber.activities.home.models.MainModelImpl
import com.jscoolstar.accountremeber.activities.home.presenter.IMainPresenter
import com.jscoolstar.accountremeber.activities.home.presenter.MainPresneterImpl
import com.jscoolstar.accountremeber.activities.home.views.MainView
import com.jscoolstar.accountremeber.dataprovider.dataentity.Account
import com.jscoolstar.accountremeber.db.entity.DMAccount
import com.jscoolstar.accountremeber.utils.AESUtil
import com.jscoolstar.jscoolstarlibrary.widgets.dialog.JSMaterialDialogClickListerner
import com.jscoolstar.jscoolstarlibrary.widgets.dialog.JSMaterialDialogUtil
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), HomeToolbar.HomeBarClickListerner, HomeToolbar4Edit.HomeBar4EditClickListerner, MainView {
    override lateinit var presenter: IMainPresenter

    lateinit var mAdapter: AccountAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter = MainPresneterImpl(MainModelImpl(), this)
        toolbar.listerner = this
        toolbar4edit.listerner = this
        var lManager = LinearLayoutManager(this)
        lManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = LinearLayoutManager(this)
        mAdapter = AccountAdapter(this, arrayListOf())
        mAdapter.listerner = object : AccountAdapter.AccountListClickListerner {
            override fun onItemClick(position: Int) {
                presenter.onItemClick(position)
            }

            override fun onItemLongClick(position: Int) {
                presenter.onItemLongClick(position)
            }
        }
        recyclerView.adapter = mAdapter

        presenter.start()
    }

    override fun finalFinish() {
        super.finish()
    }

    override fun finish() {
        presenter.dealFinish()
    }

    override fun onAddClick() {
        presenter.uiAddAccountTask()
    }

    override fun onSearchClick() {
        presenter.uiSearchTask()
    }

    override fun onSettingClick() {
        presenter.uiSettingTask()
    }

    override fun onEditClick() {
        presenter.onEditClick()
    }

    override fun cancle() {
        presenter.onEditClick()
    }

    override fun delete() {
        presenter.deleteChoosedAccounts()
    }

    override fun showMainList(list: List<Account>) {
        mAdapter.setList(list)
    }

    override fun notifyData() {
        mAdapter.notifyDataSetChanged()
    }

    override fun showItemTipDialog(account: Account) {
        var msg = account.tip ?: "没有设置密码提示"
        JSMaterialDialogUtil.INSTANCE.build(this).showDialog("密码提示", null, msg, "取消", "进入详情", object : JSMaterialDialogClickListerner {
            override fun onCancelClick(dialogInterface: DialogInterface, which: Int) {
            }

            override fun onConfirmClick(dialogInterface: DialogInterface, which: Int) {
                presenter.uiPreIntoDetail(account)
            }
        })
    }

    override fun showEditState(showEdit: Boolean) {
        mAdapter.mInEdit = showEdit
    }

    override fun showUIAccountEdit(account: Account) {

        var intent = Intent(this, EditActivity::class.java)
        intent.putExtra("account", account)
        startActivityForResult(intent, MainPresneterImpl.REQUESTCODE_DETAILEDIT)

    }

    override fun showUIAddNewAccount() {
        var intent = Intent(this, EditActivity::class.java)
        startActivityForResult(intent, MainPresneterImpl.REQUESTCODE_ADDACCOUNT)
    }

    override fun showUISearchUI() {
        showToast("去搜索")
    }

    override fun showUISettingUI() {
        showToast("去设置")
    }

    override fun showUIPasswordCheck() {
        showToast("检查密码")
    }

    override fun showUILogin() {
        showToast("去登录")
    }

    override fun showToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_LONG)
    }

    override fun showToast(textId: Int) {
        Toast.makeText(this, textId, Toast.LENGTH_LONG)
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
        presenter.onActivityResult(requestCode, resultCode, data)
    }
}
