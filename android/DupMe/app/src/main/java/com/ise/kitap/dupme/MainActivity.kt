package com.ise.kitap.dupme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ise.kitap.dupme.lib.AsyncSocketComm
import com.ise.kitap.dupme.lib.TCPSocket
import com.ise.kitap.dupme.lib.TCPSocketHandler
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var strUsername: String = ""
    private val tcpIP = "127.0.0.1"
    private val tcpPORT = 54321

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

        if(strUsername.contains(" ")) {
            Toast.makeText(this, "Username must not contain whitespace!",
                Toast.LENGTH_SHORT).show()
            return false
        }

        val tcpSocket = TCPSocket(tcpIP, tcpPORT)
        val asyncSocket = AsyncSocketComm(tcpSocket)
        val strData = "Player set_username " + edtUsername.text.toString()

        TCPSocketHandler.tcpSocket = tcpSocket

        asyncSocket.execute(strData).get()

        return true
    }

    private fun findMatch() {
        val intent = Intent(this, FindMatchActivity::class.java)
        startActivity(intent)
    }

}


