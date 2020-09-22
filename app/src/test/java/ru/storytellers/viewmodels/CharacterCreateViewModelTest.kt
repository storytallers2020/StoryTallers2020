package ru.storytellers.viewmodels

import android.os.Looper
import android.text.Editable
import android.text.SpannableStringBuilder
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.rxjava3.core.Single
import org.junit.*
import org.junit.Assert.*
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.robolectric.Shadows
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode
import ru.storytellers.di.amplitudeModule
import ru.storytellers.di.gameEngine
import ru.storytellers.model.entity.Character
import ru.storytellers.model.entity.Player
import ru.storytellers.model.repository.CharacterRepository
import ru.storytellers.utils.PlayerCreator

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
        loadKoinModules(listOf(gameEngine, amplitudeModule))
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
    fun `when called CharacterCreateViewModel-transferNamePlayer called and setNamePlayer + getPlayer,`() {

        val editable: Editable = SpannableStringBuilder("Editable")
        val char = mockk<Character>()
        val name = "Slim_Shady"
        val player = Player(1, name, char)

        every { char.name } returns name
        every { playerCreator.setCharacterOfPlayer(char) } returns Unit
        every { playerCreator.setNamePlayer(editable.toString()) } returns Unit
        every { playerCreator.getPlayer() } returns player

        characterCreateViewModel.characterSelected(char)
        characterCreateViewModel.transferNamePlayer(editable)

        verify {
            playerCreator.setNamePlayer(editable.toString())
            playerCreator.getPlayer()
        }
    }

    @Test
    fun `character was selected`() {
        val char = mockk<Character>()
        every { playerCreator.setCharacterOfPlayer(char) } returns Unit
        characterCreateViewModel.characterSelected(char)
        assertTrue(characterCreateViewModel.subscribeOnCharacterSelected().value!!)
    }
}
