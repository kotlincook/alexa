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
            // FIXME change this id
            supportedApplicationIds.add("amzn1.ask.skill.af183920-ecab-4dfd-849a-514aeabbea08")
        }
    }

    val mapper = jacksonObjectMapper()

    fun handler(input: InputStream, output: OutputStream): Unit {
        val inputObj = mapper.readValue<HandlerInput>(input)
        mapper.writeValue(output, HandlerOutput("Hello ${inputObj.who}"))
    }
}