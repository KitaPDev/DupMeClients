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

open class MainActivity : AppCompatActivity() {

    var mBoundSocketService: SocketService? = null
    var isBound = false
    private var strUsername: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //For welcomePage to show only once
        if (WelcomePage.isAppStart){
            btnStart_main.visibility = View.INVISIBLE
            WelcomePageBackground.visibility = View.INVISIBLE
        }else {
            welcomePage()
            WelcomePage.isAppStart = true
        }

        val sharedPreference = SharedPreference(this)

        btnStart_main.setOnClickListener {
            startMainActivity()
        }

        val intentService = Intent(this, SocketService::class.java)
        bindService(intentService, serviceConnection, Context.BIND_AUTO_CREATE)

        btnFindMatch_main.setOnClickListener {
            setProgressBar()
            if(verifyUserInput()) {

                sharedPreference.save("username", strUsername)

                val intentActivity = Intent(this, FindMatchActivity::class.java)
                unbindService(serviceConnection)
                startActivity(intentActivity)
            }
        }

    }

    private fun verifyUserInput(): Boolean {
        strUsername = edtUsername_main.text.toString()

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
        edtUsername_main.visibility = View.INVISIBLE
        btnFindMatch_main.visibility = View.INVISIBLE
        background.setBackgroundColor(rgb(211,211,211))
    }

    fun welcomePage(){
        edtUsername_main.visibility = View.INVISIBLE
        btnFindMatch_main.visibility = View.INVISIBLE
    }

    private fun startMainActivity(){
        edtUsername_main.visibility = View.VISIBLE
        btnFindMatch_main.visibility = View.VISIBLE
        btnStart_main.visibility = View.INVISIBLE
        WelcomePageBackground.visibility = View.INVISIBLE
        startAnimationShow(edtUsername_main)
        startAnimationShow(btnFindMatch_main)
    }

    private fun startAnimationShow(view: View) {
        val animate = TranslateAnimation(0F, 0F, view.height.toFloat(), 0F)
        animate.duration = 500
        animate.fillAfter = true
        view.startAnimation(animate)
    }


}


