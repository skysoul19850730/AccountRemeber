package com.jscoolstar.accountremeber.activities.register

import androidx.lifecycle.LiveData

class RegisterLiveData: LiveData<RegisterLiveData.RegisterUIData>{

    constructor():super(){
        value = RegisterUIData()
    }

     inner class RegisterUIData {


        var etUsername = ""
            set(value) {
                field = value
                etUsernameError=""
            }
        var etPassword = ""
            set(value) {
                field = value
                etPasswordError=""
            }
        var etPassword2 = ""
            set(value) {
                field = value
                etPasswordError2=""
            }
        var etPasswordTip = ""
            set(value) {
                field = value
                postValue(this)
            }
        var etUsernameError: String? = null
            set(value) {
                field = value
                postValue(this)
            }
        var etPasswordError: String? = null
            set(value) {
                field = value
                postValue(this)
            }
        var etPasswordError2: String? = null
            set(value) {
                field = value
                postValue(this)
            }
    }

}