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
import com.jscoolstar.accountremeber.activities.edit.models.EditModel
import com.jscoolstar.accountremeber.activities.edit.models.EditModelImpl
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
//    var account: Account? = null

    lateinit var mAdapter: ExtraColumnAdapter
    lateinit var extraDialog: Edit_Extra_Dialog
    var cateAddDialog: AlertDialog? = null

//    val mAppPassword = "850730"

    //    var cate: Cate? = null
    lateinit var cateAdapter: CateAdapter

    lateinit var editText: EditText
    var isEditState = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = EditPresenterImpl(this, EditModelImpl())
        toolbar.listerner = this
        initUI()
        presenter.initDatas(intent)
    }

    override fun onResume() {
        super.onResume()
        presenter.judgePermissonIfShow()
    }

    override fun fillUIWithAccount(account: Account) {
        et_platform.setText(account!!.platform)
        et_account.setText(account!!.accountName)
        et_password.setText(AESUtil.decrypt(AESUtil.mSeed, account!!.password!!))
        et_mail.setText(account!!.bindmail)
        et_tip.setText(account!!.tip)
        et_phone.setText(account!!.bindphone)
    }

    override fun initExtraColumnUI(extralColumns: ArrayList<ExtraColumn>) {
        mAdapter = ExtraColumnAdapter(this, extralColumns, this)
        recyclerView.adapter = mAdapter
    }

    override fun initCatesAdapter(cates: ArrayList<Cate>) {
        cateAdapter = CateAdapter(this, cates)
        spinner_cate.adapter = cateAdapter
    }

    fun initUI() {

        var lManager = LinearLayoutManager(this)
        lManager.orientation = RecyclerView.VERTICAL
        recyclerView.layoutManager = lManager
        extraDialog = Edit_Extra_Dialog(this, this)
        spinner_cate.onItemSelectedListener = this
        btn_add.setOnClickListener {
            extraDialog.showWithExtraColumn(null)
        }
        btn_permission.setOnClickListener {
            presenter.judgePermissonIfRigth(et_permission.text.toString())
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
                presenter.addCateWithName(editText.text.toString()) {
                    if (it) {
                        dialogInterface.dismiss()
                    }
                }
            }

        })
    }


    override fun selectSpinnerIndex(position: Int) {
        cateAdapter.notifyDataSetChanged()
        spinner_cate.setSelection(position)
    }

    override fun showPermissonUI(show: Boolean) {
        rlt_permission.visibility = if (show) View.VISIBLE else View.GONE
        et_permission.setText("")
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
        presenter.saveAccount {
            it.bindphone = et_phone.text.toString()
            it.bindmail = et_mail.text.toString()
            it.accountName = et_account.text.toString()
            it.create_time = Util.INSTANCE.formatTime(Date().time)
            it.password = AESUtil.encrypt(AESUtil.mSeed, et_password.text.toString())
            it.platform = et_platform.text.toString()
            it.tip = et_tip.text.toString()
            return@saveAccount it
//            account!!.extraColumnList = extralColumns
//            account!!.cate = cate
        }
    }

    override fun navClicked() {
        setResult(Activity.RESULT_CANCELED)
        finish()
    }

    override fun onItemClick(extraColumn: ExtraColumn, position: Int) {
        extraDialog.showWithExtraColumn(extraColumn)
    }

    override fun onItemDeleteClick(extraColumn: ExtraColumn, position: Int) {
        presenter.deleteExtraColumn(position) {
            mAdapter.notifyDataSetChanged()
        }
    }

    override fun onClickOk(extra: ExtraColumn) {
        presenter.addExtraColumn(extra) {
            extraDialog.dismiss()
            mAdapter.notifyDataSetChanged()
        }
    }

    override fun onClickCancel() {
        extraDialog.dismiss()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

    }

    override fun uiOnAddOrUpdateFinished(intent: Intent) {
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

}