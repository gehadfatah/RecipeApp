package com.goda.elmenus.util.base

import android.view.View
import androidx.fragment.app.Fragment
import com.goda.elmenus.util.extension.showSnackBar
import com.goda.recipesapp.R


open class BaseFragment : Fragment() {


    protected fun showProgressDialog() {
        activity?.let {

        }
    }

    protected fun hideProgressDialog() {
    }


}
