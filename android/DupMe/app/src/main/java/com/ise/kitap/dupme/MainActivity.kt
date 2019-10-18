package com.ise.kitap.dupme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button: Button = findViewById(R.id.button)
        val editText: EditText = findViewById(R.id.editText)
        button.setOnClickListener(View.OnClickListener {
            openGameplayer()
        })


    }

    private fun openGameplayer() {
        val intent = Intent(this, GameplayerActivity::class.java)
        startActivity(intent)
    }
}


