package ru.storytellers.utils

import ru.storytellers.model.entity.Character
import ru.storytellers.model.entity.Player

class PlayerCreator {

    private var idPlayer: Long=0
    private  var namePlayer: String="Player"

    private  var characterOfPlayer: Character?=null
    private  var player:Player?=null

    fun getPlayer():Player?{
        return if (createPlayer()) player
        else null
    }
    private fun createPlayer(): Boolean {
        idPlayer=getUid()
        characterOfPlayer?.let {player=Player(idPlayer,namePlayer,it)
            idPlayer=0
            namePlayer="Player"
            characterOfPlayer=null
            return true} ?: return false
    }

    fun setNamePlayer(name: String){
        namePlayer=name
    }

    fun setCharacterOfPlayer(character: Character){
        characterOfPlayer=character
    }
}