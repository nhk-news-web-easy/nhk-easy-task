package nhk.controller

import nhk.service.NewsFetcher
import nhk.service.NewsParser
import nhk.service.NewsService
import nhk.task.NewsTask
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import java.time.ZonedDateTime

@RestController
class TaskController {
    private val logger: Logger = LoggerFactory.getLogger(NewsTask::class.java)

    @Autowired
    private lateinit var newsFetcher: NewsFetcher

    @Autowired
    private lateinit var newsParser: NewsParser

    @Autowired
    private lateinit var newsService: NewsService

    @PostMapping("/fetchNews")
    fun fetchNews(): String {
        logger.info("Start to fetch news, now={}", ZonedDateTime.now())

        val topNews = newsFetcher.getTopNews()

        if (topNews.isNotEmpty()) {
            val parsedNews = topNews.mapNotNull { news ->
                newsParser.parseNews(news)
            }

            if (parsedNews.isNotEmpty()) {
                newsService.saveAll(parsedNews)
            }
        }

        return "ok"
    }
}
