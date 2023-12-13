package com.andriyanto.formkpu.ui

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andriyanto.formkpu.adapter.DataAdapter
import com.andriyanto.formkpu.databinding.FragmentDataBinding
import com.andriyanto.formkpu.viewmodel.DataViewModel

class DataFragment : Fragment() {

    private lateinit var binding: FragmentDataBinding
    private lateinit var adapter: DataAdapter
    private lateinit var viewModel: DataViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDataBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = DataAdapter(emptyList())

        binding.rvMoviePopular.adapter = adapter
        binding.rvMoviePopular.layoutManager = LinearLayoutManager(requireContext())

        viewModel = ViewModelProvider(this).get(DataViewModel::class.java)

        viewModel.getData().observe(viewLifecycleOwner, Observer { tasks ->
            adapter.setData(tasks)
        })

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val data = adapter.getDataAtPosition(position)

                // Tampilkan AlertDialog untuk konfirmasi penghapusan
                val alertDialogBuilder = AlertDialog.Builder(requireContext())
                alertDialogBuilder.setTitle("Apakah anda ingin menghapus list ini?")
                alertDialogBuilder.setPositiveButton("Ya") { _, _ ->
                    // Jika pengguna menekan "Ya", hapus catatan
                    viewModel.deleteData(data.id)
                }
                alertDialogBuilder.setNegativeButton("Tidak") { dialog, _ ->
                    // Jika pengguna menekan "Tidak", batal penghapusan
                    adapter.notifyItemChanged(position)
                    dialog.dismiss()
                }

                val alertDialog = alertDialogBuilder.create()
                alertDialog.show()
            }
        }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(binding.rvMoviePopular)
    }

}
