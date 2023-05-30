package com.goda.elmenus.util.extension

import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

fun BottomSheetDialogFragment.setupFullHeightScreen(bottomSheetDialog: BottomSheetDialog) {
    val bottomSheet =
        bottomSheetDialog.findViewById<View>( com.google.android.material.R.id.design_bottom_sheet) as FrameLayout?
    val behavior = BottomSheetBehavior.from(bottomSheet!!)
    val layoutParams = bottomSheet.layoutParams

    val windowHeight = WindowManager.LayoutParams.MATCH_PARENT
    if (layoutParams != null) {
        layoutParams.height = windowHeight
    }
    bottomSheet.layoutParams = layoutParams
    behavior.state = BottomSheetBehavior.STATE_EXPANDED
}
