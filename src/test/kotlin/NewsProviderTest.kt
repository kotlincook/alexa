import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test


internal class NewsProviderTest {

    @Test
    fun extractNewsFromSource() {
        val sourceCode = FileUtils.loadResource("/source.txt")
        val blogs = NewsProvider.extractNewsFromSource(sourceCode)
        assertEquals(10, blogs.size)
        assertTrue(blogs[0].startsWith("We’re happy to announce"))
        assertTrue(blogs[0].endsWith("(Beta)"))
        assertTrue(blogs[9].startsWith("Members of our community"))
        assertTrue(blogs[9].endsWith("serialization library."))
    }
}