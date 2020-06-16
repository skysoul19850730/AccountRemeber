package com.jscoolstar.accountremeber.activities.login

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.jscoolstar.accountremeber.BR
import java.io.Serializable

class LoginUIData : Serializable, BaseObservable() {

    @Bindable
    var etUsername = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.etUsername)
        }
    @Bindable
    var etPassword = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.etPassword)
        }
    @Bindable
    var etUsernameError: String? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.etUsernameError)

        }
    @Bindable
    var etPasswordError: String? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.etPasswordError)

        }
}