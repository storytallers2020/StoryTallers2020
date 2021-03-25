package ru.storytellers.model.repository

import android.net.Uri
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Response
import ru.storytellers.model.datasource.remote.IRemoteDataSource
import ru.storytellers.model.datasource.remote.api.UserAccountApi
import ru.storytellers.model.entity.UserAccount
import ru.storytellers.model.network.INetworkStatus
import ru.storytellers.utils.convertAccountApiToAccount
import ru.storytellers.utils.convertAccountToAccountApi

class UserAccountRepository(
    private val networkStatus: INetworkStatus,
    private val remoteDataSource: IRemoteDataSource
) : IUserAccountRepository{

    override fun saveUserAccount(userAccount: UserAccount) : Single<UserAccount> =
    networkStatus.isOnlineSingle().flatMap { isOnline ->
        val userAccountApi = convertAccountToAccountApi(userAccount)
        if (isOnline) {
            remoteDataSource.saveUserAccount(userAccountApi).map { response ->
                if (response.isSuccessful){
                    val body = response.body()
                    val usAccount = body?.let {
                        convertAccountApiToAccount(it) }
                     usAccount ?: UserAccount(
                         "No name",
                         "No email",
                         "No number",
                         "No idToken",
                         "No face" as Uri
                     )
                } else {
                    UserAccount(
                         "No name",
                         "No email",
                         "No id",
                         "No idToken",
                        "" as Uri
                    )
                }
            }
        } else {
            // заглушка, потом данные будут из кэша
            Single.just(UserAccount(
                "No name",
                "No email",
                "No number",
                "No idToken",
                "https://pngicon.ru/file/uploads/vinni-pukh-v-png.png" as Uri
            ))
        }
    }.subscribeOn(Schedulers.io())

    /*
    override fun saveUserAccountCompletable(userAccount: UserAccount): Completable =
        Single.just(convertAccountToAccountApi(userAccount))
            .flatMapCompletable { userAccountApi ->
            remoteDataSource.saveUserAccountCompletable(userAccountApi)
        }.subscribeOn(Schedulers.io())

     */

    override fun saveUserAccountCompletable(userAccount: UserAccount): Completable =
        remoteDataSource.saveUserAccountCompletable(convertAccountToAccountApi(userAccount))
            .subscribeOn(Schedulers.io())
            // remoteDataSource.saveUserAccount(convertAccountToAccountApi(userAccount))
}