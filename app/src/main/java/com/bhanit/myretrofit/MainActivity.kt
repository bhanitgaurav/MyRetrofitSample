package com.bhanit.myretrofit

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.bhanit.myretrofit.data.DataModel
import com.bhanit.myretrofit.databinding.ActivityMainBinding
import com.bhanit.myretrofit.network.ApiInterface
import com.bhanit.myretrofit.network.MyRetrofit
import com.google.gson.Gson
import com.google.gson.internal.LinkedTreeMap
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.Retrofit

class MainActivity : AppCompatActivity() {

    private lateinit var mApiInterface: ApiInterface
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        preInitializeRetrofitAndApiInterface()
        setOnClickListener()

    }

    private fun setOnClickListener() {
        binding.doApiCall.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            getUserList()
        }
    }

    private fun preInitializeRetrofitAndApiInterface() {
        val retrofitInstance = createRetrofitInstance()
        initializeApiInterface(retrofitInstance)
    }

    private fun initializeApiInterface(retrofitInstance: Retrofit) {
        mApiInterface = retrofitInstance.create(ApiInterface::class.java)
    }

    private fun createRetrofitInstance(): Retrofit {
        return MyRetrofit.getInstance()
    }

    private fun getUserList() {
        lifecycleScope.launch(Dispatchers.IO) { // api should be called to background thread
            try {
                val response = mApiInterface.getAllUsers()
                if (response !is LinkedTreeMap<*, *>) {
                    showToast("response is in different format")
                    return@launch
                }

                val jsonObject = JSONObject(response)
                val typeToken = object : TypeToken<DataModel?>() {}.type
                val responseModel: DataModel = Gson().fromJson(
                    jsonObject.toString(),
                    typeToken
                )
                Log.d(TAG, "getUserList: response is mapped into responseModel $responseModel")

                showToast("response is mapped into model $jsonObject")

            } catch (ex: Exception) {
                Log.e("Error", ex.localizedMessage as String)
                showToast(ex.localizedMessage as String)
            }
        }

    }


    private suspend fun showToast(toastMessage: String) {
        withContext(Dispatchers.Main) { //Switched to main thread
            binding.progressBar.visibility = View.GONE
            Toast.makeText(this@MainActivity, toastMessage, Toast.LENGTH_LONG)
                .show()
        }
    }

    companion object {
        private const val TAG = "Main Activity"
    }
}