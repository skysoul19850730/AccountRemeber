package com.jscoolstar.accountremeber.dataprovider.dataentity
import android.os.Parcel
import android.os.Parcelable
import com.jscoolstar.accountremeber.model.beans.Cate

class Cate() : Parcelable {
    var id = 0
    var cateName:String = ""
    var userId = 0

    constructor(parcel: Parcel) : this() {
        id = parcel.readInt()
        cateName = parcel.readString()
        userId = parcel.readInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(cateName)
        parcel.writeInt(userId)
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