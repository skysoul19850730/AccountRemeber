package com.jscoolstar.accountremeber.activities.login

import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jscoolstar.accountremeber.appbases.BaseViewModel
import com.jscoolstar.accountremeber.apps.MApplication
import com.jscoolstar.accountremeber.apps.UserInfoManager
import com.jscoolstar.accountremeber.dbroom.AccountDatabase
import com.jscoolstar.accountremeber.model.SSResult
import com.jscoolstar.accountremeber.model.beans.User
import com.jscoolstar.accountremeber.model.repository.local.UserRepositoryLocal
import com.jscoolstar.accountremeber.utils.log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel() : BaseViewModel() {


    private val localRepository: UserRepositoryLocal = UserRepositoryLocal(AccountDatabase.getInstance(MApplication.getInstance().getContext()).userDao())

    private var _uiStateLogin = MutableLiveData<UiState<User>>()

    val uiStateLogin: LiveData<UiState<User>>
        get() = _uiStateLogin

    private var _liveData = LoginLiveData()
    val liveData: LoginLiveData
        get() = _liveData



    fun getLastUser() {
        viewModelScope.launch(Dispatchers.Main) {
            UserInfoManager.getInstance().getLastUser()?.run {
                _uiStateLogin.value = UiState<User>().apply { isSuccess = this@run }
            }
        }
    }


    fun doLogin() {
        launchOnUI {
            var userName = liveData.value?.etUsername ?: ""
            var password = liveData.value?.etPassword ?: ""
            if (userName.isBlank()) {
                liveData.value?.etUsernameError = "用户名不为空"
                return@launchOnUI
            }
            if (password.isBlank()) {
                liveData.value?.etUsername = ""
                liveData.value?.etPasswordError = "密码不为空"
                return@launchOnUI
            }
            liveData.value?.etPasswordError = ""
            liveData.value?.etUsernameError = ""

            _uiStateLogin.value = UiState(isLoading = true)

            localRepository.checkPasswordWithType(0, userName, password).run {

                _uiStateLogin.value = UiState(isLoading = false)
                when (this) {
                    is SSResult.Error -> {
                        _uiStateLogin.value = UiState(isError = exception.message)
                    }
                    is SSResult.Success -> {
                        data.run {
                            when {
                                user != null -> _uiStateLogin.value = UiState(isSuccess = user)
                                errorType == -1 -> {
                                    liveData.value?.etUsernameError = "用户不存在"

//                                    _uiStateLogin.value = UiState(isError = "用户不存在")
                                }
                                leftTimes > 0 -> {
                                    liveData.value?.etPasswordError = "密码错误,还可以尝试${leftTimes}次"
                                }
                                !TextUtils.isEmpty(nextTryTime) -> {
                                    liveData.value?.etPasswordError = "密码错误,请在${nextTryTime}小时后尝试"
                                }

                            }
                        }
                    }

                }
            }
        }

    }


}