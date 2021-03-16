package com.zzq.jetpack.room.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {

    @Query("SELECT * FROM User")
    fun getAllUser(): LiveData<List<User>>

    @Query("SELECT * FROM User")
    fun getAllUser2(): List<User>

    @Insert
    fun addUser(vararg users: User)

    @Delete
    fun deleteUser(user: User)

    @Query("SELECT * FROM User WHERE name LIKE :userName")
    fun query(userName:String):LiveData<List<User>>
}