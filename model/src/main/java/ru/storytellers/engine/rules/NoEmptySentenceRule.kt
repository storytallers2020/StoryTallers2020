package ru.storytellers.engine.rules

// Проверяет, что предлоежние не пустое
class NoEmptySentenceRule : IRule {
    override fun isCorrect(str: String): Boolean = str.isNotEmpty()
}