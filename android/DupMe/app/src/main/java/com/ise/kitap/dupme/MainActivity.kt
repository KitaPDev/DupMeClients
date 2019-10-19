package com.ise.kitap.dupme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val username: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnFindMatch.setOnClickListener {
            if(verifyUserInput()) {
                openGamePlayer()
            }
        }
    }

    private fun verifyUserInput(): Boolean {
        return edtUsername.text.isNotBlank()
    }

    private fun openGamePlayer() {
        val intent = Intent(this, GamePlayerActivity::class.java)
        startActivity(intent)
    }
}


