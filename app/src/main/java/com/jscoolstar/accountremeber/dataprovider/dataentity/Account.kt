package com.jscoolstar.accountremeber.dataprovider.dataentity
import android.os.Parcel
import android.os.Parcelable

/**
 * Created by Administrator on 2018/4/2.
 */
class Account() : Parcelable {

    override fun equals(other: Any?): Boolean {
        if(other is Account){
            return id == other.id
        }
        return super.equals(other)
    }

    var isChecked = false

    var id: Int = 0
    var platform: String = ""
    var password: String= ""
    var tip: String= ""
    var bindphone: String= ""
    var bindmail: String= ""
    var create_time: String= ""
    var accountName: String= ""
    var userId:Int =0

    var cate: Cate? = null
    var extraColumnList: ArrayList<ExtraColumn>? = null

    constructor(parcel: Parcel) : this() {
        id = parcel.readInt()
        platform = parcel.readString()
        password = parcel.readString()
        tip = parcel.readString()
        bindphone = parcel.readString()
        bindmail = parcel.readString()
        create_time = parcel.readString()
        accountName = parcel.readString()
        userId = parcel.readInt()
        cate = parcel.readParcelable(Cate::class.java.classLoader)
        extraColumnList = parcel.readArrayList(ExtraColumn::class.java.classLoader) as ArrayList<ExtraColumn>?
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
        parcel.writeInt(userId)
        parcel.writeParcelable(cate, flags)
        parcel.writeList(extraColumnList)
    }


    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Account> {
        override fun createFromParcel(parcel: Parcel): Account {
            return Account(parcel)
        }

        override fun newArray(size: Int): Array<Account?> {
            return arrayOfNulls(size)
        }
    }

}