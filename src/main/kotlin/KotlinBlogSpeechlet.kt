package de.kotlincook
import com.amazon.speech.speechlet.*
import com.amazon.speech.ui.PlainTextOutputSpeech
import com.amazon.speech.ui.SimpleCard

class KotlinBlogSpeechlet : Speechlet {

    override fun onSessionStarted(request: SessionStartedRequest?, session: Session?) {
    }

    override fun onSessionEnded(request: SessionEndedRequest?, session: Session?) {
    }

    override fun onIntent(request: IntentRequest, session: Session): SpeechletResponse {
        // DEPENDENCY: "KotlinBlogIntent" must be entered in Developer Console
        if ("KotlinBlogIntent" == request.intent.name) {
            return kotlinBlogResponse()
        }
        throw SpeechletException("Invalid Intent")
    }

    override fun onLaunch(request: LaunchRequest?, session: Session?): SpeechletResponse {
        return kotlinBlogResponse()
    }

    private fun kotlinBlogResponse(): SpeechletResponse {
        var blogText = NewsProvider.loadBlogItem()
        val speechText = "Here are the news about Kotlin: $blogText"

        val card = SimpleCard().apply {
            title = "KotlinBlog"
            content = speechText
        }
        val speech = PlainTextOutputSpeech().apply {
            text = speechText
        }
        return SpeechletResponse.newTellResponse(speech, card)
    }

}