package com.andriyanto.formkpu.dbroom.module

import android.content.Context
import androidx.room.Room
import com.andriyanto.formkpu.dbroom.DatabaseDataKpu

class dbModule (context: Context){
    private val db = Room.databaseBuilder(context, DatabaseDataKpu::class.java, "datakpu.db").build()
    val itemDao = db.itemDao()
}