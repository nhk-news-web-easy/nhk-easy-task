package nhk.service

import nhk.entity.News
import nhk.entity.NewsWord
import nhk.repository.NewsRepository
import nhk.repository.NewsWordRepository
import nhk.repository.WordDefinitionRepository
import nhk.repository.WordRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class NewsService {
    private val logger: Logger = LoggerFactory.getLogger(NewsService::class.java)

    @Autowired
    private lateinit var newsRepository: NewsRepository

    @Autowired
    private lateinit var wordRepository: WordRepository

    @Autowired
    private lateinit var wordDefinitionRepository: WordDefinitionRepository

    @Autowired
    private lateinit var newsWordRepository: NewsWordRepository

    @Transactional
    fun saveAll(newsList: List<News>) {
        val latestNews = newsList.filter {
            newsRepository.findByTitle(it.title).isEmpty()
        }

        if (latestNews.isEmpty()) {
            return
        }

        newsRepository.saveAll(latestNews)

        val words = latestNews.flatMap { news ->
            news.words
        }.distinctBy { word ->
            word.name
        }.map { word ->
            val currentWords = wordRepository.findByName(word.name)

            if (currentWords.isEmpty()) {
                word
            } else {
                val currentWord = currentWords.first()
                currentWord.definitions = word.definitions

                currentWord
            }
        }
        val newWords = words.filter { word ->
            word.id == 0
        }

        wordRepository.saveAll(newWords)

        newWords.forEach { word ->
            val definitions = word.definitions
                .map { definition ->
                    definition.wordId = word.id

                    definition
                }

            wordDefinitionRepository.saveAll(definitions)
        }

        val wordNameIdMap = words.associateBy({ it.name }, { it.id })
        val newsWords = latestNews.flatMap { news ->
            news.words
                .map { word ->
                    val newsWord = NewsWord()
                    newsWord.newsId = news.id
                    newsWord.wordId = wordNameIdMap.getOrDefault(word.name, 0)
                    newsWord.idInNews = word.idInNews

                    newsWord
                }
        }

        newsWordRepository.saveAll(newsWords)
    }
}
