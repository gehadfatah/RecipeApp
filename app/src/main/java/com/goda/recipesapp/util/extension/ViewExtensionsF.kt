package com.goda.elmenus.util.extension


import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.TypedValue
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.ScrollView
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView

import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.goda.recipesapp.R

import com.google.android.material.snackbar.Snackbar



fun View.toggleVisibility(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.GONE
}

fun View.toggleVisibilityWithInvisible(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.INVISIBLE
}
fun Activity.setTransparentStatusBar() {
    window.decorView.systemUiVisibility =
        View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.statusBarColor = this.getColor(R.color.black_20)
        }
    }
}

fun scrollToView(scrollViewParent: ScrollView, view: View) {
    // Get deepChild Offset
    val childOffset = Point()
    getDeepChildOffset(scrollViewParent, view.parent, view, childOffset)
    // Scroll to child.
    scrollViewParent.smoothScrollTo(0, childOffset.y)
}

fun getDeepChildOffset(
    mainParent: ViewGroup,
    parent: ViewParent,
    child: View,
    accumulatedOffset: Point
) {
    val parentGroup = parent as ViewGroup
    accumulatedOffset.x += child.left
    accumulatedOffset.y += child.top
    if (parentGroup == mainParent) {
        return
    }
    getDeepChildOffset(mainParent, parentGroup.parent, parentGroup, accumulatedOffset)
}


infix fun View.onClick(action: (() -> Unit)) {
    this.setOnClickListener { action.invoke() }
}



lateinit var mProgressDialog: ProgressDialog

fun Fragment.showLoading(cancelable: Boolean) {
    hideLoading()
    mProgressDialog = showLoadingDialog(this.context, cancelable)
}
fun View.showOrHideLoding(enabled:Boolean) {
    this.toggleVisibility(enabled)

}
fun showLoadingDialog(
    context: Context?,
    canelable: Boolean
): ProgressDialog {
    val progressDialog = ProgressDialog(context)
    progressDialog.show()
    progressDialog.setCancelable(canelable)
    return progressDialog
}

fun hideLoading() {
    if (::mProgressDialog.isInitialized&& mProgressDialog.isShowing) {
        mProgressDialog.cancel()
    }
}

fun Activity.setWindowFlag() {
    if (Build.VERSION.SDK_INT in 19..20) {
        setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true)
    }
    if (Build.VERSION.SDK_INT >= 19) {
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
    }
    if (Build.VERSION.SDK_INT >= 21) {
        setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
        // window.statusBarColor = Color.TRANSPARENT
        window.statusBarColor = ContextCompat.getColor(this, R.color.black_20)

    }
}

