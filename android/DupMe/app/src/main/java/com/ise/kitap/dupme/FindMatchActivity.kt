package com.ise.kitap.dupme

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import com.ise.kitap.dupme.lib.SharedPreference
import com.ise.kitap.dupme.services.SocketService
import kotlinx.android.synthetic.main.activity_find_match.*

class FindMatchActivity : AppCompatActivity() {

    val REQ_AGAIN = "REQ_AGAIN"

    var mBoundSocketService: SocketService? = null
    var isBound = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_match)

        val intentService = Intent(this, SocketService::class.java)
        bindService(intentService, serviceConnection, Context.BIND_AUTO_CREATE)

        btnCancel_findMatch.setOnClickListener {
            cancelMatch()
        }

        findMatch()
    }

    private fun findMatch() {
        var strResponse = REQ_AGAIN
        val strMessage = "find_match"
        while(strResponse == REQ_AGAIN) {
            strResponse = mBoundSocketService?.requestFromServer(strMessage).toString()
        }

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

    private fun cancelMatch() {
        unbindService(serviceConnection)
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
