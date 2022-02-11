package nhk

import nhk.service.NewsFetcher
import nhk.service.NewsParser
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class NewsParserTest : BaseTest() {
    @Autowired
    private lateinit var newsFetcher: NewsFetcher

    @Autowired
    private lateinit var newsParser: NewsParser

    @Test
    fun shouldParseNewsAndWords() {
        val topNews = newsFetcher.getTopNews()
        val news = newsParser.parseNews(topNews[0])

        Assertions.assertNotNull(news)
        news?.words?.let { Assertions.assertTrue(it.isNotEmpty()) }
    }
}