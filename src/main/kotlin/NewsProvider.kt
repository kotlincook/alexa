import com.github.kittinunf.fuel.httpGet
import de.kotlincook.stringutils.extract

object NewsProvider {

    fun loadBlogItem(i: Int = 0): String {
        val responseString = "https://blog.jetbrains.com/kotlin/".httpGet().responseString()
        val blogsItems = extractNewsFromSource(responseString.third.get())
        return blogsItems[i]
    }

    internal fun extractNewsFromSource(source: String): List<String> {
        val extract = source.extract("<div class=\"entry-content\">%{news}</div><!-- .entry-content -->", "%{", "}")
        return extract.map { it["news"]
                ?.replace(Regex("<.+?>"), " ") // removing all tags
                ?.replace(Regex("&.+?;"), " ") // removing all special html chars
                ?.replace(Regex("\\s+"), " ")?: "no content available" }
                .map { it.trim() }
                .map { it.removeSuffix("Continue reading") }
                .map { it.trim() }
    }
}