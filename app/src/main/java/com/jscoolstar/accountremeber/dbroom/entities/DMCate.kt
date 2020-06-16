package com.jscoolstar.accountremeber.dbroom.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey
//同一个用户不能有相同的分类名称，userId和cateName不能同时相同
@Entity(tableName = "cate",indices = [Index(value = ["userId", "cateName"],unique = true)])
data class DMCate(
        @PrimaryKey(autoGenerate = true)
        var id: Long = 0L,
        var cateName: String = "",
        @ForeignKey(entity = DMUser::class,parentColumns = ["id"],childColumns = ["userId"],onDelete = CASCADE)
        var userId: Int = 0
)