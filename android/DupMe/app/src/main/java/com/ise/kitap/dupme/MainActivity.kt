package com.ise.kitap.dupme

import android.content.ComponentName
import android.content.Context
import android.content.Intent
<<<<<<< HEAD
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.widget.Toast
import com.ise.kitap.dupme.services.SocketService
=======
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
>>>>>>> 0e43db0250851519a31c2249574b0c91bdb43604
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var mBoundSocketService: SocketService? = null
    var isBound = false
    private var strUsername: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        WelcomePage()

        btnStart.setOnClickListener {
            StartActivity()
        }

        val intent = Intent(this, SocketService::class.java)
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)


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

<<<<<<< HEAD
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
=======
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

>>>>>>> 0e43db0250851519a31c2249574b0c91bdb43604
}


