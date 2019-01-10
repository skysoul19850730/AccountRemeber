package com.jscoolstar.accountremeber.db.entity

import android.os.Parcel
import android.os.Parcelable

class Cate() : Parcelable {
    var id = 0
    var cateName:String = ""

    constructor(parcel: Parcel) : this() {
        id = parcel.readInt()
        cateName = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(cateName)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DMCate> {
        override fun createFromParcel(parcel: Parcel): DMCate {
            return DMCate(parcel)
        }

        override fun newArray(size: Int): Array<DMCate?> {
            return arrayOfNulls(size)
        }
    }
}