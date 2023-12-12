package com.andriyanto.formkpu.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.andriyanto.formkpu.dbroom.DataKpu
import com.andriyanto.formkpu.dbroom.module.dbModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DataViewModel(application: Application) : AndroidViewModel(application) {

    private val itemDao = dbModule(application.applicationContext).itemDao
    private val dataList: MutableLiveData<List<DataKpu>> by lazy {
        MutableLiveData<List<DataKpu>>()
    }

    fun insertData(dataTodoList: DataKpu) {
        GlobalScope.launch(Dispatchers.IO) {
            itemDao.insertData(dataTodoList)
            dataList.postValue(itemDao.getData())
        }
    }

    fun getData(refresh: Boolean = false): LiveData<List<DataKpu>> {
        if (refresh || dataList.value == null) {
            GlobalScope.launch(Dispatchers.IO) {
                dataList.postValue(itemDao.getData())
            }
        }
        return dataList
    }

    fun deleteData(dataId: Int) {
        GlobalScope.launch(Dispatchers.IO) {
            itemDao.deleteDataById(dataId)
            dataList.postValue(itemDao.getData())
        }
    }
}
