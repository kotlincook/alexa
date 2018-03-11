package de.kotlincook
import com.amazon.speech.speechlet.*
import com.amazon.speech.ui.PlainTextOutputSpeech
import com.amazon.speech.ui.SimpleCard
import java.time.LocalDate
import java.time.temporal.WeekFields
import java.util.*

class KotlinSpeechlet : Speechlet {

    override fun onSessionStarted(request: SessionStartedRequest?, session: Session?) {
    }

    override fun onSessionEnded(request: SessionEndedRequest?, session: Session?) {
    }

    override fun onIntent(request: IntentRequest, session: Session): SpeechletResponse {

        // change this:
        if ("KotlinBlogIntent" == request.intent.name) {
            return binColourResponse()
        }
        else {
            throw SpeechletException("Invalid Intent")
        }
    }

    override fun onLaunch(request: LaunchRequest?, session: Session?): SpeechletResponse {
        return binColourResponse()
    }

    private fun binColourResponse(): SpeechletResponse {
        var kotlinOutput = "Simply text"
        val speechText = "Welcome to KotlinBlog. $kotlinOutput"
        val card = SimpleCard()
        card.title = "KotlinBlog"
        card.content = speechText
        val speech = PlainTextOutputSpeech()
        speech.text = speechText
        return SpeechletResponse.newTellResponse(speech, card)
    }

}