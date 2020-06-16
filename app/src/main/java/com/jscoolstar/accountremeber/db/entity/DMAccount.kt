package com.jscoolstar.accountremeber.db.entity

import android.os.Parcel
import android.os.Parcelable
import java.util.*

/**
 * Created by Administrator on 2018/4/2.
 */
class DMAccount() {
    var id: Int = 0
    var platform: String = ""
    var accountName: String = ""
    var password: String = ""
    var tip: String = ""
    var bindphone: String = ""
    var bindmail: String = ""
    var create_time: String = ""
    var cateId: Int = 0
    var userId: Int = 0
}