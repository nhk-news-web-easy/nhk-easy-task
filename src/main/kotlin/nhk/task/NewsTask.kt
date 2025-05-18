package nhk.task

import io.github.io.github.nhk_news_web_easy.News
import nhk.dto.TopNewsDto
import nhk.sentry.SentryReporter
import nhk.service.NewsFetcher
import nhk.service.NewsParser
import nhk.service.NewsService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.time.ZonedDateTime

@Component
class NewsTask {
    private val logger: Logger = LoggerFactory.getLogger(NewsTask::class.java)

    @Autowired
    private lateinit var newsFetcher: NewsFetcher

    @Autowired
    private lateinit var newsParser: NewsParser

    @Autowired
    private lateinit var newsService: NewsService

    @Autowired
    private lateinit var sentryReporter: SentryReporter

    fun saveTopNews() {
        logger.info("Start to fetch news, now={}", ZonedDateTime.now())

        var topNews = emptyList<TopNewsDto>()

        try {
            topNews = newsFetcher.getTopNews()
        } catch (e: Throwable) {
            logger.error("Failed to get top news", e)

            sentryReporter.captureException(e)
        }

        if (topNews.isEmpty()) {
            logger.error("Top news are empty")

            sentryReporter.captureException(Exception("topNews are empty"))

            return
        }

        var parsedNews = emptyList<News>()

        try {
            parsedNews = topNews.mapNotNull { news ->
                newsParser.parseNews(news)
            }
        } catch (e: Throwable) {
            logger.error("Failed to parse top news", e)

            sentryReporter.captureException(e)
        }

        if (parsedNews.isEmpty()) {
            logger.error("Parsed news are empty")

            sentryReporter.captureException(Exception("parsedNews are empty"))

            return
        }

        try {
            newsService.saveAll(parsedNews)
        } catch (e: Throwable) {
            logger.error("Failed to save parsed news", e)

            sentryReporter.captureException(e)
        }
    }
}
