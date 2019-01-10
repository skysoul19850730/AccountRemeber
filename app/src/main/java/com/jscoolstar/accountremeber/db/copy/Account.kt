package com.jscoolstar.accountremeber.db.entity

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by Administrator on 2018/4/2.
 */
class Account() : Parcelable {
    var id:Int =0
    var platform:String?=null
    var password:String?=null
    var tip:String?=null
    var bindphone:String?=null
    var bindmail:String?=null
    var create_time:String?=null
    var accountName:String?=null

    var cate:DMCate?=null
    var extraColumnList:ArrayList<DMExtraColumn>?=null

    constructor(parcel: Parcel) : this() {
        id = parcel.readInt()
        platform = parcel.readString()
        password = parcel.readString()
        tip = parcel.readString()
        bindphone = parcel.readString()
        bindmail = parcel.readString()
        create_time = parcel.readString()
        accountName = parcel.readString()

        cate = parcel.readParcelable(DMCate::class.java.classLoader) as DMCate
        extraColumnList = parcel.readArrayList(DMExtraColumn::class.java.classLoader) as ArrayList<DMExtraColumn>?
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(platform)
        parcel.writeString(password)
        parcel.writeString(tip)
        parcel.writeString(bindphone)
        parcel.writeString(bindmail)
        parcel.writeString(create_time)
        parcel.writeString(accountName)

        parcel.writeParcelable(cate,flags)
        parcel.writeList(extraColumnList)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DMAccount> {
        override fun createFromParcel(parcel: Parcel): DMAccount {
            return DMAccount(parcel)
        }

        override fun newArray(size: Int): Array<DMAccount?> {
            return arrayOfNulls(size)
        }
    }
}