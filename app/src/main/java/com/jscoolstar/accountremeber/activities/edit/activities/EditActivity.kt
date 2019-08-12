package com.jscoolstar.accountremeber.activities.edit.activities

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jscoolstar.accountremeber.R
import com.jscoolstar.accountremeber.activities.JSToolbar
import com.jscoolstar.accountremeber.activities.adapters.CateAdapter
import com.jscoolstar.accountremeber.activities.adapters.ExtraColumnAdapter
import com.jscoolstar.accountremeber.activities.edit.Edit_Extra_Dialog
import com.jscoolstar.accountremeber.activities.edit.presenters.EditPresenterImpl
import com.jscoolstar.accountremeber.activities.edit.presenters.IEditPresenter
import com.jscoolstar.accountremeber.activities.edit.views.EditViewModel
import com.jscoolstar.accountremeber.apps.BaseActivity
import com.jscoolstar.accountremeber.dataprovider.CateModelImpl
import com.jscoolstar.accountremeber.dataprovider.dataentity.Account
import com.jscoolstar.accountremeber.dataprovider.dataentity.Cate
import com.jscoolstar.accountremeber.dataprovider.dataentity.ExtraColumn
import com.jscoolstar.accountremeber.utils.AESUtil
import com.jscoolstar.accountremeber.utils.Util
import com.jscoolstar.jscoolstarlibrary.widgets.dialog.JSMaterialDialogClickListerner
import com.jscoolstar.jscoolstarlibrary.widgets.dialog.JSMaterialDialogUtil
import kotlinx.android.synthetic.main.act_editaccount.*
import java.util.*
import kotlin.collections.ArrayList

class EditActivity : BaseActivity<IEditPresenter>(), EditViewModel, JSToolbar.JSBarClickListerner, ExtraColumnAdapter.ExtraColumnClickListener, Edit_Extra_Dialog.OnEditDialogClickListerner, AdapterView.OnItemSelectedListener {


    override lateinit var presenter: IEditPresenter
    var account: Account? = null
    lateinit var extralColumns: ArrayList<ExtraColumn>
    lateinit var mAdapter: ExtraColumnAdapter
    lateinit var extraDialog: Edit_Extra_Dialog
    var cateAddDialog: AlertDialog? = null

    val mAppPassword = "850730"
    lateinit var cates: ArrayList<Cate>
    var cate: Cate? = null
    lateinit var cateAdapter: CateAdapter

    lateinit var editText: EditText
    var isEditState = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = EditPresenterImpl(this, CateModelImpl())
        toolbar.listerner = this
        account = intent.getParcelableExtra("account")
        extralColumns = account?.extraColumnList ?: ArrayList()
        initUI()
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
            cate = presenter.getDefaultCate()
            isEditState = false
        }

        mAdapter = ExtraColumnAdapter(this, extralColumns, this)
        var lManager = LinearLayoutManager(this)
        lManager.orientation = RecyclerView.VERTICAL
        recyclerView.layoutManager = lManager
        recyclerView.adapter = mAdapter
        extraDialog = Edit_Extra_Dialog(this, this)


        cates = CateModelImpl().getAllCaesByUserid(presenter.getUser()!!.userId) as ArrayList<Cate>
        cateAdapter = CateAdapter(this, cates)
        setSpinnerSelection()
        spinner_cate.onItemSelectedListener = this


        btn_add.setOnClickListener {
            extraDialog.showWithExtraColumn(null)
        }

        btn_permission.setOnClickListener {
            toJudgePermission()
        }

        editText = EditText(this)

        btn_addCate.setOnClickListener {
            showCateAddDialog()
        }
    }

    fun showCateAddDialog() {
        cateAddDialog = JSMaterialDialogUtil.INSTANCE.build(this).showDialog("新增分类", editText, "输入分类名称", "取消"
                , "确定", object : JSMaterialDialogClickListerner {
            override fun onCancelClick(dialogInterface: DialogInterface, which: Int) {
                dialogInterface.dismiss()
            }

            override fun onConfirmClick(dialogInterface: DialogInterface, which: Int) {
                presenter.addCateWithName(editText.text.toString())

            }

        })
    }

    fun setSpinnerSelection() {
        var position = 0
        for ((index, value) in cates.withIndex()) {
            if (value.id == cate?.id) {
                position = index;
                break
            }
        }

        spinner_cate.adapter = cateAdapter
        if (position >= 0) {
            spinner_cate.setSelection(position)
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


    override fun getLayoutId(): Int {
        return R.layout.act_editaccount
    }

    override fun menuClicked(menuId: Int) {
        if (rlt_permission.visibility != View.VISIBLE)
            toSave()
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
    }

    override fun navClicked() {
        setResult(Activity.RESULT_CANCELED)
        finish()
    }

    override fun onItemClick(extraColumn: ExtraColumn) {
        extraDialog.showWithExtraColumn(extraColumn)
    }

    override fun onItemDeleteClick(extraColumn: ExtraColumn) {
        extralColumns.remove(extraColumn)
        mAdapter.notifyDataSetChanged()
    }

    override fun onClickOk(extra: ExtraColumn) {
        extraDialog.dismiss()
        extralColumns.add(extra)
        mAdapter.notifyDataSetChanged()
    }

    override fun onClickCancel() {
        extraDialog.dismiss()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        cate = cates.get(position)
    }

    override fun uiOnAddOrUpdateFinished(added: Boolean) {
        if (!added) {
            showToast("添加失败，请检查错误")
        } else {
            var tempIntent = Intent()
            tempIntent.putExtra("account", account)
            tempIntent.putExtra("isEditState", isEdit)
            setResult(Activity.RESULT_OK, tempIntent)
            finish()
        }
    }

    override fun uiOnAddCateSuc(cate: Cate?) {
        if (cate != null) {
            cateAddDialog?.dismiss()
            setSpinnerSelection()
        } else {
            showToast("不能输入空")
        }
    }
}