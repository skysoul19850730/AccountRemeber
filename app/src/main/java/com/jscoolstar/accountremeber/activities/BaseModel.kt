package com.jscoolstar.accountremeber.activities

import com.jscoolstar.accountremeber.apps.UserInfoManager
import com.jscoolstar.accountremeber.model.beans.User

interface BaseModel {
    fun getCurrectUser(): User {
        return User()
    }
}