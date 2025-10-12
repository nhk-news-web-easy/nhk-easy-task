package nhk.service

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import nhk.Constants
import nhk.dto.TopNewsDto
import okhttp3.Request
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Service
class NewsFetcher {
    @Autowired
    private lateinit var httpClient: AuthenticatedHttpClient

    fun getTopNews(): List<TopNewsDto> {
        val client = httpClient.getClient()
        val request = Request.Builder()
                .url(Constants.TOP_NEWS_URL)
                .header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.15; rv:143.0) Gecko/20100101 Firefox/143.0")
                .build()
        val response = client.newCall(request).execute()

        if (response.code != 200) {
            throw RuntimeException("Failed to get top news, statusCode=${response.code}")
        }

        val json = response.body?.string()

        json?.let {
            val javaTimeModule = JavaTimeModule()
            val localDateTimeDeserializer =
                    LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(Constants.NHK_NEWS_EASY_DATE_FORMAT))
            javaTimeModule.addDeserializer(LocalDateTime::class.java, localDateTimeDeserializer)

            val objectMapper = ObjectMapper()
            objectMapper.propertyNamingStrategy = PropertyNamingStrategies.SNAKE_CASE
            objectMapper.registerModule(javaTimeModule)

            return objectMapper.readValue(it, object : TypeReference<List<TopNewsDto>>() {})
        }

        throw RuntimeException("top news are empty")
    }
}
