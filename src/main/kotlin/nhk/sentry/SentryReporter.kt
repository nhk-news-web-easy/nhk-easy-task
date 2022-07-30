package nhk.sentry

import io.sentry.Sentry
import nhk.config.SentryConfig
import org.apache.commons.lang3.StringUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class SentryReporter {
    private val logger: Logger = LoggerFactory.getLogger(SentryReporter::class.java)

    @Autowired
    private lateinit var sentryConfig: SentryConfig

    @Volatile
    private var initialized = false

    @PostConstruct
    fun postConstruct() {
        if (StringUtils.isBlank(sentryConfig.dsn)) {
            return
        }

        try {
            Sentry.init { options ->
                options.dsn = sentryConfig.dsn
            }

            this.initialized = true
        } catch (e: Exception) {
            logger.error("Failed to init sentry with dsn {}", sentryConfig.dsn, e)
        }
    }

    fun captureException(e: Throwable) {
        if (!this.initialized) {
            return
        }

        Sentry.captureException(e)
    }
}