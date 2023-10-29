package com.zenasi.cryptolist_compose_demo.utils

sealed class ApiResult<T: Any> {
    class OnSuccess<T: Any>(val data: T) : ApiResult<T>()
    class OnFail<T: Any>(val code: Int, val message: String?) : ApiResult<T>()
    class OnException<T: Any>(val e: Throwable) : ApiResult<T>()
}
