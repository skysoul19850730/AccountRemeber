package com.jscoolstar.accountremeber.dbroom.entities

import androidx.room.*

@Entity(tableName = "account")
data class DMAccount(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var platform: String = "",
    var accountName: String = "",
    var password: String = "",
    var tip: String = "",
    var bindPhone: String = "",
    var bindMail: String = "",
    var createTime: String = "",

    @ForeignKey(entity = DMCate::class,parentColumns = ["id"],childColumns = ["cateId"],onDelete = ForeignKey.SET_DEFAULT)
    var cateId: Int = 1,

    @ForeignKey(entity = DMUser::class,parentColumns = ["id"],childColumns = ["userId"],onDelete = ForeignKey.CASCADE)
    var userId: Int = 0
)
data class SimpleAccount(
        val id: Int = 0,
        var platform: String = "",
        var accountName: String = "",
        var tip: String = ""
)

data class AccountWithCateAndExtras(

    @Embedded
    var account:DMAccount?=null,
    @Relation(parentColumn = "cateId",entityColumn = "id")
    var cate :DMCate?=null,
    @Relation(parentColumn = "id",entityColumn = "accountId")
    var extrasColumns:List<DMExtraColumn>?=null

)