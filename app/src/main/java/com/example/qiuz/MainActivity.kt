package com.example.qiuz

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.webkit.CookieManager
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.BuildConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import java.util.Locale


class MainActivity : AppCompatActivity() {
     var webView: WebView? = null


    lateinit var questionText: TextView
    lateinit var firstButton: Button
    lateinit var secondButton: Button
    lateinit var thirdButton: Button
    lateinit var fourButton: Button
    lateinit var nextButton: ImageButton
    lateinit var linearLayout: LinearLayout
    lateinit var imageView: ImageView

    private val mQuestionBank: List<Question> = listOf<Question>(
        Question(text = "What Is The Nickname Of Manchester United?", firstButton = "The Red Lions", secondButton = "The Gunners", thirdButton = "The Old Lady", fourButton = "The Red Devils", answer = "The Red Devils", resBackground = R.drawable.tg_image_1),
        Question(text = "Which Country Has Won The World Cup The Most Times?", firstButton = "Spain", secondButton = "Germany", thirdButton = "Italy", fourButton = "Brazil", answer = "Brazil",resBackground = R.drawable.tg_image_2),
        Question(text = "What Is The German Soccer League Called?", firstButton = "The Bundesliga", secondButton = "The Blitzliga", thirdButton = "The Volksliga", fourButton = "The Reichsliga", answer = "The Bundesliga", resBackground = R.drawable.tg_image_3),
        Question(text = "Which former Barcelona Player Is Famous For Biting Other Players?", firstButton = "Didier Drogba", secondButton = "David Luiz", thirdButton = "Luis Suarez", fourButton = "Lionel Messi", answer = "Luis Suarez", resBackground = R.drawable.tg_image_4),
        Question(text = "Which football club does David Beckham play for last?", firstButton = "Manchester United", secondButton = "Liverpool", thirdButton = "Real Madrid", fourButton = "PSG", answer = "PSG", resBackground = R.drawable.tg_image_5),

    )
    private var currentIndex: Int = 0
    private var resultGame:Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val remoteString: String = loadRemoteString(context = this)
        if(remoteString =="") {
            val remoteConfig = FirebaseRemoteConfig.getInstance()
            val config: FirebaseRemoteConfigSettings = FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(3600).build()
            remoteConfig.setConfigSettingsAsync(config)
            remoteConfig.fetchAndActivate().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val result = remoteConfig.getString("qiuzRemoteString")
                   // saveRemoteString(this, result)
                   // val getString =  loadRemoteString(this)
                    if(result == "" || checkIsEmu()){
                        // открываем заглушку
                        setContentView(R.layout.activity_quiz)
                        imageView = findViewById(R.id.actyvityImageView)
                        linearLayout = findViewById(R.id.linear_activity)
                        questionText = findViewById(R.id.question_text_view)
                        firstButton = findViewById(R.id.first_button)

                        firstButton.setOnClickListener{view ->
                            val checkResult =  checkAnswer(mQuestionBank[currentIndex].firstButton)
                            if(checkResult){
                                view.setBackgroundColor(Color.GREEN)
                                resultGame +=1
                            }else{
                                view.setBackgroundColor(Color.RED)
                            }
                            Handler(Looper.getMainLooper()).postDelayed({

                                currentIndex = currentIndex + 1
                                if(currentIndex> (mQuestionBank.size -1)){
                                    val intent = Intent(this, ResultActivity::class.java)
                                    intent.putExtra("RESULT", resultGame)
                                    startActivity(intent)
                                } else{
                                    updateQuestion()
                                }


                            }, 2000)
                        }
                        secondButton= findViewById(R.id.second_button)

                        secondButton.setOnClickListener{view ->
                            val checkResult =  checkAnswer(mQuestionBank[currentIndex].secondButton)
                            if(checkResult){
                                view.setBackgroundColor(Color.GREEN)
                                resultGame +=1
                            }else{
                                view.setBackgroundColor(Color.RED)
                            }
                            Handler(Looper.getMainLooper()).postDelayed({

                                currentIndex = currentIndex + 1
                                if(currentIndex> (mQuestionBank.size -1)){
                                    val intent = Intent(this, ResultActivity::class.java)
                                    intent.putExtra("RESULT", resultGame)
                                    startActivity(intent)
                                } else{

                                    updateQuestion()
                                }


                            }, 2000)
                        }
                        thirdButton = findViewById(R.id.third_button)

                        thirdButton.setOnClickListener{view ->
                            val checkResult =  checkAnswer(mQuestionBank[currentIndex].thirdButton)
                            if(checkResult){
                                view.setBackgroundColor(Color.GREEN)
                                resultGame +=1
                            }else{
                                view.setBackgroundColor(Color.RED)
                            }
                            Handler(Looper.getMainLooper()).postDelayed({

                                currentIndex = currentIndex + 1
                                if(currentIndex> (mQuestionBank.size -1)){
                                    val intent = Intent(this, ResultActivity::class.java)
                                    intent.putExtra("RESULT", resultGame)
                                    startActivity(intent)
                                } else{
                                    updateQuestion()
                                }


                            }, 2000)
                        }
                        fourButton = findViewById(R.id.four_button)


                        fourButton.setOnClickListener{view ->
                            val checkResult =  checkAnswer(mQuestionBank[currentIndex].fourButton)
                            if(checkResult){
                                view.setBackgroundColor(Color.GREEN)
                                resultGame +=1
                            }else{
                                view.setBackgroundColor(Color.RED)
                            }
                            Handler(Looper.getMainLooper()).postDelayed({

                                    currentIndex = currentIndex + 1
                                if(currentIndex> (mQuestionBank.size -1)){
                                    val intent = Intent(this, ResultActivity::class.java)
                                    intent.putExtra("RESULT", resultGame)
                                    startActivity(intent)
                                } else {

                                    updateQuestion()
                                }


                            }, 2000)
                        }
                        nextButton = findViewById(R.id.next_button)
                        nextButton.setOnClickListener{view ->

                            currentIndex = currentIndex + 1

                            if(currentIndex> (mQuestionBank.size -1)){
                                val intent = Intent(this, ResultActivity::class.java)
                                intent.putExtra("RESULT", resultGame)
                                startActivity(intent)
                            }else{

                                updateQuestion()
                            }

                        }
                        if(currentIndex< mQuestionBank.size) {
                            updateQuestion()
                        }

                    }else{
                        val remoteString = remoteConfig.getString("qiuzRemoteString")
                        saveRemoteString(this, remoteString)

                        setContentView(R.layout.web_view_activity)
                        webView = findViewById(R.id.webView) as WebView
                        webView!!.webViewClient= WebViewClient()
                       // webView!!.webChromeClient = ChromeClient()
                        var webSettings = webView?.settings
                        webSettings?.javaScriptEnabled = true
                        webSettings?.loadWithOverviewMode =true
                        webSettings?.useWideViewPort =true
                        webSettings?.domStorageEnabled =true
                        webSettings?.databaseEnabled = true
                        webSettings?.setSupportZoom(false)
                        webSettings?.allowFileAccess = true
                        webSettings?.allowContentAccess = true
                        webSettings?.loadWithOverviewMode =true
                        webSettings?.useWideViewPort =true


                        webSettings?.javaScriptCanOpenWindowsAutomatically =true

                        if( savedInstanceState != null){
                            webView?.restoreState(savedInstanceState)
                        } else webView?.loadUrl(remoteString)

                        val cookieManager = CookieManager.getInstance()
                        cookieManager.setAcceptCookie(true)


                    }
                   // val remote = remoteConfig.getString("remote")
                    //Log.d("REMOTE", remote)
                   // Log.d("REMOTECONFIG ", result)
                }
            }
        }else {

           if( isOnline()){
                setContentView(R.layout.web_view_activity)
                webView = findViewById(R.id.webView)
               webView?.webViewClient= WebViewClient()
               var webSettings = webView?.settings
               webSettings?.javaScriptEnabled = true
               webSettings?.loadWithOverviewMode =true
               webSettings?.useWideViewPort =true
               webSettings?.domStorageEnabled =true
               webSettings?.databaseEnabled = true
               webSettings?.setSupportZoom(false)
               webSettings?.allowFileAccess = true
               webSettings?.allowContentAccess = true
               webSettings?.loadWithOverviewMode =true
               webSettings?.useWideViewPort =true


               webSettings?.javaScriptCanOpenWindowsAutomatically =true

               if( savedInstanceState != null){
                   webView?.restoreState(savedInstanceState)
               } else webView?.loadUrl(remoteString)

               val cookieManager = CookieManager.getInstance()
               cookieManager.setAcceptCookie(true)













           }else{
               setContentView(R.layout.no_internet_activity)

           }

        }




    }
    private fun updateQuestion(){
        val textString = mQuestionBank[currentIndex].text
        if (currentIndex == 3){
            questionText.setTextColor(Color.BLACK)
        }else{
            questionText.setTextColor(Color.WHITE)
        }

        questionText.text = textString

        firstButton.text = mQuestionBank[currentIndex].firstButton
        firstButton.setBackgroundColor(Color.WHITE)
        secondButton.text = mQuestionBank[currentIndex].secondButton
        secondButton.setBackgroundColor(Color.WHITE)
        thirdButton.text = mQuestionBank[currentIndex].thirdButton
        thirdButton.setBackgroundColor(Color.WHITE)
        fourButton.text = mQuestionBank[currentIndex].fourButton
        fourButton.setBackgroundColor(Color.WHITE)
        //linearLayout.setBackgroundResource(mQuestionBank[currentIndex].resBackground)
        imageView.setImageResource(mQuestionBank[currentIndex].resBackground)
    }

    private fun checkAnswer(userString: String): Boolean{
       val rightAnswer: String = mQuestionBank[currentIndex].answer
        var message =""
        var boolResult:Boolean = false
        if(userString == rightAnswer){
            message = "This answer is correct"
            boolResult = true
        } else{
            message = "This answer is incorrect"
            boolResult = false

        }
       // Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        return boolResult
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
        webView?.saveState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {

        super.onRestoreInstanceState(savedInstanceState)
        webView?.restoreState(savedInstanceState)
    }

    override fun onBackPressed() {
        if (webView!!.canGoBack()) {
            webView!!.goBack()
        }
    }
     fun isOnline(): Boolean {

         val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
             val nw      = connectivityManager.activeNetwork ?: return false
             val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
             return when {
                 actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                 actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                 //for other device how are able to connect with Ethernet
                 actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                 else -> false
             }
         } else {
             val nwInfo = connectivityManager.activeNetworkInfo ?: return false
             return nwInfo.isConnected
         }


     }
}
const val PREFS_NAME = "QIUZ"
const val REMOTE_STRING = "REMOTESTRING"
const val DEFAULT_STRING = ""
fun loadRemoteString(context: Context): String{
    val prefs = context.getSharedPreferences(PREFS_NAME, 0)
    val prefString = prefs.getString(REMOTE_STRING, DEFAULT_STRING)
    return  prefString ?: DEFAULT_STRING
}

fun saveRemoteString(context: Context, remoteString: String){
    val putstring = context.getSharedPreferences(PREFS_NAME, 0).edit().putString(REMOTE_STRING, remoteString).apply()
}