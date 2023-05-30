package com.goda.recipesapp

import android.app.Application
import com.goda.elmenus.util.ApplicationIntegration
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class RecipePrepareApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        ApplicationIntegration.with(this)
    }
}