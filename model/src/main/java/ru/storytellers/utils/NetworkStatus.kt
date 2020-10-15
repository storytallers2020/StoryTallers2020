package ru.storytellers.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.subjects.BehaviorSubject
import ru.storytellers.model.network.INetworkStatus

class NetworkStatus(context: Context): INetworkStatus {

    private val statusSubject = BehaviorSubject.create<Boolean>()

    init {
        statusSubject.onNext(false)
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkRequest = NetworkRequest.Builder().build()
        connectivityManager.registerNetworkCallback(networkRequest, object: ConnectivityManager.NetworkCallback() {
            override fun onLost(network: Network) {
                super.onLost(network)
                statusSubject.onNext(false)
            }

            override fun onUnavailable() {
                super.onUnavailable()
                statusSubject.onNext(false)
            }

            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                statusSubject.onNext(true)
            }
        })
    }

    override fun isOnline(): Observable<Boolean> = statusSubject
    override fun isOnlineSingle(): Single<Boolean> = statusSubject.first(false)
}