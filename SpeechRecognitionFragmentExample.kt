
import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.os.SystemClock
import android.speech.SpeechRecognizer
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Chronometer


class SpeechRecognitionFragmentExample : Fragment() {

    private lateinit var chronometer: Chronometer
    private lateinit var permissionHandler: PermissionsHandler
    private val speechRecognitionHelper by lazy { SpeechRecognitionHelper(requireContext(),language) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    override fun onResume() {
        super.onResume()
        setUpPermissionsHandler()
        permissionHandler.requestPermission(Manifest.permission.RECORD_AUDIO)
    }

    override fun onDestroy() {
        super.onDestroy()
        speechRecognitionHelper.destroy()
    }


    @SuppressLint("ClickableViewAccessibility")
    private fun setupSpeechRecognition(){

        speechRecognitionHelper.setupSpeechRecognizer(
            onReadyForSpeech = {
                // This block of code will be executed when the speech recognizer is ready for speech
                chronometer.base = SystemClock.elapsedRealtime()
                chronometer.start()
                binding.chronometer.visibility = View.VISIBLE
            },
            onResults = { text ->
                // update your EditText here
                binding.etSend.setText(text)
                chronometer.stop()
                binding.chronometer.visibility = View.GONE
            },
            onError = { error ->
                val errorMessage = when (error) {
                    SpeechRecognizer.ERROR_AUDIO -> "Audio recording error"
                    SpeechRecognizer.ERROR_CLIENT -> "Client side error"
                    SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS -> "Insufficient permissions"
                    SpeechRecognizer.ERROR_NETWORK -> "Network error"
                    SpeechRecognizer.ERROR_NETWORK_TIMEOUT -> "Network timeout"
                    SpeechRecognizer.ERROR_NO_MATCH -> "No match"
                    SpeechRecognizer.ERROR_RECOGNIZER_BUSY -> "RecognitionService busy"
                    SpeechRecognizer.ERROR_SERVER -> "Server error"
                    SpeechRecognizer.ERROR_SPEECH_TIMEOUT -> "No speech input"
                    else -> "Unknown error"
                }
                Log.e("SpeechRecognizer", "Error: $errorMessage")
            }
        )

        binding.btnMicrophone.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    speechRecognitionHelper.startListening()
                    true
                }
                MotionEvent.ACTION_UP -> {
                    speechRecognitionHelper.stopListening()
                    v.performClick()
                    true
                }
                else -> false
            }
        }
    }

    // Need to create PermissionsHandler class and ask for permission first
    private fun setUpPermissionsHandler() {
        permissionHandler = PermissionsHandler(requireContext())
        permissionHandler.setPermissionCallbacks(
            onGranted = { permission ->
                if (permission == Manifest.permission.RECORD_AUDIO) {
                    setupSpeechRecognition()
                }
            },
            onDenied = { permission ->
                if (permission == Manifest.permission.RECORD_AUDIO) {
                    permissionHandler.showPermissionChangeDialog(requireContext(), "Audio Record")
                }
            }
        )
    }
}
