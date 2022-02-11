package nhk

import nhk.repository.NewsRepository
import nhk.repository.WordDefinitionRepository
import nhk.repository.WordRepository
import nhk.service.NewsFetcher
import nhk.service.NewsParser
import nhk.service.NewsService
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class NewsServiceTest : BaseTest() {
    @Autowired
    private lateinit var newsFetcher: NewsFetcher

    @Autowired
    private lateinit var newsParser: NewsParser

    @Autowired
    private lateinit var newsService: NewsService

    @Autowired
    private lateinit var newsRepository: NewsRepository

    @Autowired
    private lateinit var wordRepository: WordRepository

    @Autowired
    private lateinit var wordDefinitionRepository: WordDefinitionRepository

    @Test
    fun shouldSaveTopNews() {
        val topNews = newsFetcher.getTopNews()
        val parsedNews = topNews.mapNotNull { news ->
            newsParser.parseNews(news)
        }

        newsService.saveAll(parsedNews)

        val allNews = newsRepository.findAll()
        val allWords = wordRepository.findAll()
        val allWordDefinitions = wordDefinitionRepository.findAll()

        assertTrue(allNews.isNotEmpty())
        assertTrue(allWords.isNotEmpty())
        assertTrue(allWordDefinitions.isNotEmpty())
    }
}
