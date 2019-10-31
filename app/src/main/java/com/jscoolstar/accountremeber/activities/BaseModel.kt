package com.jscoolstar.accountremeber.activities

import com.jscoolstar.accountremeber.apps.UserInfoManager
import com.jscoolstar.accountremeber.dataprovider.dataentity.User

interface BaseModel {
    fun getCurrectUser():User {
        return UserInfoManager.getInstance().getUser()
    }
}