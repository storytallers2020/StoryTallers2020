package ru.storytellers.utils

import ru.storytellers.application.StoryTallerApp


class StatHelper {
    companion object {
        //Share
        const val timeEvent= "TimeEvent"
        const val userId = "UserId"
        const val buttonBackClicked = "BackClicked"
        //StartScreen
        const val startScreenBtnToRulesGame = "StartScreen|BtnToRulesScreen"
        const val startScreenBtnToCreateTale = "StartScreen|BtnToCreateTale"
        const val startScreenBtnToAboutScreen = "StartScreen|BtnToAboutScreen"
        const val startScreenBtnToLibraryScreen = "StartScreen|BtnToLibraryScreen"
        //LevelScreen
        const val levelScreenBtnToCharacterScreen = "LevelScreen|BtnToCharacterScreen"
        const val levelName = "levelName"
        //CharacterCreateScreen
        const val playerName = "playerName"
        const val characterName = "characterName"
        const val characterScreenBtnPlayerSaved = "CharacterScreen|BtnPlayerSaved"
        //TeamCharacterScreen
        const val playerId = "PlayerId"
        const val playerCount = "PlayerCount"
        const val teamScreenAddPlayerClicked = "TeamScreen|AddPlayerClicked"
        const val teamScreenBtnToLocationScreen = "TeamScreen|BtnToLocationScreen"
        //LocationScreen
        const val locationScreenLocationSelected = "LocationScreen|LocationSelected"
        const val locationName = "locationName"
        const val locationId = "locationId"
        //GameStartScreen
        const val gameStartScreenBtnStartClicked = "GameStartScreen|BtnStartClicked"
        const val gameStartTimeFromGameCreation = "GameStartScreen|TimeFromGameCreation"
        //GameScreen
        const val gamePlayerId = "GamePlayerId"
        const val gamePlayerName = "GamePlayerName"
        const val gameScreenBtnSendClicked = "GameScreen|BtnSendClicked"
        const val gameScreenBtnEndGameClicked = "GameScreen|BtnEndGameClicked"
        const val totalNumberSentencesInStory = "TotalNumberSentencesInStory"
        const val turn = "Turn"
        //GameEndScreen
        const val gameEndScreenBtnCopyClicked = "GameEndScreen|BtnCopyClicked"
        const val gameEndScreenBtnContinueGameClicked = "GameEndScreen|BtnContinueGameClicked"
        const val gameEndScreenBtnSelectCoverClicked = "GameEndScreen|BtnSelectCoverClicked"
        //SelectCoverScreen
        const val selectCoverScreenCoverSelected = "SelectCoverScreen|CoverSelected"
        const val coverName = "CoverName"
        const val coverId = "CoverId"
        //TitleAndSaveStoryScreen
        const val titleAndSaveScreenBtnSaveClicked = "TitleAndSaveScreen|BtnSaveClicked"
        const val storyId = "StoryId"
        const val storyName = "StoryName"
        const val saveStoryFailed = "SaveStoryFailed"
        //LibraryScreen
        const val libraryScreenStorySelected = "LibraryScreen|StorySelected"
        const val libraryScreenMenuItemCopyClicked = "LibraryScreen|MenuItemCopyClicked"
        const val libraryScreenMenuItemDeleteClicked = "LibraryScreen|MenuItemDeleteClicked"
        const val libraryScreenMenuItemShareClicked = "LibraryScreen|MenuItemShareClicked"
        const val libraryScreenBtnStartScreenClicked = "LibraryScreen|BtnStartScreenClicked"
        //LibraryBook
        const val libraryBookScreenMenuItemCopyClicked = "LibraryBookScreen|MenuItemCopyClicked"
        const val libraryBookScreenMenuItemDeleteClicked = "LibraryBookScreen|MenuItemDeleteClicked"
        const val libraryBookScreenMenuItemShareClicked = "LibraryBookScreen|MenuItemShareClicked"

         fun itemClickedStat(itemName:String){
            val prop = listOf(
                timeEvent to getCurrentDateTime().getString()
            )
            StoryTallerApp.instance.stat.riseEvent(itemName, prop.toProperties())
        }
        fun riseEvent(eventName: String,prop: List<Pair<String,String>>) {
            StoryTallerApp.instance.stat.riseEvent(eventName, prop.toProperties())
        }
        fun riseEvent(eventName: String) {
            StoryTallerApp.instance.stat.riseEvent(eventName)
        }
    }
}