package com.ise.kitap.dupme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_scoreboard.*

class ScoreboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scoreboard)

        btnPlayAgain.setOnClickListener {
            goBackMainActivity()
        }

        btnLeave.setOnClickListener {
            goBackFindMatchActivity()
        }


    }

    private fun goBackMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun goBackFindMatchActivity() {
        startActivity(Intent(this, FindMatchActivity::class.java))
    }

}
