package com.goda.elmenus.util.extension

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.ByteArrayOutputStream


fun Bitmap.toByteArray(quality: Int = 100): ByteArray =
    ByteArrayOutputStream().also {
        compress(Bitmap.CompressFormat.JPEG, quality, it)
    }.toByteArray()

fun ByteArray.toBitmap(): Bitmap? = BitmapFactory.decodeByteArray(this, 0, this.size);
