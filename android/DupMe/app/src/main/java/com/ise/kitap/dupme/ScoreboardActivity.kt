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

        btnPlayAgain.setOnClickListener(View.OnClickListener {
            openGameplayer()
        })


    }

    private fun openGameplayer() {
        val intent = Intent(this, GamePlayerActivity::class.java)
        startActivity(intent)
    }

}
