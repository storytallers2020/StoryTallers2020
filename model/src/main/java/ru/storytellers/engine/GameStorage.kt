package ru.storytellers.engine

import ru.storytellers.model.entity.Location
import ru.storytellers.model.entity.Player
import ru.storytellers.model.entity.SentenceOfTale

class GameStorage {
     private val listPlayers= mutableListOf<Player>()
     private var levelGame:Int=0
     private var locationGame:Location?=null
     private val listSentenceOfTale= mutableListOf<SentenceOfTale?>()





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