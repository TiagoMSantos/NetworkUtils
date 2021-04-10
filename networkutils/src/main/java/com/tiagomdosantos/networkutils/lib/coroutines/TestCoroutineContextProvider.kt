package com.tiagomdosantos.networkutils.lib.coroutines

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

open class TestCoroutineContextProvider : CoroutineContextProvider() {
    override fun main(): CoroutineDispatcher = Dispatchers.Unconfined
    override fun io(): CoroutineDispatcher = Dispatchers.Unconfined
    override fun computation(): CoroutineDispatcher = Dispatchers.Unconfined
}
