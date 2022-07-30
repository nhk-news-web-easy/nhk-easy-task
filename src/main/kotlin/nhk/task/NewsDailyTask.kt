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
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.ZonedDateTime

@Component
class NewsDailyTask {
    private val logger: Logger = LoggerFactory.getLogger(NewsDailyTask::class.java)

    @Autowired
    private lateinit var newsFetcher: NewsFetcher

    @Autowired
    private lateinit var newsParser: NewsParser

    @Autowired
    private lateinit var newsService: NewsService

    @Autowired
    private lateinit var sentryReporter: SentryReporter

    @Scheduled(cron = "0 0 10 * * *", zone = "UTC")
    fun saveTopNews() {
        logger.info("Start to fetch news, now={}", ZonedDateTime.now())

        var topNews = emptyList<TopNewsDto>()

        try {
            topNews = newsFetcher.getTopNews()
        } catch (e: Throwable) {
            sentryReporter.captureException(e)
        }

        if (topNews.isEmpty()) {
            sentryReporter.captureException(Exception("topNews are empty"))

            return
        }

        var parsedNews = emptyList<News>()

        try {
            parsedNews = topNews.mapNotNull { news ->
                newsParser.parseNews(news)
            }
        } catch (e: Throwable) {
            sentryReporter.captureException(e)
        }

        if (parsedNews.isEmpty()) {
            sentryReporter.captureException(Exception("parsedNews are empty"))

            return
        }

        try {
            newsService.saveAll(parsedNews)
        } catch (e: Throwable) {
            sentryReporter.captureException(e)
        }
    }
}
