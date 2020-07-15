package ru.storytellers.utils

import ru.storytellers.model.entity.Character
import ru.storytellers.model.entity.Player

class PlayerCreator {

    private var idPlayer: Long=0
    private  var namePlayer: String="Player"

    private lateinit var characterOfPlayer: Character

    fun createPlayer(): Player {
        idPlayer++
        return Player(idPlayer,namePlayer,characterOfPlayer)
    }

    fun setNamePlayer(name: String){
        namePlayer=name
    }

    fun setCharacterOfPlayer(character: Character){
        characterOfPlayer=character
    }
}