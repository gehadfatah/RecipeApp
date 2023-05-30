package com.goda.elmenus.util.extension

import com.goda.recipesapp.data.Model.ResultData
import retrofit2.HttpException
import java.io.IOException

fun Exception.translateToError(): ResultData.Exception {
    when (this) {
        is HttpException -> {
            return ResultData.Exception(
                this,
                "Something went wrong! ${this.response()?.errorBody()?.string()}"
            )
        }

        is IOException -> {
            return ResultData.Exception(
                this,
                "Couldn't reach server, Check your internet connection"
            )
        }

        else -> {
            return ResultData.Exception(
                this,
                "Something went wrong! ${this.message}"
            )
        }
    }
}
