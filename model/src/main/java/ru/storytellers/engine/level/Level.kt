package ru.storytellers.engine.level

import ru.storytellers.engine.rules.Rules
import ru.storytellers.engine.showRules.IShowRule
import ru.storytellers.engine.wordRules.IWordRule

data class Level(
    val id: Int,
    val rules: Rules,
    val showRule: IShowRule,
    val wordRule: IWordRule
)