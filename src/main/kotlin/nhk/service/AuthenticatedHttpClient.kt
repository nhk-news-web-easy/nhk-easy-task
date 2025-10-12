package nhk.service

import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import org.springframework.stereotype.Service

@Service
class AuthenticatedHttpClient {
    private val cookieStore = mutableMapOf<String, MutableList<Cookie>>()

    private val cookieJar = object : CookieJar {
        override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
            val host = url.host
            cookieStore.getOrPut(host) { mutableListOf() }.addAll(cookies)
        }

        override fun loadForRequest(url: HttpUrl): List<Cookie> {
            val host = url.host

            return cookieStore[host]?.filter { it.expiresAt > System.currentTimeMillis() } ?: emptyList()
        }
    }

    private var isAuthenticated = false

    fun getClient(): OkHttpClient {
        if (!isAuthenticated) {
            performOAuthFlow()
            isAuthenticated = true
        }

        return OkHttpClient.Builder()
                .cookieJar(cookieJar)
                .build()
    }

    private fun performOAuthFlow() {
        val client = OkHttpClient.Builder()
                .cookieJar(cookieJar)
                .followRedirects(true)
                .followSslRedirects(true)
                .build()

        // Simulate clicking "I understand" button - this triggers the OAuth flow
        // The "abroad" profile with these parameters auto-consents
        val authUrl = "https://news.web.nhk/tix/build_authorize?" +
                "idp=a-alaz&" +
                "profileType=abroad&" +
                "redirect_uri=https%3A%2F%2Fnews.web.nhk%2Fnews%2Feasy%2F&" +
                "entity=none&" +
                "area=130&" +
                "pref=13&" +
                "jisx0402=13101&" +
                "postal=1000001"
        val request = Request.Builder()
                .url(authUrl)
                .header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.15; rv:143.0) Gecko/20100101 Firefox/143.0")
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                .header("Accept-Language", "en-US,en;q=0.5")
                .header("Referer", "https://news.web.nhk/news/easy/")
                .build()
        val response = client.newCall(request).execute()
        response.close()
    }
}
