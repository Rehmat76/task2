package com.example.task.networking

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response


abstract class BaseDataSource {

    protected suspend fun <T> getResult(
        call: suspend () -> Response<T>
    ): Resource<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return Resource.success(body)
            } else if (response.code() == 500) {
                return error("Internal Server Error")
            } else if (response.code() == 401) {
                return error("Invalid Credentials! Please try again.")
            } else if (response.errorBody() != null) {
                var obj: JSONObject? = null
                try {
                    obj = JSONObject(response.errorBody()!!.string())
                    val dataObj = obj.getString("data")
                    if (canParseDataObject(dataObj)) {
                        return error(parseDataObject(dataObj))
                    } else {
                        return error(obj.getString("data"))
                    }
                } catch (ex: JSONException) {
                    return error(obj!!.getString("message"))
                }
            }
            return error(response.message())
        } catch (e: Exception) {
            e.printStackTrace()
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(message: String): Resource<T> {
        return Resource.error(null, message)
    }

    private fun canParseDataObject(dataString: String): Boolean {
        return try {
            JSONObject(dataString)
            true
        } catch (ex: JSONException) {
            false
        }
    }

    private fun parseDataObject(dataString: String): String {
        try {
            val dataObj = JSONObject(dataString)
            val keys: Iterator<String> = dataObj.keys()
            while (keys.hasNext()) {
                val key = keys.next()
                if (dataObj.get(key) is JSONArray) {
                    val arrObject1 = (dataObj.get(key) as JSONArray)[0]
                    return arrObject1.toString()
                }
            }
        } catch (ex: JSONException) {
            return ""
        }
        return ""
    }


}