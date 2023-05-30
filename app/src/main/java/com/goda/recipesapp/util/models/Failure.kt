package com.goda.elmenus.util.models

import com.google.gson.annotations.SerializedName

data class Failure (
    val  status :String,
    val message :String,
    @SerializedName("errorCode")
    val errorCode :String

)
