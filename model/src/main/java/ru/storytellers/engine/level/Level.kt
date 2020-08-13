package ru.storytellers.engine.level

import ru.storytellers.engine.rules.Rules
import ru.storytellers.engine.showRules.IShowRule

data class Level(val id: Int, val rules: Rules, val showRule: IShowRule)