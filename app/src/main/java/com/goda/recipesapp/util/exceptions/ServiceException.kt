package com.goda.elmenus.util.exceptions

import com.goda.elmenus.util.extension.parseErrorResponse
import retrofit2.Response

const val DEFAULT_ERROR_CODE = "100"

class ServiceException(response: Response<Any>) : Exception(
    if (response.code() == 422)
        response.parseErrorResponse().errorCode
    else
        DEFAULT_ERROR_CODE
)