fun FragmentActivity.getNavFragment(navView: Int): Fragment? {
    val fragment: Fragment? = null

    try {
        val navHost = this.supportFragmentManager.findFragmentById(navView)
        navHost?.let { navFragment ->
            navFragment.childFragmentManager.primaryNavigationFragment?.let { fragment ->
                return fragment
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return fragment
}

fun setWindowFlag(activity: Activity, bits: Int, on: Boolean) {

    val win = activity.window
    val winParams = win.attributes
    if (on) {
        winParams.flags = winParams.flags or bits
    } else {
        winParams.flags = winParams.flags and bits.inv()
    }
    win.attributes = winParams
}

fun ImageView.loadImage(imageUrl: String?, placeholder: Int=0) {
    imageUrl?.let {
        if (!TextUtils.isEmpty(it)) {
            val requestOptions = RequestOptions()
            // requestOptions.placeholder(R.drawable.loading_animation)
            // requestOptions.error(R.drawable.ic_broken_image)
            Glide.with(this.context)
                .load(imageUrl)

                .placeholder(placeholder)
                .apply(requestOptions)
                .into(this)
        }
    }
}
fun Context.sendWhatsup() {
    val phone="+201050863854"
    val url = "https://api.whatsapp.com/send?phone=$phone"
    val i = Intent(Intent.ACTION_VIEW)
    i.data = Uri.parse(url)
    startActivity(i)
}
fun ImageView.loadImage(imageUrl: String?, placeholder: Int, error: Int) {
    imageUrl?.let {
        //if (!TextUtils.isEmpty(it)) {
            val requestOptions = RequestOptions()
            // requestOptions.placeholder(R.drawable.loading_animation)
            // requestOptions.error(R.drawable.ic_broken_image)
            Glide.with(this.context)
                .load(imageUrl)
                .placeholder(placeholder)
                .error(error)
                .apply(requestOptions)
                .into(this)
        }
   // }
}

fun View.showSnackbar(message: String) {
    if (!TextUtils.isEmpty(message)) {
        val snackBar = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
        snackBar.setActionTextColor(Color.WHITE)
        val sbView = snackBar.view
        sbView.setBackgroundColor(ContextCompat.getColor(this.context, R.color.black_20))
        //        sbView.background=(ContextCompat.getColor(this.context, R.color.grend))
        this.hideKeyboard()
        snackBar.show()
    }
}

fun View.showSnackbar(message: String, intResourse: Int) {
    if (!TextUtils.isEmpty(message)) {
        val snackBar = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
        snackBar.setActionTextColor(Color.WHITE)
        val sbView = snackBar.view
        sbView.setBackgroundColor(ContextCompat.getColor(this.context, intResourse))
        //        sbView.background=(ContextCompat.getColor(this.context, R.color.grend))
        this.hideKeyboard()

        snackBar.show()
    }
}


fun Activity.navigateActivity(activity: Class<*>) {
    val intent = Intent(this, activity)
    startActivity(intent)

}
fun Activity.navigateActivity(flag: Int,activity: Class<*>) {
    val intent = Intent(this, activity)
    intent.flags=flag
    startActivity(intent)

}
/*fun Context.sendWhatsup(activity: Activity,eventString: String="whatsapp") {
    (activity as IBaseActivity).logEvent(eventString)
    val phone=if ( getCurrency()=="AED")"+971585002365" else "+201098588886"
    val url = "https://api.whatsapp.com/send?phone=$phone"
    val i = Intent(Intent.ACTION_VIEW)
    i.data = Uri.parse(url)
    startActivity(i)
}*/

fun Fragment.navigateToWebView(uri: String?) {
    val i = Intent(Intent.ACTION_VIEW)
    i.data = Uri.parse(uri)
    startActivity(i)
}

fun View.margin(
    left: Float? = null,
    top: Float? = null,
    right: Float? = null,
    bottom: Float? = null
) {
    layoutParams<ViewGroup.MarginLayoutParams> {
        left?.run { leftMargin = dpToPx(this) }
        top?.run { topMargin = dpToPx(this) }
        right?.run { rightMargin = dpToPx(this) }
        bottom?.run { bottomMargin = dpToPx(this) }
    }
}

inline fun <reified T : ViewGroup.LayoutParams> View.layoutParams(block: T.() -> Unit) {
    if (layoutParams is T) block(layoutParams as T)
}

fun View.dpToPx(dp: Float): Int = context.dpToPx(dp)
fun Context.dpToPx(dp: Float): Int =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics).toInt()

fun View.hideKeyboard() {
    val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputManager.hideSoftInputFromWindow(windowToken, 0)
}

fun View.showKeyboard() {
    val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputManager.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}
fun View.showOrHide() {
    (this as EditText).onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
        if (hasFocus) {
            //  if (height > 0) {
            //keyboard is shown
            showKeyboard()
            //  }

        } else hideKeyboard()
    }
   /* (this as EditText).setOnTouchListener(object : View.OnTouchListener {
        override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
            if (p0 is EditText) {
                p0.focusable(tru); // User touched edittext
            }
            return false;
        }
    })*/

}
fun RecyclerView.setDivider(@DrawableRes drawableRes: Int) {
    val divider = DividerItemDecoration(
        this.context,
        DividerItemDecoration.VERTICAL
    )
    val drawable = ContextCompat.getDrawable(
        this.context,
        drawableRes
    )
    drawable?.let {
        divider.setDrawable(it)
        addItemDecoration(divider)
    }
}

fun ViewGroup.deepForEach(function: View.() -> Unit) {
    this.forEach { child ->
        child.function()
        if (child is ViewGroup) {
            child.deepForEach(function)
        }
    }
}

inline fun <FRAGMENT : Fragment> FRAGMENT.putArgs(argsBuilder: Bundle.() -> Unit): FRAGMENT =
    this.apply { arguments = Bundle().apply(argsBuilder) }





fun GradientDrawable.setCornerRadius(
    topLeft: Float = 0F,
    topRight: Float = 0F,
    bottomRight: Float = 0F,
    bottomLeft: Float = 0F
) {
    cornerRadii = arrayOf(
        topLeft, topLeft,
        topRight, topRight,
        bottomRight, bottomRight,
        bottomLeft, bottomLeft
    ).toFloatArray()
}
