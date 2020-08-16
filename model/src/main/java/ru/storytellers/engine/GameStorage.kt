package ru.storytellers.engine

import ru.storytellers.model.entity.Cover
import ru.storytellers.model.entity.Location
import ru.storytellers.model.entity.Player
import ru.storytellers.model.entity.SentenceOfTale

class GameStorage {
     private val listPlayers= mutableListOf<Player>()
     private var levelGame:Int=0
     private var locationGame:Location?=null
     private val listSentenceOfTale= mutableListOf<SentenceOfTale>()
     private var coverStoryTaller:Cover?=null
     private var titleStoryTaller:String?=null





     fun getTitleStory()=titleStoryTaller
     fun setTitleStory(titleStory:String){
          titleStoryTaller=titleStory
     }
     fun getCoverStoryTaller()=coverStoryTaller
     fun setCoverStoryTaller(coverStory:Cover){
          coverStoryTaller=coverStory
     }

     fun getListPlayers()=listPlayers
     fun getListSentenceOfTale()=listSentenceOfTale

     fun getLevelGame()=levelGame
     fun setLevelGame(lvlGame:Int){
          levelGame=lvlGame
     }
     fun getLocationGame()=locationGame
     fun setLocationGame(location:Location){
          locationGame=location
     }



}