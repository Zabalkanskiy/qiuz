package com.example.qiuz

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class ResultActivity : AppCompatActivity() {
    lateinit var score: TextView
    lateinit var scoreText: TextView
    lateinit var intentButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        score = findViewById(R.id.result_score)
        scoreText = findViewById(R.id.result_motivation)
        intentButton = findViewById(R.id.again)


        val intent: Intent = getIntent()
        val result: Int = intent.getIntExtra("RESULT", 0)
        if (result == 5){
            scoreText.text = "Excellent"
            score.text = "5/5"
            score.setTextColor(Color.parseColor("#FFD700"))
            scoreText.setTextColor(Color.parseColor("#FFD700"))

        }else if (result == 4){
            scoreText.text = "Good"
            score.text ="4/5"
            score.setTextColor(Color.parseColor("#808080"))
            scoreText.setTextColor(Color.parseColor("#808080"))

        }else if (result == 3){
            scoreText.text = "Good"
            score.text = "3/5"
            score.setTextColor(Color.parseColor("#cd7f32"))
            scoreText.setTextColor(Color.parseColor("#cd7f32"))
        }else {
            scoreText.text = "Bad"
            score.text = "$result/5"
            score.setTextColor(Color.parseColor("#a19d94"))
            scoreText.setTextColor(Color.parseColor("#a19d94"))
        }

        intentButton.setOnClickListener{ view ->
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        intentButton.setTextColor(Color.BLACK)

    }
}