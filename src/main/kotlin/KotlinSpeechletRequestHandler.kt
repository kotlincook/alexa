package de.kotlincook
import com.amazon.speech.speechlet.lambda.SpeechletRequestStreamHandler
import java.io.*
import com.fasterxml.jackson.module.kotlin.*

data class HandlerInput(val who: String)
data class HandlerOutput(val message: String)

class KotlinSpeechletRequestHandler : SpeechletRequestStreamHandler(KotlinSpeechlet(), supportedApplicationIds) {

    companion object {
        private val supportedApplicationIds = HashSet<String>()
        init {
            // Fill here the skill id of your generated Skill
            supportedApplicationIds.add("amzn1.ask.skill.50b9f4ef-1c02-4e72-bd4c-1bbdf1df61f5")
        }
    }

    val mapper = jacksonObjectMapper()

    fun handler(input: InputStream, output: OutputStream): Unit {
        val inputObj = mapper.readValue<HandlerInput>(input)
        mapper.writeValue(output, HandlerOutput("Hello ${inputObj.who}"))
    }
}