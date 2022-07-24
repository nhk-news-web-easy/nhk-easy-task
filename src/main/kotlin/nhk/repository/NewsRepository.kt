package nhk.repository

import io.github.io.github.nhk_news_web_easy.News
import org.springframework.data.jpa.repository.JpaRepository

interface NewsRepository : JpaRepository<News, Int> {
    fun findFirstByTitle(title: String): News?
}
