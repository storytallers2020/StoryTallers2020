package ru.storytellers.utils

class StatHelper {
    companion object {
        //LevelScreen
        const val levelScreenBackClicked = "levelScreenBackClicked"
        const val levelScreenNextClicked = "levelScreenNextClicked"
        const val levelName = "levelName"
        const val time = "selectLevelTime"
        //CharacterCreateScreen
        const val playerName = "playerName"
        const val characterName = "characterName"
        //TeamCharacterScreen
        const val playerId = "playerId"
        const val playerCount = "playerCount"
        const val addPlayerClicked = "addPlayerClicked"
        const val onPlayerAdded = "onPlayerAdded"
        const val characterScreenNextClicked = "characterScreenNextClicked"

        //StartScreen
        const val onRulesGame = "onRulesGame"
        const val userId = "userId"
        const val timeOnRulesGame = "toRulesGameTime"
        const val timeStartCreateTale = "timeStartCreateTale"
        const val startCreateTale = "StartCreateTale"
        const val onAboutScreen = "onAboutScreen"
        const val onLibraryScreen = "toLibraryScreen"
        const val timeToLibraryScreen = "timeToLibraryScreen"
        //LocationScreen
        const val onLocationChoice = "onLocationChoice"
        const val locationName = "locationName"
        const val timeLocationChoice = "timeLocationChoice"
        const val locationId = "locationId"
        //GameScreen
        const val onGameScreen = "GameScreen"
        const val gamePlayerId = "GamePlayerId"
        const val gamePlayerName = "GamePlayerName"
        const val createSentenceTime = "CreateSentenceTime"
        const val buttonSendClicked = "ButtonSendClicked"
        const val turn = "Turn"
        //SelectCoverScreen
        const val onSelectCoverScreen = "SelectCoverScreen"
        const val coverSelected="CoverSelected"
        const val selectCoverTime = "SelectCoverTime"
        const val coverName = "CoverName"
        const val coverId = "CoverId"
        //GameStartScreen
        const val onGameStartScreen = "GameStartScreen"
        const val buttonStartClicked = "ButtonStartGameClicked"
        const val numberOfPlayersGame = "NumberOfPlayersGame"
        const val selectedLevelGame = "SelectedLevelGame"
        const val createGameTime = "CreateGameTime"
        //TitleAndSaveStoryScreen
        const val onTitleAndSaveStoryScreen = "TitleAndSaveStoryScreen"
        const val storyId = "StoryId"
        const val storyName = "StoryName"
        const val saveStoryTime = "SaveStoryTime"
        const val saveStoryFailed = "SaveStoryFailed"
        const val buttonSaveStoryClicked = "ButtonSaveStoryClicked"
    }
}