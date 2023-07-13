
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer

class SpeechRecognitionHelper(private val context: Context, language: String) {

    private val speechRecognizer: SpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(context)
    private val recognizerIntent: Intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
        putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        putExtra(RecognizerIntent.EXTRA_LANGUAGE, language)
    }

    fun setupSpeechRecognizer(
        onResults: (String) -> Unit,
        onReadyForSpeech: () -> Unit = {},
        onError: (Int) -> Unit = {}
    ) {

        speechRecognizer.setRecognitionListener(object : RecognitionListener {
            override fun onReadyForSpeech(params: Bundle) {
                onReadyForSpeech()
            }

            override fun onBeginningOfSpeech() {}

            override fun onRmsChanged(rmsdB: Float) {}

            override fun onBufferReceived(buffer: ByteArray) {}

            override fun onEndOfSpeech() {}

            override fun onError(error: Int) {
                onError(error)
            }

            override fun onResults(results: Bundle) {
                val matches: ArrayList<String>? = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                if (matches != null) {
                    val text = matches[0] // The best match
                    onResults(text)
                }
            }

            override fun onPartialResults(partialResults: Bundle) {}

            override fun onEvent(eventType: Int, params: Bundle) {}
        })
    }

    fun startListening() {
        if (SpeechRecognizer.isRecognitionAvailable(context)) {
            speechRecognizer.startListening(recognizerIntent)
        }
    }

    fun stopListening() {
        if (SpeechRecognizer.isRecognitionAvailable(context)) {
            speechRecognizer.stopListening()
        }
    }

    fun destroy() {
        if (SpeechRecognizer.isRecognitionAvailable(context)) {
            speechRecognizer.destroy()
        }
    }
}
