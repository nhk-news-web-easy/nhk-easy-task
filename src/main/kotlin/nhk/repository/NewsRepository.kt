package nhk.repository

import nhk.entity.News
import org.springframework.data.jpa.repository.JpaRepository

interface NewsRepository : JpaRepository<News, Int> {
    fun findByTitle(title: String): List<News>
}
