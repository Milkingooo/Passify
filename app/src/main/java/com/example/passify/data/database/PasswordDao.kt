package com.example.passify.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PasswordDao {
    @Query("SELECT * FROM passwords")
    fun getAllPasswords(): Flow<List<PasswordEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPassword(password: PasswordEntity)

    @Delete
    suspend fun deletePassword(password: PasswordEntity)

    @Query("DELETE FROM passwords WHERE id = :id")
    suspend fun deletePasswordById(id: Int)

    @Update
    suspend fun updatePassword(password: PasswordEntity)

    @Query("DELETE FROM passwords")
    suspend fun deleteAllPasswords()

    @Query("SELECT * FROM passwords WHERE id = :id LIMIT 1")
    fun getPasswordById(id: Int): PasswordEntity
}