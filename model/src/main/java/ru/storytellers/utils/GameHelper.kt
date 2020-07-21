package ru.storytellers.utils


fun getPlayerNumByTurn(turn: Int, playersCount: Int) =
    turn % playersCount

