package com.jscoolstar.accountremeber.dataprovider.dataentity
import android.os.Parcel
import android.os.Parcelable
import com.jscoolstar.accountremeber.model.beans.ExtraColumn

/**
 * Created by Administrator on 2018/4/2.
 */
class ExtraColumn() : Parcelable {
    var id: Int = 0
    var aId: Int = 0
    var key: String= ""
    var value: String= ""


    constructor(parcel: Parcel) : this() {
        id = parcel.readInt()
        aId = parcel.readInt()
        key = parcel.readString()
        value = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeInt(aId)
        parcel.writeString(key)
        parcel.writeString(value)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ExtraColumn> {
        override fun createFromParcel(parcel: Parcel): ExtraColumn {
            return ExtraColumn(parcel)
        }

        override fun newArray(size: Int): Array<ExtraColumn?> {
            return arrayOfNulls(size)
        }
    }


}