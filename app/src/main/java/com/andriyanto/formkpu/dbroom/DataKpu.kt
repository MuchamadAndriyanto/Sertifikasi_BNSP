package com.andriyanto.formkpu.dbroom

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class DataKpu(
    @PrimaryKey(autoGenerate = true)
    var id : Int,
    var dataNik : String,
    var dataNama : String,
    var dataHp : String,
    var dataJk : String,
    var dataTanggal : String,
    var dataAlamat : String
) : Parcelable