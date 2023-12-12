package com.andriyanto.formkpu.dbroom

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DataKpuDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertData(DataTodoList: DataKpu)

    @Query("SELECT * FROM DataKpu ORDER BY id ASC ")
    fun getData() : List<DataKpu>

    @Query("DELETE FROM DataKpu WHERE id = :dataId")
    suspend fun deleteDataById(dataId: Int)

}