package com.goda.elmenus.util.extension

import com.google.gson.Gson
import com.goda.elmenus.util.exceptions.ServiceException
import com.goda.elmenus.util.models.Failure
import retrofit2.Response

val gson: Gson = Gson()

fun <T> Response<T>.handleError(): Failure {
    return gson.fromJson(this.errorBody()?.string(), Failure::class.java)
}


@Throws(ServiceException::class)
fun <T : Any> Response<T>.value(): T {
    if (isSuccessful) {
        return body() ?: throw ServiceException(this as Response<Any>)
    } else {
        throw ServiceException(this as Response<Any>)
    }
}
