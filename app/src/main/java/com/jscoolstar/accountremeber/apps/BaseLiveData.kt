package com.jscoolstar.accountremeber.apps

import androidx.lifecycle.LiveData

class BaseLiveData<T> :LiveData<T> {
    constructor(cls:Class<T>):super(){
        value = cls.newInstance()
    }
}