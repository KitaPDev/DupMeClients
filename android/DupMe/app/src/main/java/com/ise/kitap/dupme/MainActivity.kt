package com.ise.kitap.dupme

import android.content.Intent
import android.graphics.Color.rgb
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.TranslateAnimation
import android.widget.Toast
import com.ise.kitap.dupme.lib.AsyncSocketComm
import com.ise.kitap.dupme.lib.TCPSocket
import com.ise.kitap.dupme.lib.TCPSocketHandler
import kotlinx.android.synthetic.main.activity_gameplayer.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var strUsername: String = ""
    private val tcpIP = "127.0.0.1"
    private val tcpPORT = 54321

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        WelcomePage()

        btnStart.setOnClickListener {
            StartActivity()
        }

        btnFindMatch.setOnClickListener {
//            SetProgressBar()
//            if(verifyUserInput()) {
//                findMatch()
//            }
            val intent = Intent(this, GamePlayerActivity::class.java)
            startActivity(intent)
        }

        btnCancel.setOnClickListener {
            cancelMatch()
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

    private fun SetProgressBar() {
        loading.visibility = View.VISIBLE
        loading.bringToFront()
        btnCancel.visibility = View.VISIBLE
        edtUsername.visibility = View.INVISIBLE
        btnFindMatch.visibility = View.INVISIBLE
        background.setBackgroundColor(rgb(211,211,211))

    }

    private fun cancelMatch(){
        loading.visibility=View.INVISIBLE
        btnCancel.visibility=View.INVISIBLE
        edtUsername.visibility=View.VISIBLE
        btnFindMatch.visibility=View.VISIBLE
        background.setBackgroundColor(rgb(255,255,255))
        edtUsername.bringToFront()
    }

    private fun WelcomePage(){
        edtUsername.visibility = View.INVISIBLE
        btnFindMatch.visibility = View.INVISIBLE
    }

    private fun StartActivity(){
        edtUsername.visibility = View.VISIBLE
        btnFindMatch.visibility = View.VISIBLE
        btnStart.visibility = View.INVISIBLE
        StartAnimation(edtUsername)
        StartAnimation(btnFindMatch)
    }

    private fun StartAnimation(view: View) {
        val animate = TranslateAnimation(0F, 0F, view.height.toFloat(), 0F)
        animate.duration = 500
        animate.fillAfter = true
        view.startAnimation(animate)
    }

}


