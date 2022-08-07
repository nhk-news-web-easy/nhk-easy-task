package nhk

import nhk.repository.NewsRepository
import nhk.task.NewsDailyTask
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class NewsDailyTaskTest : BaseTest() {
    @Autowired
    private lateinit var newsDailyTask: NewsDailyTask

    @Autowired
    private lateinit var newsRepository: NewsRepository

    @Test
    fun shouldGetDailyNews() {
        newsDailyTask.saveTopNews()

        val allNews = newsRepository.findAll()

        Assertions.assertTrue(allNews.isNotEmpty())
    }
}