package com.zzq.jetpack.room.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(@PrimaryKey val userId: Long,
                @ColumnInfo var name: String,
                @ColumnInfo val registerTime: Long,
                @ColumnInfo val description: String,
                @ColumnInfo var address: String? = null) {


    override fun toString(): String {
        return "User(userId=$userId, name='$name', registerTime=$registerTime, description='$description', address='$address')"
    }
}