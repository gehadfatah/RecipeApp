package com.goda.elmenus.util.extension

import android.app.Activity
import android.app.Dialog
import android.view.ViewGroup
import android.view.Window



 fun Activity.showLogoutDialog() {

    val dialog = Dialog(this.baseContext)
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.setCancelable(true)
    dialog.setCanceledOnTouchOutside(true)

    dialog.window?.setLayout(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT
    )


    if (this != null)
        dialog.show()
}
