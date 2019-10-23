package com.ise.kitap.dupme

import android.content.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.widget.Toast
import com.ise.kitap.dupme.services.SocketService
import android.graphics.Color.rgb
import android.view.View
import android.view.animation.TranslateAnimation
import com.ise.kitap.dupme.lib.SharedPreference
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var mBoundSocketService: SocketService? = null
    var isBound = false
    private var strUsername: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        welcomePage()

        val sharedPreference = SharedPreference(this)

        btnStart.setOnClickListener {
            startActivity()
        }

        val intentService = Intent(this, SocketService::class.java)
        bindService(intentService, serviceConnection, Context.BIND_AUTO_CREATE)

        btnFindMatch.setOnClickListener {
            setProgressBar()
            if(verifyUserInput()) {

                sharedPreference.save("username", strUsername)

                val intentActivity = Intent(this, FindMatchActivity::class.java)
                unbindService(serviceConnection)
                startActivity(intentActivity)
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

        } else if(strResponse.equals("BAD")){
            Toast.makeText(this, "Username must not contain whitespace!",
                Toast.LENGTH_SHORT).show()
        }

        return false
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
        prgBar_main.visibility = View.VISIBLE
        prgBar_main.bringToFront()
        btnCancel.visibility = View.VISIBLE
        edtUsername.visibility = View.INVISIBLE
        btnFindMatch.visibility = View.INVISIBLE
        background.setBackgroundColor(rgb(211,211,211))
    }

    private fun cancelMatch(){
        prgBar_main.visibility=View.INVISIBLE
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


