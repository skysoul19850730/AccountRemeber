package com.jscoolstar.accountremeber.activities.home.activities

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.core.view.GravityCompat
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.jscoolstar.accountremeber.R
import com.jscoolstar.accountremeber.activities.HomeToolbar
import com.jscoolstar.accountremeber.activities.HomeToolbar4Edit
import com.jscoolstar.accountremeber.activities.adapters.AccountAdapter
import com.jscoolstar.accountremeber.activities.edit.activities.EditActivity
import com.jscoolstar.accountremeber.activities.home.models.MainModelImpl
import com.jscoolstar.accountremeber.activities.home.presenter.IMainPresenter
import com.jscoolstar.accountremeber.activities.home.presenter.MainPresneterImpl
import com.jscoolstar.accountremeber.activities.home.views.MainView
import com.jscoolstar.accountremeber.activities.login.activities.LoginActivity
import com.jscoolstar.accountremeber.apps.ActivityManager
import com.jscoolstar.accountremeber.dataprovider.dataentity.Account
import com.jscoolstar.accountremeber.dataprovider.dataentity.User
import com.jscoolstar.accountremeber.db.entity.DMAccount
import com.jscoolstar.accountremeber.utils.AESUtil
import com.jscoolstar.jscoolstarlibrary.widgets.dialog.JSMaterialDialogClickListerner
import com.jscoolstar.jscoolstarlibrary.widgets.dialog.JSMaterialDialogUtil
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_drawer_header.*
import kotlinx.android.synthetic.main.main_drawer_header.view.*


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
        lManager.orientation = RecyclerView.VERTICAL
        recyclerView.layoutManager = lManager
        var list:ArrayList<Account> = arrayListOf()
        mAdapter = AccountAdapter(this, list)
        mAdapter.listerner = object : AccountAdapter.AccountListClickListerner {
            override fun onItemClick(position: Int) {
                presenter.onItemClick(position)
            }

            override fun onItemLongClick(position: Int) {
                presenter.onItemLongClick(position)
            }
        }
        recyclerView.adapter = mAdapter

        nav_view.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.drawer_menu_setting -> presenter.uiSettingTask()
                R.id.drawer_menu_backup -> presenter.uiBackupTask()
                R.id.drawer_menu_rate -> presenter.uiRatingTask()
                R.id.drawer_menu_share -> presenter.uiShareTask()
            }
            drawer_layout.closeDrawer(GravityCompat.START)
            return@setNavigationItemSelectedListener true
        }

        nav_view.getHeaderView(0).img_user.setOnClickListener { presenter.uiUserTask() }
        nav_view.getHeaderView(0).btn_logout.setOnClickListener { presenter.login_out() }

        fb_add.setOnClickListener { presenter.uiAddAccountTask() }
        presenter.start()
    }

    override fun finalFinish() {
        super.finish()
    }

    override fun finish() {
        presenter.dealFinish()
    }

    fun onAddClick() {
        presenter.uiAddAccountTask()
    }

    override fun showUserInfo(user: User) {
        nav_view.getHeaderView(0).tv_username.text = user.nickName
    }

    override fun onSearchClick() {
        presenter.uiSearchTask()
    }

    override fun onNavigationClick() {
        drawer_layout.openDrawer(GravityCompat.START)
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

    override fun notifyItemChanged(position: Int) {
        mAdapter.notifyItemChanged(position)
    }

    override fun notifyItemInserted(position: Int) {
        mAdapter.notifyItemInserted(position)
    }

    override fun showItemTipDialog(account: Account) {
        var msg = account.tip ?: "没有设置密码提示"
        JSMaterialDialogUtil.INSTANCE.build(this).showDialog("密码提示", null, msg, "取消", "进入详情", object : JSMaterialDialogClickListerner {
            override fun onCancelClick(dialogInterface: DialogInterface, which: Int) {
            }

            override fun onConfirmClick(dialogInterface: DialogInterface, which: Int) {
                showUIAccountEdit(account)
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

    override fun showUILogin() {
        ActivityManager.goLogin(this)
    }

    override fun showToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show()
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
