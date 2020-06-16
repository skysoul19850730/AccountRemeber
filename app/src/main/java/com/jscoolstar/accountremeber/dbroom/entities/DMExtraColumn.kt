package com.jscoolstar.accountremeber.dbroom.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "extra_column",indices = [Index("accountId","extraKey",unique = true)])
data class DMExtraColumn(
        @PrimaryKey(autoGenerate = true)
        var id: Int = 0,
        @ForeignKey(entity = DMAccount::class,parentColumns = ["id"],childColumns = ["accountId"],onDelete = ForeignKey.CASCADE)
        var accountId: Int = 0,

        
        var extraKey: String = "",
        var extraValue: String = ""
)