package com.example.us.ui.screen.games

object HeartRanks {
    val names = listOf(
        "Незнакомец",
        "Симпатия",
        "Милое Сердце",
        "Теплый Луч",
        "Очаровательный",
        "Неравнодушный",
        "Сияющий",
        "Покоритель Улыбок",
        "Хранитель Тепла",
        "Источник Радости",
        "Светлый Спутник",
        "Вдохновляющий",
        "Утешающий",
        "Защитник Сердец",
        "Магнетический",
        "Нежный Ветер",
        "Лунный Свет",
        "Пленитель Души",
        "Пылающее Сердце",
        "Единственный"
    )

    // Порог — общее количество кликов, необходимое чтобы **достичь** уровня (соответствует индексу)
    val thresholds = listOf(
        0, 20, 50, 100, 180, 280, 400, 550,
        750, 1000, 1300, 1700, 2200, 2800,
        3500, 4300, 5200, 6200, 7400, 9000
    )

    fun levelForClicks(clicks: Int): Int {
        // возвращает индекс уровня (0..names.lastIndex)
        var lvl = 0
        for (i in thresholds.indices) {
            if (clicks >= thresholds[i]) lvl = i
            else break
        }
        return lvl
    }

    fun nextThresholdFor(levelIndex: Int): Int {
        val nextIndex = (levelIndex + 1).coerceAtMost(thresholds.lastIndex)
        return thresholds[nextIndex]
    }
}
