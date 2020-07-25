package ru.storytellers.engine.rules

// Проверяет, что в очередном шаге игрок ввел только одно предложение
class OneSentenceInTextRule : IRule {
    private val dot: Char = '.'

    override fun isCorrect(str: String): Boolean {
            var currentIndex = 0
            str.forEachIndexed { index, char ->
                if (char == dot) {
                    if (currentIndex > 0 && currentIndex != index - 1) return false
                    currentIndex = index
                }
            }
            return true
    }
}