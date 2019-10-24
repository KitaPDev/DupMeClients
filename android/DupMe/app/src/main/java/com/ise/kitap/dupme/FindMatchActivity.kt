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

    var mBoundSocketService: SocketService? = null
    var isBound = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_match)

        val intentService = Intent(this, SocketService::class.java)
        startService(intentService)
        bindService(intentService, serviceConnection, Context.BIND_AUTO_CREATE)

        btnCancel_findMatch.setOnClickListener {
            cancelMatch()
        }
    }

    private fun findMatch() {
        val strMessage = "find_match"
        val strResponse = mBoundSocketService?.requestFromServer(strMessage).toString()

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

        startActivity(intent)
        unbindService(serviceConnection)
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
