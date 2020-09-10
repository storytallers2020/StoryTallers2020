package ru.storytellers.utils

import ru.storytellers.application.StoryTallerApp


class StatHelper {
    companion object {
        //Share
        const val timeEvent= "TimeEvent"
        const val buttonBackClicked = "BackClicked"
        //StartScreen
        const val startScreenBtnToRulesGame = "StartScreenBtnToRulesScreen"
        const val userId = "userId"
        const val startScreenBtnToCreateTale = "StartScreenBtnToCreateTale"
        const val startScreenBtnToAboutScreen = "StartScreenBtnToAboutScreen"
        const val startScreenBtnToLibraryScreen = "StartScreenBtnToLibraryScreen"
        //LevelScreen
        const val levelScreenBtnToCharacterScreen = "LevelScreenBtnToCharacterScreen"
        const val levelName = "levelName"
        //CharacterCreateScreen
        const val playerName = "playerName"
        const val characterName = "characterName"
        const val characterScreenBtnPlayerSaved = "CharacterScreenBtnPlayerSaved"
        //TeamCharacterScreen
        const val playerId = "PlayerId"
        const val playerCount = "PlayerCount"
        const val teamScreenAddPlayerClicked = "TeamScreenAddPlayerClicked"
        const val teamScreenBtnToLocationScreen = "TeamScreenBtnToLocationScreen"
        //LocationScreen
        const val locationScreenLocationSelected = "LocationScreenLocationSelected"
        const val locationName = "locationName"
        const val locationId = "locationId"
        //GameStartScreen
        const val gameStartScreenBtnStartClicked = "GameStartScreenBtnStartClicked"
        //GameScreen
        const val gamePlayerId = "GamePlayerId"
        const val gamePlayerName = "GamePlayerName"
        const val gameScreenBtnSendClicked = "GameScreenBtnSendClicked"
        const val turn = "Turn"
        //GameEndScreen
        const val gameEndScreenBtnCopyClicked = "GameEndScreenBtnCopyClicked"
        const val gameEndScreenBtnContinueGameClicked = "GameEndScreenBtnContinueGameClicked"
        const val gameEndScreenBtnSelectCoverClicked = "GameEndScreenBtnSelectCoverClicked"
        //SelectCoverScreen
        const val selectCoverScreenCoverSelected = "SelectCoverScreenCoverSelected"
        const val coverName = "CoverName"
        const val coverId = "CoverId"
        //TitleAndSaveStoryScreen
        const val titleAndSaveScreenBtnSaveClicked = "TitleAndSaveScreenBtnSaveClicked"
        const val storyId = "StoryId"
        const val storyName = "StoryName"
        const val saveStoryFailed = "SaveStoryFailed"
        //LibraryScreen
        const val libraryScreenStorySelected = "LibraryScreenStorySelected"
        const val libraryScreenMenuItemCopyClicked = "LibraryScreenMenuItemCopyClicked"
        const val libraryScreenMenuItemDeleteClicked = "LibraryScreenMenuItemDeleteClicked"
        const val libraryScreenMenuItemShareClicked = "LibraryScreenMenuItemShareClicked"
        const val libraryScreenBtnStartScreenClicked = "LibraryScreenBtnStartScreenClicked"
        //LibraryBook
        const val libraryBookScreenMenuItemCopyClicked = "LibraryBookScreenMenuItemCopyClicked"
        const val libraryBookScreenMenuItemDeleteClicked = "LibraryBookScreenMenuItemDeleteClicked"
        const val libraryBookScreenMenuItemShareClicked = "LibraryBookScreenMenuItemShareClicked"

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