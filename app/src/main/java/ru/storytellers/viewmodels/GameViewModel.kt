package ru.storytellers.viewmodels

import ru.storytellers.application.StoryTallerApp
import ru.storytellers.engine.Game
import ru.storytellers.engine.level.Levels
import ru.storytellers.model.DataModel
import ru.storytellers.viewmodels.baseviewmodel.BaseViewModel

class GameViewModel(
    private val game: Game,
    levels: Levels

): BaseViewModel<DataModel>() {
    private  val listPlayer= StoryTallerApp.instance.gameStorage.getListPlayers()
    private  val levelGame= levels.getLevelById(StoryTallerApp.instance.gameStorage.getLevelGame())

    fun createNewGame(){
        levelGame?.let { game.newGame(listPlayer, it ) }
    }

}