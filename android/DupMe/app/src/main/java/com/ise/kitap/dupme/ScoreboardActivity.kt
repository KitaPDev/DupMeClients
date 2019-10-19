package com.ise.kitap.dupme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText

class ScoreboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scoreboard)

        val button: Button = findViewById(R.id.button)
        button.setOnClickListener(View.OnClickListener {
            openGameplayer()
        })


    }

    private fun openGameplayer() {
        val intent = Intent(this, GameplayerActivity::class.java)
        startActivity(intent)
    }

}
