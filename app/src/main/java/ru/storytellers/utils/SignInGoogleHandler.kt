package ru.storytellers.utils

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import ru.storytellers.model.entity.UserAccount
import timber.log.Timber

class SignInGoogleHandler {

    fun getUserAccount(completedTask: Task<GoogleSignInAccount>) : UserAccount? {
        var userAccountData : UserAccount? = null
        try {
            val account = completedTask.getResult(ApiException::class.java)
            userAccountData = getUserDataFromAccount(account)
        } catch (err: ApiException) {
            Timber.e(err)
        }
        return userAccountData
    }

    private fun getUserDataFromAccount(account: GoogleSignInAccount) = account.run {
            UserAccount(
                displayName ?: "No name",
                email ?: "No email",
                id ?: "No id",
                idToken ?: "No idToken",
                photoUrl
            )
        }
}