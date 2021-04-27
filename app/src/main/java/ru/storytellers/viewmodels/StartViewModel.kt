package ru.storytellers.viewmodels


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import ru.storytellers.application.StoryHeroesApp
import ru.storytellers.model.DataModel
import ru.storytellers.model.entity.Story
import ru.storytellers.model.entity.User
import ru.storytellers.model.repository.IStoryRepository
import ru.storytellers.model.repository.IUserLocalRepository
import ru.storytellers.model.repository.IUserRemoteRepository
import ru.storytellers.utils.*
import ru.storytellers.utils.StatHelper.Companion.riseEvent
import ru.storytellers.viewmodels.baseviewmodel.BaseViewModel
import timber.log.Timber


class StartViewModel(
    private val storyRepository: IStoryRepository,
    private val signInGoogleHandler: SignInGoogleHandler,
    private val userRemoteRepository: IUserRemoteRepository,
    private val userLocalRepository: IUserLocalRepository
) : BaseViewModel<DataModel>() {
    private val compositeDisposable: CompositeDisposable by lazy { CompositeDisposable() }
    private val onSuccessLiveData = MutableLiveData<DataModel.Success<Story>>()
    private val onErrorLiveData = MutableLiveData<DataModel.Error>()
    private val onLoadingLiveData = MutableLiveData<DataModel.Loading>()
    private val accountExistsLiveData = MutableLiveData<Boolean>()
    private val userNameLiveData = MutableLiveData<String>()
    private var user: User? = null

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
        val userAccount = signInGoogleHandler.getUserDataFromAccount(account)
        user = convertAccountToUser(userAccount)
        /*
        user?.let {
            getUserFromLocalDB(it)
            getUserFromBackend(it)
        }
         */
        userNameLiveData.value = user?.name
        accountExistsLiveData.value = true
    }

    fun getUserDataFromGoogleAccount(completedTask: Task<GoogleSignInAccount>) {
        val userAccount = signInGoogleHandler.getUserAccount(completedTask)
        user = userAccount?.let { convertAccountToUser(it) }
        user?.let {
            userNameLiveData.value = it.name
            saveUserToBackend(it)
            accountExistsLiveData.value = true
            saveUserToLocalDB(it)
        }
    }

    private fun saveUserToLocalDB(user: User) {
        compositeDisposable.add(
            userLocalRepository.addUser(user)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Timber.d("Saved to database")
                },
                    {
                        Timber.e(it)
                    })
        )
    }

    private fun getUserFromLocalDB(user: User) {
        compositeDisposable.add(
            userLocalRepository.getUserById(user.id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ user ->
                    Timber.d(user.name)
                },
                    {
                        Timber.e(it)
                    })
        )
    }


    private fun saveUserToBackend(user: User) {
        compositeDisposable.add(userRemoteRepository.saveUser(user)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {},
                {
                    Timber.e(it)
                }
            )
        )
    }

    private fun getUserFromBackend(user: User) {
        compositeDisposable.add(
            userRemoteRepository.getUser(user)
                .subscribe({

                }, {

                })
        )
    }

    fun toRulesScreenStatistics() {
        val prop = listOf(
            StatHelper.userId to user?.id.toString(),
            StatHelper.timeEvent to getCurrentDateTime().getString()
        )
        riseEvent(StatHelper.startScreenBtnToRulesGame, prop)
    }

    fun createTaleStatistics() {
        val prop = listOf<Pair<String, String>>()
        user?.let {
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

    fun getAllStory() {
        storyRepository.getAll()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                onSuccessLiveData.value = DataModel.Success(it)
            }, {
                onErrorLiveData.value = DataModel.Error(it)
            })
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}