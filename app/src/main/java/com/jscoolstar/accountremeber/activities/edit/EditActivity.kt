package com.jscoolstar.accountremeber.activities.edit

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import android.widget.Toast
import android.widget.ViewAnimator
import com.jscoolstar.accountremeber.R
import com.jscoolstar.accountremeber.activities.JSToolbar
import com.jscoolstar.accountremeber.activities.adapters.CateAdapter
import com.jscoolstar.accountremeber.activities.adapters.ExtraColumnAdapter
import com.jscoolstar.accountremeber.db.entity.Account
import com.jscoolstar.accountremeber.db.entity.Cate
import com.jscoolstar.accountremeber.db.entity.ExtraColumn
import com.jscoolstar.accountremeber.models.account.CateModelImpl
import com.jscoolstar.accountremeber.utils.AESUtil
import com.jscoolstar.accountremeber.utils.Util
import com.jscoolstar.jscoolstarlibrary.widgets.dialog.JSMaterialDialogClickListerner
import com.jscoolstar.jscoolstarlibrary.widgets.dialog.JSMaterialDialogUtil
import kotlinx.android.synthetic.main.act_editaccount.*
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by Administrator on 2018/4/16.
 */
class EditActivity : AppCompatActivity(), JSToolbar.JSBarClickListerner, ExtraColumnAdapter.ExtraColumnClickListerner, Edit_Extra_Dialog.OnEditDialogClickListerner, AdapterView.OnItemSelectedListener {
    override fun onNothingSelected(p0: AdapterView<*>?) {
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        cate = cates.get(p2)
    }

    override fun onClickOk(extra: ExtraColumn) {
        extraDialog.dismiss()
        extralColumns.add(extra)
        mAdapter.notifyDataSetChanged()
    }

    override fun onClickCancel() {
        extraDialog.dismiss()
    }

    override fun onItemDeleteClick(extraColumn: ExtraColumn) {
        extralColumns.remove(extraColumn)
        mAdapter.notifyDataSetChanged()
    }

    override fun onItemClick(extraColumn: ExtraColumn) {
        extraDialog.showWithExtraColumn(extraColumn)
    }

    var account: Account? = null
    lateinit var extralColumns: ArrayList<ExtraColumn>
    lateinit var mAdapter: ExtraColumnAdapter
    lateinit var extraDialog: Edit_Extra_Dialog

    val mAppPassword = "850730"

    lateinit var cates: ArrayList<Cate>
    var cate: Cate? = null
    lateinit var cateAdapter: CateAdapter

    lateinit var editText: EditText

    override fun menuClicked(menuId: Int) {
        if (rlt_permission.visibility != View.VISIBLE)
            toSave()
    }

    override fun navClicked() {
        setResult(Activity.RESULT_CANCELED)
        finish()
    }

    fun toSave() {
        if (TextUtils.isEmpty(et_platform.text.toString()) || TextUtils.isEmpty(et_password.text.toString()) || TextUtils.isEmpty(et_account.text.toString())) {
            Toast.makeText(this, "平台及账号、密码不能为空", Toast.LENGTH_LONG).show()
            return
        }
        account!!.bindphone = et_phone.text.toString()
        account!!.bindmail = et_mail.text.toString()
        account!!.accountName = et_account.text.toString()
        account!!.create_time = Util.INSTANCE.formatTime(Date().time)
        account!!.password = AESUtil.encrypt(AESUtil.mSeed, et_password.text.toString())
        account!!.platform = et_platform.text.toString()
        account!!.tip = et_tip.text.toString()
        account!!.extraColumnList = extralColumns
        account!!.cate = cate
        var tempIntent = Intent()
        tempIntent.putExtra("account", account)
        setResult(Activity.RESULT_OK, tempIntent)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_editaccount)
        toolbar.listerner = this
        account = intent.getParcelableExtra("account")
        extralColumns = account?.extraColumnList ?: ArrayList()
        initUI()
    }

    override fun onResume() {
        super.onResume()
        rlt_permission.visibility = View.VISIBLE
        et_permission.setText("")
    }

    fun initUI() {
        if (account != null) {
            et_platform.setText(account!!.platform)
            et_account.setText(account!!.accountName)
            et_password.setText(AESUtil.decrypt(AESUtil.mSeed, account!!.password!!))
            et_mail.setText(account!!.bindmail)
            et_tip.setText(account!!.tip)
            et_phone.setText(account!!.bindphone)
            cate = account!!.cate
        } else {
            account = Account()
        }

        mAdapter = ExtraColumnAdapter(this, extralColumns, this)
        var lManager = LinearLayoutManager(this)
        lManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = mAdapter
        extraDialog = Edit_Extra_Dialog(this, this)


        cates = CateModelImpl().getAllCates() as ArrayList<Cate>
        cateAdapter = CateAdapter(this, cates)
        setSpinnerSelection()
        spinner_cate.onItemSelectedListener = this


        btn_add.setOnClickListener({
            extraDialog.showWithExtraColumn(null)
        })

        btn_permission.setOnClickListener({
            toJudgePermission()
        })

        editText = EditText(this)

        btn_addCate.setOnClickListener({
            JSMaterialDialogUtil.INSTANCE.build(this).showDialog("新增分类", editText, "输入分类名称", "取消"
                    , "确定", object : JSMaterialDialogClickListerner {
                override fun onCancelClick(dialogInterface: DialogInterface, which: Int) {
                    dialogInterface.dismiss()
                }

                override fun onConfirmClick(dialogInterface: DialogInterface, which: Int) {
                    if(addCateWithName(editText.text.toString())){
                        dialogInterface.dismiss()
                        setSpinnerSelection()
                    }else{
                        Toast.makeText(this@EditActivity,"不能输入空",Toast.LENGTH_LONG).show()
                    }
                }

            })
        })
    }

    fun addCateWithName(name: String): Boolean {
        if(name.length==0)return false
        var cate:Cate = Cate()
        cate.cateName = name
        if(CateModelImpl().addOneCate(cate)){
            this.cate = cate
            cates.add(cate)
            cateAdapter.notifyDataSetChanged()
            return true
        }
        return false
    }

    fun setSpinnerSelection() {
        var position = -1
        var defposition = 0
        for (index in 0..cates.size) {
            var tmp = cates.get(index)
            if (tmp.id == cate?.id) {
                position = index
                break
            }
            if (tmp.cateName.equals("default")) {
                defposition = index
            }
        }

        spinner_cate.adapter = cateAdapter
        if (position >= 0) {
            spinner_cate.setSelection(position)
        } else {
            spinner_cate.setSelection(defposition)
        }
    }

    fun toJudgePermission() {
        var tmp = et_permission.text.toString()
        if (mAppPassword.equals(tmp)) {
            rlt_permission.visibility = View.GONE
        } else {
            onClickCancel()
        }
    }
}