package nhk

import nhk.task.NewsTask
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.boot.CommandLineRunner
import kotlin.system.exitProcess

@EnableScheduling
@SpringBootApplication
@EntityScan(basePackages = ["io.github.io.github.nhk_news_web_easy"])
class Application(
    private val newsTask: NewsTask
) : CommandLineRunner {
    override fun run(vararg args: String?) {
        newsTask.saveTopNews()
        exitProcess(0)
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(Application::class.java, *args)
}
