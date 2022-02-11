package nhk

import nhk.service.NewsFetcher
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class NewsFetcherTest : BaseTest() {
    @Autowired
    private lateinit var newsFetcher: NewsFetcher

    @Test
    fun shouldGetTopNews() {
        val topNews = newsFetcher.getTopNews()

        Assertions.assertTrue(topNews.isNotEmpty())
    }
}