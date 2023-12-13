package com.andriyanto.formkpu.dbroom

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// membuat dan memelihara koneksi ke basis data SQLite
@Database(entities = [DataKpu::class], version = 1, exportSchema = false)
abstract class DatabaseDataKpu : RoomDatabase(){
    abstract fun itemDao(): DataKpuDao

    companion object {
        private var instance: DatabaseDataKpu? = null

        fun getInstance(context: Context): DatabaseDataKpu {
            if (instance == null) {
                synchronized(DatabaseDataKpu::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        DatabaseDataKpu::class.java,
                        "datakpu"
                    ).build()
                }
            }
            return instance!!
        }
    }
}