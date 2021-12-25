package nhk

import nhk.repository.NewsRepository
import nhk.repository.WordDefinitionRepository
import nhk.repository.WordRepository
import nhk.service.NewsParser
import nhk.service.NewsService
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class NewsServiceTest : BaseTest() {
    @Autowired
    private lateinit var newsService: NewsService

    @Autowired
    private lateinit var newsRepository: NewsRepository

    @Autowired
    private lateinit var wordRepository: WordRepository

    @Autowired
    private lateinit var wordDefinitionRepository: WordDefinitionRepository

    @Autowired
    private lateinit var newsParser: NewsParser

    @Test
    fun shouldGetTopNews() {
        val topNews = newsService.getTopNews()

        assertTrue(topNews.isNotEmpty())
    }

    @Test
    fun shouldParseNewsAndWords() {
        val topNews = newsService.getTopNews()
        val news = newsParser.parseNews(topNews[0])

        assertNotNull(news)
        assertTrue(news.words.isNotEmpty())
    }

    @Test
    fun shouldSaveTopNews() {
        newsService.fetchAndSaveTopNews()

        val allNews = newsRepository.findAll()
        val allWords = wordRepository.findAll()
        val allWordDefinitions = wordDefinitionRepository.findAll()

        assertTrue(allNews.isNotEmpty())
        assertTrue(allWords.isNotEmpty())
        assertTrue(allWordDefinitions.isNotEmpty())
    }
}
