package com.jscoolstar.accountremeber.activities.login

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.jscoolstar.accountremeber.R
import com.jscoolstar.accountremeber.activities.register.RegisterActivity
import com.jscoolstar.accountremeber.appbases.BaseVMActivity
import com.jscoolstar.accountremeber.databinding.LoginactBinding
import com.jscoolstar.accountremeber.exts.longToast
import com.jscoolstar.accountremeber.utils.JavaUtils
import com.jscoolstar.accountremeber.utils.log
import org.koin.androidx.viewmodel.ext.android.getViewModel
class LoginActivity : BaseVMActivity<LoginViewModel>() {

    override fun initVM(): LoginViewModel {
        var viewmodel:LoginViewModel = getViewModel()
//        var viewmodel:LoginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

//        Log.d("sqc2","vmAdd is ${JavaUtils.addressOf(viewmodel)}  livedata ${viewmodel.liveData.value!!.flag}")
        return viewmodel
    }
    override fun getLayoutResId(): Int {
        return R.layout.loginact
    }

    override fun initView() {
        (mBinding as LoginactBinding).viewModel = mViewModel

    }



    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable("mvm",mViewModel.liveData.value)
    }


    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        savedInstanceState?.let {
            var x = it.getSerializable("mvm")
            mViewModel.liveData.value =(x as LoginUIData)
        }
    }

    override fun initData() {
        mViewModel.getLastUser()
    }

    override fun startObserve() {


        mViewModel.apply {
            uiStateLogin.observe(this@LoginActivity, Observer {
                it.isSuccess?.let {
                    log("isSuccess changed ")

                    gotoHome()
                }
                if (it.isLoading){
                    log("isloading changed to ${it.isLoading}")
                    showProgressDialog()
                } else {
                    log("isloading changed to ${it.isLoading}")
                    dismissProgressDialog()
                }

                if(it.isError!=null){
                    longToast(it.isError!!)
                }

            })
            liveData.observe(this@LoginActivity, Observer {

                log("livadata changed $it")

            })
        }
    }

    private fun gotoRegister() {
//        mViewModel.liveData.value!!.flag = mViewModel.liveData.value!!.flag+1
//        startActivity(Intent(this, RegisterActivity::class.java))
//        Log.d("sqc1","livedata ${mViewModel.liveData.value!!.flag}")
        startActivity(Intent(this, RegisterActivity::class.java))

    }

    private fun gotoHome() {
        log("gotohome")

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.tv_register -> {
                gotoRegister()
            }
            R.id.btn_login -> {
//                gotoHome()
                mViewModel.doLogin()
            }
        }
    }


    private var progressDialog: ProgressDialog? = null
    private fun showProgressDialog() {
        if (progressDialog == null)
            progressDialog = ProgressDialog(this)
        progressDialog?.show()
    }

    private fun dismissProgressDialog() {
        progressDialog?.dismiss()
    }
}

