package ru.storytellers.engine

import ru.storytellers.model.entity.Player

class GameStorage {
     private val listPlayers= mutableListOf<Player>()

     fun getListPlayers()=listPlayers


}