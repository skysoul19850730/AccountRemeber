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

    companion object CREATOR : Parcelable.Creator<Cate> {
        override fun createFromParcel(parcel: Parcel): Cate {
            return Cate(parcel)
        }

        override fun newArray(size: Int): Array<Cate?> {
            return arrayOfNulls(size)
        }
    }
}