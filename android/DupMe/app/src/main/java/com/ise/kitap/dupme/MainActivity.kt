package com.ise.kitap.dupme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var strUsername: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnFindMatch.setOnClickListener {
            if(verifyUserInput()) {
                findMatch()
            }
        }
    }

    private fun verifyUserInput(): Boolean {
        strUsername = edtUsername.text.toString()


    }

    private fun findMatch() {
        val intent = Intent(this, GamePlayerActivity::class.java)
        startActivity(intent)
    }
}


