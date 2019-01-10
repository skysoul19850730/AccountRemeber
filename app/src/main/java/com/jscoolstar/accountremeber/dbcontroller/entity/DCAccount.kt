package com.jscoolstar.accountremeber.dbcontroller.entity

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by Administrator on 2018/4/2.
 */
class DCAccount() {
    var id: Int = 0
    var platform: String? = null
    var accountName: String? = null
    var password: String? = null
    var tip: String? = null
    var bindphone: String? = null
    var bindmail: String? = null
    var create_time: String? = null
    var cate: DCCate? = null
    var userId: Int = 0
    var extraColumns = ArrayList<DCExtraColumn>()
}