package de.kotlincook
import com.amazon.speech.speechlet.lambda.SpeechletRequestStreamHandler
import java.io.*
import com.fasterxml.jackson.module.kotlin.*

data class HandlerInput(val who: String)
data class HandlerOutput(val message: String)

// DEPENDENCY: class "de.kotlincook.KotlinSpeechletRequestHandler" must be set in the AWS configuration
class KotlinSpeechletRequestHandler : SpeechletRequestStreamHandler(KotlinBlogSpeechlet(), supportedApplicationIds) {

    companion object {
        private val supportedApplicationIds = HashSet<String>()
        init {
            // DEPENDENCY: Take this ID from the Skill Developer Console generated there
            supportedApplicationIds.add("amzn1.ask.skill.50b9f4ef-1c02-4e72-bd4c-1bbdf1df61f5")
        }
    }

    val mapper = jacksonObjectMapper()

    fun handler(input: InputStream, output: OutputStream) {
        val inputObj = mapper.readValue<HandlerInput>(input)
        mapper.writeValue(output, HandlerOutput("Hello ${inputObj.who}"))
    }
}