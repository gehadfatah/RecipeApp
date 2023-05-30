package com.goda.elmenus.util.extension

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar
/**
 * Shorthand extension function to make view gone
 */
fun View.makeGone() {
    this.visibility = View.GONE
}

/**
 * Shorthand extension function to make view visible
 */
fun View.makeVisible() {
    this.visibility = View.VISIBLE
}

fun EditText.hideKeyboard() {
    val keyboard: InputMethodManager? =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
    keyboard?.hideSoftInputFromWindow(windowToken, 0)
}

fun View.showSnackBar(
    message: String,
    action: String = " ",
    actionListener: () -> Unit = {}
): Snackbar {
    val snackbar = Snackbar.make(this, message, Snackbar.LENGTH_SHORT)
    if (action.isNotBlank()) {
        snackbar.duration = Snackbar.LENGTH_SHORT
        snackbar.setAction(action) {
            actionListener()
            snackbar.dismiss()
        }
    }
    snackbar.show()
    return snackbar
}

