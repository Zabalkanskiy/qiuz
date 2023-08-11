package com.example.qiuz

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.BuildConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import java.util.Locale

class MainActivity : AppCompatActivity() {
    lateinit var questionText: TextView
    lateinit var firstButton: Button
    lateinit var secondButton: Button
    lateinit var thirdButton: Button
    lateinit var fourButton: Button
    lateinit var nextButton: ImageButton
    lateinit var previousButton: ImageButton

    private val mQuestionBank: List<Question> = listOf<Question>(
        Question(text = "What Is The Nickname Of Manchester United?", firstButton = "The Red Lions", secondButton = "The Gunners", thirdButton = "The Old Lady", fourButton = "The Red Devils", answer = "The Red Devils"),
        Question(text = "Which Country Has Won The World Cup The Most Times?", firstButton = "Spain", secondButton = "Germany", thirdButton = "Italy", fourButton = "Brazil", answer = "Brazil"),
        Question(text = "What Is The German Soccer League Called?", firstButton = "The Bundesliga", secondButton = "The Blitzliga", thirdButton = "The Volksliga", fourButton = "The Reichsliga", answer = "The Bundesliga"),
        Question(text = "Which former Barcelona Player Is Famous For Biting Other Players?", firstButton = "Didier Drogba", secondButton = "David Luiz", thirdButton = "Luis Suarez", fourButton = "Lionel Messi", answer = "Luis Suarez"),
        Question(text = "Which football club does David Beckham play for last?", firstButton = "Manchester United", secondButton = "Liverpool", thirdButton = "Real Madrid", fourButton = "PSG", answer = "PSG"),

    )
    private var currentIndex: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val remoteConfig = FirebaseRemoteConfig.getInstance()
        val result = remoteConfig.getString("qiuzRemoteString")
        val remote = remoteConfig.getString("remote")
        Log.d("REMOTE", remote)
        Log.d("REMOTECONFIG ", result)
        setContentView(R.layout.activity_quiz)
        questionText = findViewById(R.id.question_text_view)
        firstButton = findViewById(R.id.first_button)

        firstButton.setOnClickListener{view ->
            checkAnswer(mQuestionBank[currentIndex].firstButton)
        }
        secondButton= findViewById(R.id.second_button)

        secondButton.setOnClickListener{view ->
            checkAnswer(mQuestionBank[currentIndex].secondButton)
        }
        thirdButton = findViewById(R.id.third_button)

        thirdButton.setOnClickListener{view ->
            checkAnswer(mQuestionBank[currentIndex].thirdButton)
        }
        fourButton = findViewById(R.id.four_button)


        fourButton.setOnClickListener{view ->
            checkAnswer(mQuestionBank[currentIndex].fourButton)
        }
        nextButton = findViewById(R.id.next_button)
        nextButton.setOnClickListener{view ->

            currentIndex = (currentIndex + 1) % mQuestionBank.size
            updateQuestion()
        }
         previousButton = findViewById(R.id.previous_button)
        previousButton.setOnClickListener{
            if(currentIndex > 0) {
                currentIndex = (currentIndex + 1) % mQuestionBank.size
            }else {
                currentIndex = mQuestionBank.size -1
            }
            updateQuestion()
        }
        updateQuestion()



    }
    private fun updateQuestion(){
        val textString = mQuestionBank[currentIndex].text
        questionText.text = textString

        firstButton.text = mQuestionBank[currentIndex].firstButton
        secondButton.text = mQuestionBank[currentIndex].secondButton
        thirdButton.text = mQuestionBank[currentIndex].thirdButton
        fourButton.text = mQuestionBank[currentIndex].fourButton
    }

    private fun checkAnswer(userString: String){
       val rightAnswer: String = mQuestionBank[currentIndex].answer
        var message =""
        if(userString == rightAnswer){
            message = "This answer is correct"
        } else{
            message = "This answer is incorrect"
        }
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
    private fun checkIsEmu(): Boolean {
        if (BuildConfig.DEBUG) return false

        val phoneModel = Build.MODEL
        val buildProduct = Build.PRODUCT
        val buildHardware = Build.HARDWARE
        val brand = Build.BRAND

        var result = (Build.FINGERPRINT.startsWith("generic")
                || phoneModel.contains("google_sdk")
                || phoneModel.lowercase(Locale.getDefault()).contains("droid4x")
                || phoneModel.contains("Emulator")
                || phoneModel.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion")
                || buildHardware == "goldfish"
                || Build.BRAND.contains("google")
                || buildHardware == "vbox86"
                || buildProduct == "sdk"
                || buildProduct == "google_sdk"
                || buildProduct == "sdk_x86"
                || buildProduct == "vbox86p"
                || Build.BOARD.lowercase(Locale.getDefault()).contains("nox")
                || Build.BOOTLOADER.lowercase(Locale.getDefault()).contains("nox")
                || buildHardware.lowercase(Locale.getDefault()).contains("nox")
                || buildProduct.lowercase(Locale.getDefault()).contains("nox"))


        if (result) return true
        result = result or (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic"))
        if (result) return true
        result = result or ("google_sdk" == buildProduct)
        return result
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }
}