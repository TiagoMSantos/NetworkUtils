package com.tiagomdosantos.networkutils.lib.coroutines

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

open class CoroutineContextProvider {
    open fun main(): CoroutineDispatcher = Dispatchers.Main
    open fun io(): CoroutineDispatcher = Dispatchers.IO
    open fun computation(): CoroutineDispatcher = Dispatchers.Default
}
