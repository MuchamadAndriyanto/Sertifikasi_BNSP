package com.andriyanto.formkpu.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.andriyanto.formkpu.databinding.ListDataDiriBinding
import com.andriyanto.formkpu.dbroom.DataKpu

class DataAdapter(private var dataList: List<DataKpu>) : RecyclerView.Adapter<DataAdapter.ViewHolder>() {

    class ViewHolder(var binding: ListDataDiriBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ListDataDiriBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: DataAdapter.ViewHolder, position: Int) {
        val data = dataList[position]

        holder.binding.noteId.text = data.id.toString()
        holder.binding.dataNik.text = data.dataNik
        holder.binding.dataNama.text = data.dataNama
        holder.binding.dataHp.text = data.dataHp
        holder.binding.dataJenisKelamin.text = data.dataJk
        holder.binding.dataTanggal.text = data.dataTanggal
        holder.binding.dataAlamat.text = data.dataAlamat
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun setData(newDataList: List<DataKpu>) {
        dataList = newDataList
        notifyDataSetChanged()
    }

    fun getDataAtPosition(position: Int): DataKpu {
        return dataList[position]
    }

}
