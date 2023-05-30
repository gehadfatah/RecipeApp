package com.goda.elmenus.util

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.GsonBuilder
import io.reactivex.Completable
import io.reactivex.Single


val preferencesGateway by lazy { PreferencesGateway() }

const val PREFERENCES_NAME = "FoodRecipes_PREFERENCE"
const val KEY_USER_ID = "user_id"
const val KEY_TOKEN = "token"
const val CheckedCarPosition = "CheckedCarPosition"
const val KEY_ADD_TOKEN = "add_token"


class PreferencesGateway {

    inline fun <reified T : Any> save(key: String, value: T): Single<T> {
        return Single.fromCallable {
            ApplicationIntegration.getApplication()
                .getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
                .edit()
                .apply { putValue(key, value) }
                .apply()
            value
        }
    }

    inline fun <reified T : Any> load(key: String, defaultValue: T): Single<T> {
        return Single.fromCallable {
            ApplicationIntegration.getApplication()
                .getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
                .run { getValue(key, defaultValue) }
        }
    }

    fun isSaved(key: String): Single<Boolean> {
        return Single.fromCallable {
            ApplicationIntegration.getApplication()
                .getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
                .contains(key)
        }
    }

    fun remove(key: String): Completable {
        return Completable.fromAction {
            ApplicationIntegration.getApplication()
                .getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
                .edit()
                .remove(key)
                .apply()
        }
    }

}

inline fun <reified T : Any> SharedPreferences.Editor.putValue(
    key: String,
    value: T
) {
    when (T::class) {
        Boolean::class -> putBoolean(key, value as Boolean)
        Int::class -> putInt(key, value as Int)
        Long::class -> putLong(key, value as Long)
        Float::class -> putFloat(key, value as Float)
        String::class -> putString(key, value as String)
        else -> throw UnsupportedOperationException("not supported preferences type")
    }
}

inline fun <reified T : Any> SharedPreferences.getValue(
    key: String,
    defaultValue: T
): T {
    return when (T::class) {
        Boolean::class -> getBoolean(key, defaultValue as Boolean) as T
        Int::class -> getInt(key, defaultValue as Int) as T
        Long::class -> getLong(key, defaultValue as Long) as T
        Float::class -> getFloat(key, defaultValue as Float) as T
        String::class -> getString(key, defaultValue as String) as T
        else -> throw UnsupportedOperationException("not supported preferences type")
    }
}

fun <T> SharedPreferences.Editor.put(obj: T, key: String) {
    //Convert object to JSON String.
    val jsonString = GsonBuilder().create().toJson(obj)
    //Save that String in SharedPreferences
    putString(key, jsonString).apply()
}

inline fun <reified T> SharedPreferences.get(key: String): T? {
    //We read JSON String which was saved.
    val value = this.getString(key, null)
    //JSON String was found which means object can be read.
    //We convert this JSON String to model object. Parameter "c" (of
    //type “T” is used to cast.
    return GsonBuilder().create().fromJson(value, T::class.java)
}




fun SharedPreferences.setUserData(userId: String, token: String) {
    this
        .edit()
        .apply { putValue(KEY_USER_ID, userId) }
        .apply { putValue(KEY_USER_ID, userId) }
        .apply { putValue(KEY_TOKEN, token) }
        .apply()

}


