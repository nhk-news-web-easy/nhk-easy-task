package nhk.repository

import nhk.entity.Word
import org.springframework.data.jpa.repository.JpaRepository

interface WordRepository : JpaRepository<Word, Int> {
    fun findFirstByName(name: String): Word?
}
