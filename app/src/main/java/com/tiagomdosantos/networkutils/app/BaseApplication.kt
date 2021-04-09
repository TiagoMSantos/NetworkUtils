package com.tiagomdosantos.utils.app

import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDexApplication

class BaseApplication : MultiDexApplication() {

    init {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }
}