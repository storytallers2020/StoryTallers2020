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
import ru.storytellers.utils.SignInGoogleHandler
import ru.storytellers.utils.StatHelper
import ru.storytellers.utils.StatHelper.Companion.riseEvent
import ru.storytellers.utils.getCurrentDateTime
import ru.storytellers.utils.getString
import ru.storytellers.viewmodels.baseviewmodel.BaseViewModel
import timber.log.Timber


class StartViewModel(
    private val storyRepository: IStoryRepository,
    private val signInGoogleHandler: SignInGoogleHandler
) : BaseViewModel<DataModel>() {
    private val onSuccessLiveData = MutableLiveData<DataModel.Success<Story>>()
    private val onErrorLiveData = MutableLiveData<DataModel.Error>()
    private val onLoadingLiveData = MutableLiveData<DataModel.Loading>()
    private val accountExistsLiveData = MutableLiveData<Boolean>()
    private var userAccount: UserAccount? = null

    fun subscribeOnAccountExists(): LiveData<Boolean> {
        return accountExistsLiveData
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

    fun checkLastSignedInAccount(account: GoogleSignInAccount?) {
        accountExistsLiveData.value = account?.let { true } ?: false
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
            with(prop) {
                plus(StatHelper.timeEvent to getCurrentDateTime().getString())
                plus(StatHelper.userId to it.id)
                plus(StatHelper.userName to it.name)
            }
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

    fun getUserAccount(completedTask: Task<GoogleSignInAccount>) {
        userAccount = signInGoogleHandler.getUserAccount(completedTask)
        Timber.d(userAccount?.name)
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