package com.ise.kitap.dupme

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.widget.Toast
import com.ise.kitap.dupme.services.SocketService
import android.graphics.Color.rgb
import android.view.View
import android.view.animation.TranslateAnimation
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var mBoundSocketService: SocketService? = null
    var isBound = false
    private var strUsername: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        welcomePage()

        btnStart.setOnClickListener {
            startActivity()
        }

        val intent = Intent(this, SocketService::class.java)
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)


        btnFindMatch.setOnClickListener {
            setProgressBar()
            if(verifyUserInput()) {



                findMatch()
            }
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

        var strResponse = mBoundSocketService?.requestFromServer(strUsername)

        if(strResponse.equals("OK")) {
            return true
        }

        return false
    }

    private fun findMatch() {
        val intent = Intent(this, FindMatchActivity::class.java)
        unbindService(serviceConnection)
        startActivity(intent)
    }

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            val binder = service as SocketService.LocalBinder
            mBoundSocketService = binder.getService()
            isBound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            isBound = false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(serviceConnection)
    }

    private fun setProgressBar() {
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

    private fun welcomePage(){
        edtUsername.visibility = View.INVISIBLE
        btnFindMatch.visibility = View.INVISIBLE
    }

    private fun startActivity(){
        edtUsername.visibility = View.VISIBLE
        btnFindMatch.visibility = View.VISIBLE
        btnStart.visibility = View.INVISIBLE
        startAnimation(edtUsername)
        startAnimation(btnFindMatch)
    }

    private fun startAnimation(view: View) {
        val animate = TranslateAnimation(0F, 0F, view.height.toFloat(), 0F)
        animate.duration = 500
        animate.fillAfter = true
        view.startAnimation(animate)
    }
}


