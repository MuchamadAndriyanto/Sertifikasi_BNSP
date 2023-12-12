package com.andriyanto.formkpu.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
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
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.google.android.gms.cast.framework.media.ImagePicker
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FormFragment : Fragment() {

    private lateinit var binding: FragmentFormBinding
    private lateinit var dbRoom: DatabaseDataKpu
    private lateinit var viewModel: DataViewModel
    private lateinit var dataGambarUri: Uri

    companion object {
        const val IMAGE_REQUEST_CODE = 1_000
    }

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

        binding.profileImage.setOnClickListener {
            openGallery()
        }


    }

    private fun openGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent, IMAGE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val imageUri = data?.data

            // Save the image URI in SharedPreferences
            val sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE)
            val editor = sharedPref.edit()
            editor.putString("userImageUri", imageUri.toString())
            editor.apply()

            // Update dataGambarUri
            dataGambarUri = imageUri!!

            // Load the image into profileImage and apply CircleCrop
            loadImageWithCircleCrop(dataGambarUri)
        }
    }
    private fun loadImageWithCircleCrop(imageUri: Uri) {
        Glide.with(this)
            .load(imageUri)
            .apply(RequestOptions.bitmapTransform(CircleCrop()))
            .into(binding.profileImage)
    }
    private fun addTask() {
        val dataNik = binding.nikEditText.text.toString()
        val dataNama = binding.namaEdiText.text.toString()
        val dataHp = binding.noEditText.text.toString()
        val dataJk = binding.jkEdiText.text.toString()
        val dataTanggal = binding.tanggalEdiText.text.toString()
        val dataAlamat = binding.alamatEdiText.text.toString()

        if (dataNama.isNotEmpty() && dataHp.isNotEmpty() && dataJk.isNotEmpty() && dataTanggal.isNotEmpty() && dataAlamat.isNotEmpty()) {
            if (::dataGambarUri.isInitialized) {
                lifecycleScope.launch(Dispatchers.IO) {
                    try {
                        val dataTodoList = DataKpu(0, dataNik, dataNama, dataHp, dataJk, dataTanggal, dataAlamat, dataGambarUri.toString())
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
                Toast.makeText(requireContext(), "Gambar belum dipilih", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(requireContext(), "Semua kolom harus diisi", Toast.LENGTH_SHORT).show()
        }
    }
}
