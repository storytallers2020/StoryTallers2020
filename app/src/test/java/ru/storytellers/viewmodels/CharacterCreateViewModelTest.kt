package ru.storytellers.viewmodels

import android.os.Looper
import android.text.Editable
import android.text.SpannableStringBuilder
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.mockk.*
import io.mockk.impl.annotations.SpyK
import io.reactivex.rxjava3.core.Single
import org.junit.After
import org.junit.Before

import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.robolectric.Shadows
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode
import ru.storytellers.di.gameEngine
import ru.storytellers.model.entity.Character
import ru.storytellers.model.entity.Player
import ru.storytellers.model.repository.CharacterRepository
import ru.storytellers.utils.PlayerCreator
import ru.storytellers.utils.trimSentenceSpace

@RunWith(AndroidJUnit4::class)
@LooperMode(LooperMode.Mode.PAUSED)
@Config(sdk = [28])
class CharacterCreateViewModelTest : KoinTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val characterRepository = mockk<CharacterRepository>()
    private val playerCreator = mockk<PlayerCreator>()
    private lateinit var characterCreateViewModel: CharacterCreateViewModel


    @Before
    fun setUp() {
        loadKoinModules(gameEngine)
        characterCreateViewModel = CharacterCreateViewModel(characterRepository, playerCreator)
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `get all character from character repository`() {
        val testCharacter1 = mockk<Character>()
        val testCharacter2 = mockk<Character>()
        val testListCharacter = listOf<Character>(testCharacter1, testCharacter2)

        every { characterRepository.getAll() } returns Single.just(testListCharacter)

        characterCreateViewModel.getAllCharacters()

        Shadows.shadowOf(Looper.getMainLooper()).idle()
        val listCharacterFromCharacterRepo =
            characterCreateViewModel.subscribeOnSuccess().value?.data

        assertEquals(testListCharacter, listCharacterFromCharacterRepo)
        verify { characterRepository.getAll() }
    }

    @Test
    fun `on start list of players is empty`() {
        val listCharacterFromCharacterRepo =
            characterCreateViewModel.subscribeOnSuccess().value?.data
        assertNull(listCharacterFromCharacterRepo)
    }

    @Test
    fun `character select is successfully and PlayerCreator setCharacter was called`() {
        val testCharacter = mockk<Character>()
        every { playerCreator.setCharacterOfPlayer(testCharacter) } returns Unit

        characterCreateViewModel.characterSelected(testCharacter)

        assertTrue(characterCreateViewModel.isCharacterSelected)
        verify { playerCreator.setCharacterOfPlayer(testCharacter) }
    }

    @Test
    fun `gg`() {
        val editable: Editable = SpannableStringBuilder("Editable")
        val player = mockk<Player>()
        val editableValue = editable.toString()
//        every { playerCreator.setNamePlayer(editableValue.trimSentenceSpace()) } returns Unit
//        every { playerCreator.getPlayer() } returns player

        characterCreateViewModel.transferNamePlayer(editable)

//        verify {
////            playerCreator.setNamePlayer(editableValue.trimSentenceSpace())
//            playerCreator.getPlayer()
//        }
    }
}
