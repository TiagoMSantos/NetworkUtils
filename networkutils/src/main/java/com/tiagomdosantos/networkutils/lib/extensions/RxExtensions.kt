package com.tiagomdosantos.networkutils.lib.extensions

import com.tiagomdosantos.networkutils.lib.objects.Constants.DEFAULT_WAIT_TIME_MILLIS
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

// region --- DISPOSABLE ---
fun List<Disposable>.disposeAll() {
    this.forEach {
        it.dispose()
    }
}

fun Disposable.dispose(list: List<Disposable>) {
    list.plus(this)
}
// endregion

//region --- Observable ---
private fun <T> Observable<T>.subscribeOnIo(): Observable<T> = subscribeOn(Schedulers.io())
private fun <T> Observable<T>.observeOnMainThread(): Observable<T> =
    observeOn(AndroidSchedulers.mainThread())

fun <T> Observable<T>.saveMainThread(): Observable<T> = subscribeOnIo().observeOnMainThread()
fun <T> Observable<T>.debounce(): Observable<T> = debounce(DEFAULT_WAIT_TIME_MILLIS, TimeUnit.MILLISECONDS)
//endregion

//region --- Single ---
private fun <T> Single<T>.subscribeOnIo(): Single<T> = subscribeOn(Schedulers.io())
private fun <T> Single<T>.observeOnMainThread(): Single<T> =
    observeOn(AndroidSchedulers.mainThread())

fun <T> Single<T>.saveMainThread(): Single<T> = subscribeOnIo().observeOnMainThread()
//endregion

//region --- Completable ---
private fun Completable.subscribeOnIo(): Completable = subscribeOn(Schedulers.io())
private fun Completable.observeOnMainThread(): Completable =
    observeOn(AndroidSchedulers.mainThread())

fun Completable.saveMainThread(): Completable = subscribeOnIo().observeOnMainThread()
//endregion
