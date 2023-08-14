package com.example.qiuz

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.os.Parcelable
import android.provider.MediaStore
import android.util.Log
import android.webkit.CookieManager
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.BuildConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
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
    lateinit var linearImageView: ImageView


    private var mUploadMessage: ValueCallback<Uri?>? = null
    private var mCapturedImageURI: Uri? = null
    private var mFilePathCallback: ValueCallback<Array<Uri>>? = null
    private var mCameraPhotoPath: String? = null

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
                        linearImageView = findViewById(R.id.footbalImage)
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
                        webView!!.webChromeClient = ChromeClient()
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
               webView!!.webChromeClient = ChromeClient()
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

            questionText.setTextColor(Color.WHITE)


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
        imageView.setBackgroundColor(Color.parseColor("#7F7F7F"))
        //imageView.setImageResource(mQuestionBank[currentIndex].resBackground)
        linearImageView.setImageResource(mQuestionBank[currentIndex].resBackground)

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

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp =
            SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        val storageDir = Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_PICTURES
        )
        return File.createTempFile(
            imageFileName,  /* prefix */
            ".jpg",  /* suffix */
            storageDir /* directory */
        )
    }

    inner class ChromeClient : WebChromeClient() {
        // For Android 5.0
        override fun onShowFileChooser(
            view: WebView,
            filePath: ValueCallback<Array<Uri>>,
            fileChooserParams: FileChooserParams
        ): Boolean {
            // Double check that we don't have any existing callbacks
            if (mFilePathCallback != null) {
                mFilePathCallback!!.onReceiveValue(null)
            }
            mFilePathCallback = filePath
            var takePictureIntent: Intent? = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (takePictureIntent!!.resolveActivity(packageManager) != null) {
                // Create the File where the photo should go
                var photoFile: File? = null
                try {
                    photoFile = createImageFile()
                    takePictureIntent.putExtra("PhotoPath", mCameraPhotoPath)
                } catch (ex: IOException) {
                    // Error occurred while creating the File
                    Log.e("ErrorCreatingFile", "Unable to create Image File", ex)
                }

                // Continue only if the File was successfully created
                if (photoFile != null) {
                    mCameraPhotoPath = "file:" + photoFile.absolutePath
                    takePictureIntent.putExtra(
                        MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile)
                    )
                } else {
                    takePictureIntent = null
                }
            }
            val contentSelectionIntent = Intent(Intent.ACTION_GET_CONTENT)
            contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE)
            contentSelectionIntent.type = "image/*"
            val intentArray: Array<Intent?>
            intentArray = takePictureIntent?.let { arrayOf(it) } ?: arrayOfNulls(0)
            val chooserIntent = Intent(Intent.ACTION_CHOOSER)
            chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent)
            chooserIntent.putExtra(Intent.EXTRA_TITLE, "Image Chooser")
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray)
            startActivityForResult(chooserIntent, INPUT_FILE_REQUEST_CODE)
            return true
        }

        // openFileChooser for Android 3.0+
        // openFileChooser for Android < 3.0
        @JvmOverloads
        fun openFileChooser(uploadMsg: ValueCallback<Uri?>?, acceptType: String? = "") {
            mUploadMessage = uploadMsg
            // Create AndroidExampleFolder at sdcard
            // Create AndroidExampleFolder at sdcard
            val imageStorageDir = File(
                Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES
                ), "AndroidExampleFolder"
            )
            if (!imageStorageDir.exists()) {
                // Create AndroidExampleFolder at sdcard
                imageStorageDir.mkdirs()
            }

            // Create camera captured image file path and name
            val file = File(
                imageStorageDir.toString() + File.separator + "IMG_"
                        + System.currentTimeMillis().toString() + ".jpg"
            )
            mCapturedImageURI = Uri.fromFile(file)

            // Camera capture image intent
            val captureIntent = Intent(
                MediaStore.ACTION_IMAGE_CAPTURE
            )
            captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI)
            val i = Intent(Intent.ACTION_GET_CONTENT)
            i.addCategory(Intent.CATEGORY_OPENABLE)
            i.type = "image/*"

            // Create file chooser intent
            val chooserIntent = Intent.createChooser(i, "Image Chooser")

            // Set camera intent to file chooser
            chooserIntent.putExtra(
                Intent.EXTRA_INITIAL_INTENTS, arrayOf<Parcelable>(captureIntent)
            )

            // On select image call onActivityResult method of activity
            startActivityForResult(chooserIntent, FILECHOOSER_RESULTCODE)
        }

        //openFileChooser for other Android versions
        fun openFileChooser(
            uploadMsg: ValueCallback<Uri?>?,
            acceptType: String?,
            capture: String?
        ) {
            openFileChooser(uploadMsg, acceptType)
        }
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (requestCode != INPUT_FILE_REQUEST_CODE || mFilePathCallback == null) {
                super.onActivityResult(requestCode, resultCode, data)
                return
            }
            var results: Array<Uri>? = null

            // Check that the response is a good one
            if (resultCode == RESULT_OK) {
                if (data == null) {
                    // If there is not data, then we may have taken a photo
                    if (mCameraPhotoPath != null) {
                        results = arrayOf(Uri.parse(mCameraPhotoPath))
                    }
                } else {
                    val dataString = data.dataString
                    if (dataString != null) {
                        results = arrayOf(Uri.parse(dataString))
                    }
                }
            }
            mFilePathCallback!!.onReceiveValue(results)
            mFilePathCallback = null
        } else if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
            if (requestCode != FILECHOOSER_RESULTCODE || mUploadMessage == null) {
                super.onActivityResult(requestCode, resultCode, data)
                return
            }
            if (requestCode == FILECHOOSER_RESULTCODE) {
                if (null == mUploadMessage) {
                    return
                }
                var result: Uri? = null
                try {
                    result = if (resultCode != RESULT_OK) {
                        null
                    } else {

                        // retrieve from the private variable if the intent is null
                        if (data == null) mCapturedImageURI else data.data
                    }
                } catch (e: Exception) {
                    Toast.makeText(
                        applicationContext, "activity :$e",
                        Toast.LENGTH_LONG
                    ).show()
                }
                mUploadMessage!!.onReceiveValue(result)
                mUploadMessage = null
            }
        }
        return
    }

    companion object {
        private const val INPUT_FILE_REQUEST_CODE = 1
        private const val FILECHOOSER_RESULTCODE = 1
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