package com.example.task.ui.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.task.adapters.DrinksAdapter
import com.example.task.databinding.FragmentHomeBinding
import com.example.task.models.Drink
import com.example.task.models.DrinkDbModel
import com.example.task.networking.Resource
import com.example.task.utils.AppConstants
import com.example.task.utils.FileUtils
import com.example.task.utils.MyPref
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment(), DrinksAdapter.OnItemClickListener {

    private val mViewModel: DrinksViewModel by viewModels()
    private lateinit var mBinding: FragmentHomeBinding
    private var mDrinks: ArrayList<Drink> = ArrayList()
    private lateinit var searchBy: String

    private val REQUEST_STORAGE_PERMISSION = 100
    val mAdapter = DrinksAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchBy = AppConstants.searchByName


        mBinding.rvRecipes.adapter = mAdapter

        val myPref = MyPref.getInstance(requireContext())

        if (myPref?.searchBy.equals(AppConstants.searchByName)) {
            mBinding.searchByName.isChecked = true
        } else if (myPref?.searchBy.equals(AppConstants.searchByAlphabet)) {
            mBinding.searchByFirstAlphabet.isChecked = true
        }

        mBinding.searchByRadio.setOnCheckedChangeListener { radioGroup, i ->

            val selectedOption = mBinding.root.findViewById<RadioButton>(i)
            if (selectedOption.text == AppConstants.searchByName) {
                myPref?.searchBy = AppConstants.searchByName
                searchBy = AppConstants.searchByName
            } else if (selectedOption.text == AppConstants.searchByAlphabet) {
                myPref?.searchBy = AppConstants.searchByAlphabet
                searchBy = AppConstants.searchByAlphabet
            }
        }

        mViewModel.getDrinksByName("margarita")

        mViewModel.drinksResponse.observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.LOADING -> {
                    mBinding.progressBar.visibility = View.VISIBLE
                }
                Resource.Status.SUCCESS -> {
                    it.data?.let { drinks ->

                        mBinding.progressBar.visibility = View.GONE
                        mBinding.rvRecipes.visibility = View.VISIBLE
                        mDrinks.clear()
                        mDrinks.addAll(drinks.drinks)
                        mAdapter.submitList(mDrinks)
                    }
                }
                Resource.Status.ERROR -> {
                    Toast.makeText(requireContext(), "error", Toast.LENGTH_SHORT).show()
                }
            }
        }


        mBinding.ivSearch.setOnClickListener {

            if (mBinding.etSearch.text.toString().isNotEmpty()) {
                when (searchBy) {
                    AppConstants.searchByName -> {
                        mBinding.progressBar.visibility = View.VISIBLE
                        mBinding.rvRecipes.visibility = View.GONE
                        mViewModel.getDrinksByName(mBinding.etSearch.text.toString())
                        mDrinks.clear()
                    }
                    AppConstants.searchByAlphabet -> {
                        mViewModel.getDrinksByALPHABET(mBinding.etSearch.text.toString())
                        mBinding.progressBar.visibility = View.VISIBLE
                        mBinding.rvRecipes.visibility = View.GONE
                        mDrinks.clear()
                    }
                }
            } else {
                Toast.makeText(
                    requireContext(),
                    "enter keyword for search",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onItemClick(drink: Drink) {
        drink.favourite = true
        val dbDrink = DrinkDbModel(
            drink.idDrink,
            drink.strAlcoholic,
            drink.strCategory,
            drink.strDrink,
            drink.strInstructions,
            drink.strDrinkThumb,
            "",
            drink.favourite
        )
        mViewModel.saveFavoriteDrink(dbDrink)
        mAdapter.notifyDataSetChanged()
    }


    private fun checkStoragePermission(): Boolean {
        return (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
                && PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.READ_EXTERNAL_STORAGE
        ))
    }


    private fun requestStoragePermissions() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ),
            REQUEST_STORAGE_PERMISSION
        )
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_STORAGE_PERMISSION -> if (grantResults[0] == PackageManager.PERMISSION_GRANTED
                && grantResults[1] == PackageManager.PERMISSION_GRANTED
            ) checkStoragePermission()
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }

    }

/*

    private class DownloadImage : AsyncTask<String, Any?, Bitmap?>() {

        protected fun onPostExecute(result: Bitmap): Bitmap? {
            // Set the bitmap into ImageView
            //  image.setImageBitmap(result)
            return result
        }

        override fun doInBackground(vararg p0: String?): Bitmap? {
            val imageURL = p0[0]
            var bitmap: Bitmap? = null
            try {
                // Download Image from URL
                val input: InputStream = URL(imageURL as String?).openStream()
                // Decode Bitmap
                bitmap = BitmapFactory.decodeStream(input)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return bitmap
        }
    }

    internal class DownloadTask : AsyncTask<String?, Int?, String>() {


        override fun doInBackground(vararg params: String): String {
            val path = params[0]
            val file_length: Int
            try {
                val url = URL(path)
                val urlConnection: URLConnection = url.openConnection()
                urlConnection.connect()
                file_length = urlConnection.getContentLength()

                val new_folder =
                    File(Environment.getExternalStorageDirectory().absolutePath, "TASK")
                if (!new_folder.exists()) {
                    if (new_folder.mkdir()) {
                        Log.i("Info", "Folder succesfully created")
                    } else {
                        Log.i("Info", "Failed to create folder")
                    }
                } else {
                    Log.i("Info", "Folder already exists")
                }
                */
    /**
     * Create an output file to store the image for download
     *//*

                val output_file = File(new_folder, "downloaded_image.jpg")
                val outputStream: OutputStream = FileOutputStream(output_file)
                val inputStream: InputStream = BufferedInputStream(url.openStream(), 8192)
                val data = ByteArray(1024)
                var total = 0
                var count: Int
                while (inputStream.read(data).also { count = it } != -1) {
                    total += count
                    outputStream.write(data, 0, count)
                }
                inputStream.close()
                outputStream.close()
                Log.i("Info", "file_length: " + Integer.toString(file_length))
            } catch (e: MalformedURLException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return "Download complete."
        }


        override fun onPostExecute(result: String) {
            val folder = File(Environment.getExternalStorageDirectory().absolutePath, "myfolder")
            val output_file = File(folder, "downloaded_image.jpg")
            val path: String = output_file.toString()
            saveDataToDataBase()
            Log.i("Info", "Path: $path")
        }

    }

*/


}