package com.jscoolstar.accountremeber.appbases

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jscoolstar.accountremeber.BR
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by luyao
 * on 2019/5/31 16:06
 */
open class BaseViewModel : ViewModel() {


    open class UiState<T> : BaseObservable() {

        @Bindable
        var isLoading: Boolean = false
            set(value) {
                field = value;
                notifyPropertyChanged(BR.isLoading)
            }

        @Bindable
        var isRefresh: Boolean = false
            set(value) {
                field = value
                notifyPropertyChanged(BR.isRefresh)
            }

        @Bindable
        var isSuccess: T? = null
            set(value) {
                field = value
                notifyPropertyChanged(BR.isSuccess)
            }

        var isError: String? = null
    }

    val mException: MutableLiveData<Throwable> = MutableLiveData()


    fun launchOnUI(block: suspend CoroutineScope.() -> Unit) {

        viewModelScope.launch { block() }

    }

    suspend fun <T> launchOnIO(block: suspend CoroutineScope.() -> T) {
        withContext(Dispatchers.IO) {
            block
        }
    }
}