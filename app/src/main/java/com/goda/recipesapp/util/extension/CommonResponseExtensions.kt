package com.goda.elmenus.util.extension

import com.goda.elmenus.util.exceptions.ServiceException
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.goda.elmenus.util.models.ErrorResponse
import retrofit2.Response
import java.util.*

fun <T> Response<T>.parseErrorResponse(): ErrorResponse {
    val gson = Gson()
    val type = object : TypeToken<ErrorResponse>() {}.type
    return gson.fromJson(errorBody()?.charStream(), type)
}




fun <RECEIVER_TYPE, RETURN_TYPE> RECEIVER_TYPE?.nullCheckOrDefault(
    defaultNonNullValue: RETURN_TYPE,
    block: (RECEIVER_TYPE) -> RETURN_TYPE
): RETURN_TYPE {
    return if (this != null) {
        block(this)
    } else {
        defaultNonNullValue
    }
}




@Throws(ServiceException::class)
@Deprecated("This method is no longer acceptable as it is not safe please use safeMapToResult instead")
fun <T> Response<T>.mapToResult(): T {
    if (!isSuccessful) throw ServiceException(this as Response<Any>)
    return body()!!
}

// mapToResult with null safty
@Throws(ServiceException::class)
fun <T> Response<T>.safeMapToResult(): T? {
    if (!isSuccessful) throw ServiceException(this as Response<Any>)
    return body()
}

private const val USER_SEC_QUESTIONS_RESETED = "10003"
private const val MESSAGE_SECURITY_QUESTION_FAILED = "10004"
private const val USER_REACHED_MAX_DEVICES = "10009"
private const val USER_LOCKED = "10011"
private const val USER_ACCOUNT_EXPIRED = "10012"
private const val USER_LOCKED_BY_ADMIN = "10013"
private const val DEVICE_TOKEN_NOT_TRUSTED = "10014"
private const val OTP_REQUIRED_FOR_LOGIN = "10015"
private const val ENDING_SESSION_CODE_62 = "00062"
private const val ENDING_SESSION_CODE_63 = "00063"
private const val SUCCESS_CODE = "0"
private const val RUNTIME_EXCEPTION = "-1"


