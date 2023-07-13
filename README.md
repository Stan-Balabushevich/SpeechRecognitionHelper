# SpeechRecognitionHelper
SpeechRecognitionHelper class for implementing speech recognition functionality in an Android application.

The provided class, `SpeechRecognitionHelper`, is used for implementing speech recognition functionality in an Android application. It simplifies the process of setting up and managing the SpeechRecognizer object provided by the Android Speech Recognition API.

Here's an overview of the functionality provided by the `SpeechRecognitionHelper` class:

1. Initialization: The class initializes a SpeechRecognizer object and creates an Intent for speech recognition using the RecognizerIntent.ACTION_RECOGNIZE_SPEECH action. It specifies the language model as RecognizerIntent.LANGUAGE_MODEL_FREE_FORM and sets the desired language for recognition.("en-US" for example)

2. Setting up Speech Recognition: The `setupSpeechRecognizer` function allows you to set up the speech recognition process by providing callbacks for different recognition events. The available callbacks include:
   - `onResults`: Invoked when speech recognition results are available. It provides the recognized speech text as a parameter.
   - `onReadyForSpeech`: Invoked when the SpeechRecognizer is ready to receive speech input.
   - `onError`: Invoked when an error occurs during the recognition process. It provides an error code as a parameter.

3. Starting Speech Recognition: The `startListening` function starts the speech recognition process by calling `speechRecognizer.startListening` if speech recognition is available on the device.

4. Stopping Speech Recognition: The `stopListening` function stops the speech recognition process by calling `speechRecognizer.stopListening` if speech recognition is available on the device.

5. Destroying the SpeechRecognizer: The `destroy` function releases the resources used by the SpeechRecognizer by calling `speechRecognizer.destroy` if speech recognition is available on the device.

Overall, the `SpeechRecognitionHelper` class encapsulates the setup and management of the SpeechRecognizer object and provides functions to start, stop, and destroy the speech recognition process. It simplifies the implementation of speech recognition functionality in an Android application, allowing you to handle recognition results and errors through the provided callbacks.
