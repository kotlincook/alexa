import java.net.URL
import java.nio.charset.Charset

object FileUtils {

    fun loadResource(resourceName: String, charSet: Charset = Charsets.UTF_8): String {
        val resource: URL? = javaClass::class.java.getResource(resourceName)
        return resource?.readText(charSet) ?: throw IllegalArgumentException("Resource '${resourceName}' not found")
    }

}