package com.friendycar.owner.util.extension

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.SharedPreferences

import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.goda.elmenus.util.ApplicationIntegration
import com.goda.elmenus.util.PREFERENCES_NAME


fun Context.showLongToast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
}

fun Context.showLongToast(msgId: Int) {
    Toast.makeText(this, getString(msgId), Toast.LENGTH_SHORT).show()
}

fun Context.showShortToast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

val pref: SharedPreferences by lazy {
    ApplicationIntegration.getApplication()
        .getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
}
fun Context.showShortToast(msgId: Int) {
    Toast.makeText(this, getString(msgId), Toast.LENGTH_SHORT).show()
}

fun Context.unwrap(): Activity {
    var context = this
    while (context !is Activity && context is ContextWrapper) {
        context = (this as ContextWrapper).baseContext
    }
    return context as Activity
}

fun Context.getDeviceMetrics(): DisplayMetrics {
    val metrics = DisplayMetrics()
    val wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val display = wm.defaultDisplay
    display.getMetrics(metrics)
    return metrics
}



fun Context.hideKeyboard(view: View?) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
}

fun Activity.showKeyboard() {
    val view = this.currentFocus
    val methodManager = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    //assert(view != null)
    if (view != null)
        methodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
}

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Activity.hideKeyboard() {
    hideKeyboard(if (currentFocus == null) View(this) else currentFocus)
}







