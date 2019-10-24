package com.ise.kitap.dupme

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.view.View
import com.ise.kitap.dupme.lib.SharedPreference
import com.ise.kitap.dupme.services.SocketService
import kotlinx.android.synthetic.main.activity_find_match.*

class FindMatchActivity : AppCompatActivity() {

    private val REQ_AGAIN = "REQ_AGAIN"

    var mBoundSocketService: SocketService? = null
    var isBound = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_match)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()

        val intentService = Intent(this, SocketService::class.java)
        startService(intentService)
        applicationContext.bindService(intentService, serviceConnection, Context.BIND_AUTO_CREATE)

        btnCancel_findMatch.setOnClickListener {
            cancelMatch()
        }
    }

    private fun findMatch() {
        var strResponse = REQ_AGAIN
        val strMessage = "find_match"
        strResponse = mBoundSocketService?.requestFromServer(strMessage).toString()

        val lsStrResponse = strResponse.split(' ')
        val sharedPreference = SharedPreference(this)
        sharedPreference.save("username_opponent", lsStrResponse[0])

        val intent = Intent(this, GamePlayerActivity::class.java)
        if(lsStrResponse.size > 1) {
            if(lsStrResponse[1] == "start") {
                sharedPreference.save("start_flag", true)

            } else {
                sharedPreference.save("start_flag", false)
            }
        }

        if(isBound) {
            applicationContext.unbindService(serviceConnection)
        }
        startActivity(intent)
    }

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            val binder = service as SocketService.LocalBinder
            mBoundSocketService = binder.getService()
            isBound = true

            findMatch()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            isBound = false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(serviceConnection)
    }

    private fun cancelMatch() {
        unbindService(serviceConnection)
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
