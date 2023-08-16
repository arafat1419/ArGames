package com.arafat1419.argames.core.data.network.datasource

import com.arafat1419.argames.core.data.network.api.ApiResponse
import com.arafat1419.argames.core.data.network.api.ApiService
import com.arafat1419.argames.core.utils.DataMapper
import com.arafat1419.argames.core.utils.DummyData
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkDataSourceTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var networkDataSource: NetworkDataSource
    private lateinit var apiService: ApiService

    @Before
    fun setUp() {

        TestCoroutineScope()

        mockWebServer = MockWebServer()
        mockWebServer.start()

        apiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)

        networkDataSource = NetworkDataSource(apiService)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `Test listGames method with real API data`() = runBlocking {
        // Given: Enqueue a mock response with actual API data
        val jsonResponse = """
            {
              "count": "",
              "next": "",
              "previous": "",
              "results": [
                {
                  "id": 3328,
                  "name": "The Witcher 3: Wild Hunt",
                  "background_image": "https://media.rawg.io/media/games/618/618c2031a07bbff6b4f611f10b6bcdbc.jpg",
                  "released": "2015-05-18",
                  "description": null,
                  "rating": "4.66",
                  "playtime": "45",
                  "website": null,
                  "short_screenshots": [
                    {
                      "id": -1,
                      "image": "https://media.rawg.io/media/games/618/618c2031a07bbff6b4f611f10b6bcdbc.jpg"
                    },
                    {
                      "id": 30336,
                      "image": "https://media.rawg.io/media/screenshots/1ac/1ac19f31974314855ad7be266adeb500.jpg"
                    }
                  ],
                  "publishers": null
                },
                {
                  "id": 4200,
                  "name": "Portal 2",
                  "background_image": "https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg",
                  "released": "2011-04-18",
                  "description": null,
                  "rating": "4.61",
                  "playtime": "11",
                  "website": null,
                  "short_screenshots": [
                    {
                      "id": -1,
                      "image": "https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg"
                    },
                    {
                      "id": 99018,
                      "image": "https://media.rawg.io/media/screenshots/221/221a03c11e5ff9f765d62f60d4b4cbf5.jpg"
                    }
                  ],
                  "publishers": null
                },
                {
                  "id": 5286,
                  "name": "Tomb Raider (2013)",
                  "background_image": "https://media.rawg.io/media/games/021/021c4e21a1824d2526f925eff6324653.jpg",
                  "released": "2013-03-05",
                  "description": null,
                  "rating": "4.05",
                  "playtime": "10",
                  "website": null,
                  "short_screenshots": [
                    {
                      "id": -1,
                      "image": "https://media.rawg.io/media/games/021/021c4e21a1824d2526f925eff6324653.jpg"
                    },
                    {
                      "id": 99160,
                      "image": "https://media.rawg.io/media/screenshots/4f9/4f9d5efdecfb63cb99f1baa4c0ceb3bf.jpg"
                    }
                  ],
                  "publishers": null
                }
              ]
            }
        """.trimIndent()

        mockWebServer.enqueue(MockResponse().setBody(jsonResponse))

        val result = networkDataSource.listGames()

        result.collect { response ->
            when (response) {
                is ApiResponse.Success -> {
                    val dummyListGames = DataMapper.list(DummyData.listGames, DataMapper::gamesResponse)

                    assertEquals(dummyListGames, response.data)
                }

                is ApiResponse.Error -> {}
            }
        }
    }

    @Test
    fun `Test gameDetail method with real API data`() = runBlocking {
            // Given: Enqueue a mock response with actual API data
            val id = DummyData.game?.id!!
            val jsonResponse = """
            {
              "id": 3328,
              "name": "The Witcher 3: Wild Hunt",
              "background_image": "https://media.rawg.io/media/games/618/618c2031a07bbff6b4f611f10b6bcdbc.jpg",
              "released": "2015-05-18",
              "description": null,
              "rating": "4.66",
              "playtime": "45",
              "website": null,
              "short_screenshots": [
                {
                  "id": -1,
                  "image": "https://media.rawg.io/media/games/618/618c2031a07bbff6b4f611f10b6bcdbc.jpg"
                },
                {
                  "id": 30336,
                  "image": "https://media.rawg.io/media/screenshots/1ac/1ac19f31974314855ad7be266adeb500.jpg"
                }
              ],
              "publishers": null
            }
        """.trimIndent()

            mockWebServer.enqueue(MockResponse().setBody(jsonResponse))

            val result = networkDataSource.gameDetail(id)

            result.collect { response ->
                when (response) {
                    is ApiResponse.Success -> {
                        val dummyGames = DataMapper.gamesResponse(DummyData.game!!)

                        assertEquals(dummyGames, response.data)
                    }

                    is ApiResponse.Error -> {}
                }
            }
        }

    @Test
    fun `Test gameScreenshots method with real API data`() = runBlocking {
        // Given: Enqueue a mock response with actual API data
        val id = DummyData.game?.id!!
        val jsonResponse = """
            {
                "count": "",
                "next": "",
                "previous": "",
                "results": [
                    {
                        "id": 1827221,
                        "image": "https://media.rawg.io/media/screenshots/a7c/a7c43871a54bed6573a6a429451564ef.jpg"
                    },
                    {
                        "id": 1827222,
                        "image": "https://media.rawg.io/media/screenshots/cf4/cf4367daf6a1e33684bf19adb02d16d6.jpg"
                    },
                    {
                        "id": 1827223,
                        "image": "https://media.rawg.io/media/screenshots/f95/f9518b1d99210c0cae21fc09e95b4e31.jpg"
                    }
                ]
            }
        """.trimIndent()

        mockWebServer.enqueue(MockResponse().setBody(jsonResponse))

        val result = networkDataSource.gameScreenshots(id)

        result.collect { response ->
            when (response) {
                is ApiResponse.Success -> {
                    val dummyListScreenshots = DataMapper.list(DummyData.screenshots, DataMapper::screenshot)

                    assertEquals(dummyListScreenshots, response.data)
                }

                is ApiResponse.Error -> {}
            }
        }
    }
}