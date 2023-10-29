package com.zenasi.cryptolist_compose_demo.repository

import com.zenasi.cryptolist_compose_demo.utils.ApiResult
import retrofit2.HttpException
import retrofit2.Response

open class BaseRepo {

    suspend fun <T : Any> handleApiResponse(response: suspend () -> Response<T>): ApiResult<T> {
        return try {
            val result = response.invoke()
            if (result.isSuccessful && result.body() != null) {
                ApiResult.OnSuccess(data = result.body()!!)
            } else {
                ApiResult.OnFail(code = result.code(), message = result.message())
            }
        } catch (e: HttpException) {
            ApiResult.OnFail(code = e.code(), message = e.message())
        } catch (e: Throwable) {
            ApiResult.OnException(e = e)
        }
    }
}