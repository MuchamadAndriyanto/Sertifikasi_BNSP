package com.andriyanto.formkpu.dbroom

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

//mengabstraksi akses ke basis data SQLite
@Dao
interface DataKpuDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertData(DataTodoList: DataKpu)

    @Query("SELECT * FROM DataKpu ORDER BY id ASC ")
    fun getData() : List<DataKpu>

    @Query("DELETE FROM DataKpu WHERE id = :dataId")
    suspend fun deleteDataById(dataId: Int)

    @Query("SELECT * FROM DataKpu WHERE dataNik = :nik LIMIT 1")//digunakan untuk mendapatkan nik yang sudah ada di list
    suspend fun getDataByNik(nik: String): DataKpu?


}