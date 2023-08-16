package com.arafat1419.argames.core.utils

import com.arafat1419.argames.core.domain.model.GameDomain
import com.arafat1419.argames.core.domain.model.ListDomain
import com.arafat1419.argames.core.domain.model.ScreenshotDomain
import com.arafat1419.argames.core.vo.ErrorData

object DummyData {
    val listGames = ListDomain(
        "",
        "",
        "",
        listOf(
            GameDomain(
                id = 3328,
                name = "The Witcher 3: Wild Hunt",
                backgroundImage = "https://media.rawg.io/media/games/618/618c2031a07bbff6b4f611f10b6bcdbc.jpg",
                released = "2015-05-18",
                description = null,
                rating = "4.66",
                playtime = "45",
                website = null,
                shortScreenshots = listOf(
                    ScreenshotDomain(
                        id = -1,
                        image = "https://media.rawg.io/media/games/618/618c2031a07bbff6b4f611f10b6bcdbc.jpg"
                    ),
                    ScreenshotDomain(
                        id = 30336,
                        image = "https://media.rawg.io/media/screenshots/1ac/1ac19f31974314855ad7be266adeb500.jpg"
                    )
                ),
                publishers = null
            ),

            GameDomain(
                id = 4200,
                name = "Portal 2",
                backgroundImage = "https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg",
                released = "2011-04-18",
                description = null,
                rating = "4.61",
                playtime = "11",
                website = null,
                shortScreenshots = listOf(
                    ScreenshotDomain(
                        id = -1,
                        image = "https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg"
                    ),
                    ScreenshotDomain(
                        id = 99018,
                        image = "https://media.rawg.io/media/screenshots/221/221a03c11e5ff9f765d62f60d4b4cbf5.jpg"
                    )
                ),
                publishers = null
            ),
            GameDomain(
                id = 5286,
                name = "Tomb Raider (2013)",
                backgroundImage = "https://media.rawg.io/media/games/021/021c4e21a1824d2526f925eff6324653.jpg",
                released = "2013-03-05",
                description = null,
                rating = "4.05",
                playtime = "10",
                website = null,
                shortScreenshots = listOf(
                    ScreenshotDomain(
                        id = -1,
                        image = "https://media.rawg.io/media/games/021/021c4e21a1824d2526f925eff6324653.jpg"
                    ),
                    ScreenshotDomain(
                        id = 99160,
                        image = "https://media.rawg.io/media/screenshots/4f9/4f9d5efdecfb63cb99f1baa4c0ceb3bf.jpg"
                    )
                ),
                publishers = null
            )
        )
    )

    val game = listGames.results?.get(0)

    val screenshots = ListDomain(
        "",
        "",
        "",
        listOf(
            ScreenshotDomain(id=1827221, image="https://media.rawg.io/media/screenshots/a7c/a7c43871a54bed6573a6a429451564ef.jpg"),
            ScreenshotDomain(id=1827222, image="https://media.rawg.io/media/screenshots/cf4/cf4367daf6a1e33684bf19adb02d16d6.jpg"),
            ScreenshotDomain(id=1827223, image="https://media.rawg.io/media/screenshots/f95/f9518b1d99210c0cae21fc09e95b4e31.jpg")
        )
    )

    val errorCode = ErrorData("500", "Test error")
}