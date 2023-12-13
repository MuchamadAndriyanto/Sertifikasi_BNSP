package com.andriyanto.formkpu.dbroom.module

import android.content.Context
import androidx.room.Room
import com.andriyanto.formkpu.dbroom.DatabaseDataKpu

//digunakan untuk menyediakan instance dari objek-objek yang diperlukan dalam aplikasi
class dbModule (context: Context){
    private val db = Room.databaseBuilder(context, DatabaseDataKpu::class.java, "datakpu.db").build()
    val itemDao = db.itemDao()
}