package com.andriyanto.formkpu.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.andriyanto.formkpu.R
import com.andriyanto.formkpu.databinding.FragmentFormBinding
import com.andriyanto.formkpu.dbroom.DataKpu
import com.andriyanto.formkpu.dbroom.DatabaseDataKpu
import com.andriyanto.formkpu.viewmodel.DataViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FormFragment : Fragment() {

    private lateinit var binding: FragmentFormBinding
    private lateinit var dbRoom: DatabaseDataKpu
    private lateinit var viewModel: DataViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFormBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dbRoom = DatabaseDataKpu.getInstance(requireContext())
        viewModel = ViewModelProvider(this).get(DataViewModel::class.java)

        binding.btnSubmit.setOnClickListener {
            addTask()
        }
    }

    private fun addTask() {
        val dataNik = binding.nikEditText.text.toString()
        val dataNama = binding.namaEdiText.text.toString()
        val dataHp = binding.noEditText.text.toString()
        val dataJk = binding.jkEdiText.text.toString()
        val dataTanggal = binding.tanggalEdiText.text.toString()
        val dataAlamat = binding.alamatEdiText.text.toString()

        if (dataNama.isNotEmpty() && dataHp.isNotEmpty() && dataJk.isNotEmpty() && dataTanggal.isNotEmpty() && dataAlamat.isNotEmpty()) {
            lifecycleScope.launch(Dispatchers.IO) {
                try {
                    val dataTodoList = DataKpu(0, dataNik, dataNama, dataHp, dataJk, dataTanggal, dataAlamat)
                    viewModel.insertData(dataTodoList)
                    withContext(Dispatchers.Main) {
                        findNavController().navigate(R.id.action_formFragment_to_dataFragment)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.e("YourFragmentTag", "Error adding data: ${e.message}")
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            requireContext(),
                            "Terjadi kesalahan saat menyimpan data: ${e.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        } else {
            Toast.makeText(requireContext(), "Semua kolom harus diisi", Toast.LENGTH_SHORT).show()
        }
    }
}
