package com.andriyanto.formkpu.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.andriyanto.formkpu.R
import com.andriyanto.formkpu.databinding.ListDataDiriBinding
import com.andriyanto.formkpu.dbroom.DataKpu
import com.bumptech.glide.Glide

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

        Glide.with(holder.itemView.context)
            .load(Uri.parse(data.dataGambar)) // assuming dataGambar is the image URI or path
            .placeholder(R.drawable.ic_launcher_background) // placeholder image while loading
            .error(R.drawable.ic_launcher_foreground) // error image if Glide fails to load
            .into(holder.binding.ivBackground)
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
