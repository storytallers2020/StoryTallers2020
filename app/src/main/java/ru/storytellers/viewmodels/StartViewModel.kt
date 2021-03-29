package ru.storytellers.viewmodels


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import ru.storytellers.application.StoryHeroesApp
import ru.storytellers.model.DataModel
import ru.storytellers.model.entity.Story
import ru.storytellers.model.entity.UserAccount
import ru.storytellers.model.repository.IStoryRepository
import ru.storytellers.model.repository.IUserAccountRepository
import ru.storytellers.utils.SignInGoogleHandler
import ru.storytellers.utils.StatHelper
import ru.storytellers.utils.StatHelper.Companion.riseEvent
import ru.storytellers.utils.getCurrentDateTime
import ru.storytellers.utils.getString
import ru.storytellers.viewmodels.baseviewmodel.BaseViewModel
import timber.log.Timber


class StartViewModel(
    private val storyRepository: IStoryRepository,
    private val signInGoogleHandler: SignInGoogleHandler,
    private val userAccountRepository: IUserAccountRepository
) : BaseViewModel<DataModel>() {
    private val onSuccessLiveData = MutableLiveData<DataModel.Success<Story>>()
    private val onErrorLiveData = MutableLiveData<DataModel.Error>()
    private val onLoadingLiveData = MutableLiveData<DataModel.Loading>()
    private val accountExistsLiveData = MutableLiveData<Boolean>()
    private val userNameLiveData = MutableLiveData<String>()
    private var userAccount: UserAccount? = null

    init {
        accountExistsLiveData.value = false
    }

    fun subscribeOnAccountExists(): LiveData<Boolean> {
        return accountExistsLiveData
    }

    fun subscribeOnUserName(): LiveData<String> {
        return userNameLiveData
    }

    fun subscribeOnSuccess(): LiveData<DataModel.Success<Story>> {
        return onSuccessLiveData
    }

    fun subscribeOnError(): LiveData<DataModel.Error> {
        return onErrorLiveData
    }

    fun subscribeOnLoading(): LiveData<DataModel.Loading> {
        return onLoadingLiveData
    }

    fun getUserDataFromLastSignedAccount(account: GoogleSignInAccount) {
        userAccount = signInGoogleHandler.getUserDataFromAccount(account)
        userNameLiveData.value = userAccount?.name
        accountExistsLiveData.value = true
    }

    fun getUserDataFromGoogleAccount(completedTask: Task<GoogleSignInAccount>) {
        userAccount = signInGoogleHandler.getUserAccount(completedTask)
        userAccount?.let {
            userNameLiveData.value = it.name
            saveUserAccount(it)
            accountExistsLiveData.value = true
            Timber.d(it.name)
        }
    }

    fun toRulesScreenStatistics() {
        val prop = listOf(
            StatHelper.userId to "userId", // заглушка пока что
            StatHelper.timeEvent to getCurrentDateTime().getString()
        )
        riseEvent(StatHelper.startScreenBtnToRulesGame, prop)
    }

    fun createTaleStatistics() {
        val prop = listOf<Pair<String, String>>()
        userAccount?.let {
            prop.plus(StatHelper.timeEvent to getCurrentDateTime().getString())
                .plus(StatHelper.userId to it.id)
                .plus(StatHelper.userName to it.name)
        }
        riseEvent(StatHelper.startScreenBtnToCreateTale, prop)
    }

    fun onLibraryScreenStatistics() {
        val prop = listOf(
            StatHelper.timeEvent to getCurrentDateTime().getString()
        )
        riseEvent(StatHelper.startScreenBtnToLibraryScreen, prop)
    }

    fun timeCreateStory() {
        StoryHeroesApp.instance.gameStorage.setTimeCreateStory(getCurrentDateTime().time)
    }

    fun onAboutScreenStatistics() {
        val prop = listOf(
            StatHelper.timeEvent to getCurrentDateTime().getString()
        )
        riseEvent(StatHelper.startScreenBtnToAboutScreen, prop)
    }

    fun onStartScreenNumberOfTaleStat(numberOfTale: Int) {
        val prop = listOf(
            StatHelper.startScreenNumberOfTale to numberOfTale.toString()
        )
        riseEvent(StatHelper.startScreen, prop)
    }


    private fun saveUserAccount(userAccount: UserAccount) {
        var saved = false
        userAccountRepository.saveUser(userAccount)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    saved = true
                },
                {
                    Timber.e(it)
                }
            )
    }

    fun getAllStory() {
        storyRepository.getAll()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                onSuccessLiveData.value = DataModel.Success(it)
            }, {
                onErrorLiveData.value = DataModel.Error(it)
            })
    }

}