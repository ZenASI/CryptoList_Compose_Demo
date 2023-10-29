package com.zenasi.cryptolist_compose_demo.utils

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import okio.buffer
import okio.source
import java.io.ByteArrayInputStream
import java.lang.reflect.Type
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class JsonUtils @Inject constructor(
    val moshi: Moshi,
    val gson: Gson
) {
    //    @OptIn(ExperimentalStdlibApi::class)
    inline fun <reified T> moshi2Json(data: T): String {
        return try {
            // 兩者都適用
//            val adapter = moshi.adapter<T>().lenient()
//            adapter.lenient().toJson(data)
            val adapter: JsonAdapter<T> = moshi.adapter(T::class.java)
            adapter.lenient().toJson(data)
        } catch (e: Exception) {
            Log.d("JsonUtils", "moshi2Json: ${e.message}")
            ""
        }
    }

    inline fun <reified T> moshiFromJsonObject(data: String): T? {
        return try {
            val adapter: JsonAdapter<T> = moshi.adapter(T::class.java)
            adapter.lenient().fromJson(data)
        } catch (e: Exception) {
            Log.d("JsonUtils", "moshiFromJson: ${e.printStackTrace()}")
            null
        }
    }

    fun moshi2Map(json: String): Map<String, String>? {
        val moshi = Moshi.Builder().build()
        val type: Type = Types.newParameterizedType(
            MutableMap::class.java,
            String::class.java,
            String::class.java
        )
        val adapter: JsonAdapter<Map<String, String>> = moshi.adapter(type)
        val inputStream = ByteArrayInputStream(json.toByteArray())
        val map: Map<String, String>? = adapter.fromJson(inputStream.source().buffer())
        return map?.ifEmpty {
            emptyMap()
        }
    }

//    fun moshi2Map(data: Any): Map<String, String> {
//        return try {
//            val type: Type = Types.newParameterizedType(
//                MutableMap::class.java,
//                String::class.java,
//                String::class.java
//            )
//            val adapter: JsonAdapter<Map<String, String>> = moshi.adapter(type)
//            return adapter.fromJson(moshi2Json(data))!!
//        } catch (e: Exception) {
//            Log.w("JsonUtils", "moshi2Map: ${e.message}")
//            emptyMap()
//        }
//    }

    //    @OptIn(ExperimentalStdlibApi::class)
    inline fun <reified T> moshiFromJson(data: String): List<T> {
        return try {
            // 兩者都適用
//            val adapter = moshi.adapter<List<T>>().lenient()
//            val list: List<T> = adapter.fromJson(data)!!
//            list
            val type: Type = Types.newParameterizedType(
                MutableList::class.java,
                T::class.java
            )
            val adapter: JsonAdapter<List<T>> = moshi.adapter(type)
            adapter.fromJson(data)!!
        } catch (e: Exception) {
            Log.d("JsonUtils", "moshiFromJson: ${e.message}")
            emptyList<T>()
        }
    }

    fun <T> gsonFromJson(data: String, type: Class<T>): T? {
        return try {
            gson.fromJson<T>(data, type)
        } catch (e: Exception) {
            Log.d("JsonUtils", "gsonFromJson: ${e.printStackTrace()}")
            null
        }
    }

    // https://stackoverflow.com/questions/57818332/class-com-google-gson-internal-linkedtreemap-cannot-be-cast-to-class-partner
    inline fun <reified T> gsonFromJson(data: String): List<T> {
        return try {
            val type = object : TypeToken<List<T>>() {}.type
            gson.fromJson<List<T>>(data, type)
        } catch (e: Exception) {
            Log.d("JsonUtils", "gsonFromJson: ${e.printStackTrace()}")
            emptyList()
        }
    }

    fun <T> gson2Json(data: T): String {
        return try {
            gson.toJson(data)
        } catch (e: Exception) {
            Log.d("JsonUtils", "gson2Json: ${e.message}")
            ""
        }
    }
}