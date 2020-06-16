package com.jscoolstar.accountremeber.activities.login

import androidx.lifecycle.LiveData

class LoginLiveData : LiveData<LoginUIData> {

    constructor() : super() {
        value = LoginUIData(this)
    }

    public override fun setValue(value: LoginUIData?) {
        super.setValue(value)
    }



}