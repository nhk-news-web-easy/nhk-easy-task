package nhk.repository

import nhk.entity.News
import org.springframework.data.jpa.repository.JpaRepository

interface NewsRepository : JpaRepository<News, Int> {
    fun findFirstByTitle(title: String): News?
}
