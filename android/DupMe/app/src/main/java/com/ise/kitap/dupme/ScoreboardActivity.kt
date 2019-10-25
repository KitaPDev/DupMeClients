package com.ise.kitap.dupme

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.ise.kitap.dupme.lib.SharedPreference
import com.ise.kitap.dupme.services.SocketService
import kotlinx.android.synthetic.main.activity_gameplayer.*
import kotlinx.android.synthetic.main.activity_scoreboard.*

class ScoreboardActivity : AppCompatActivity() {

    var mBoundSocketService: SocketService? = null
    var isBound = false

    var iScore = 0
    var iScoreOpponent = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scoreboard)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()

        val sharedPreference = SharedPreference(this)
        val strUsername = sharedPreference.getValueString("username").toString()
        val strUsernameOpponent = sharedPreference.getValueString("username_opponent").toString()

        iScore = intent.getIntExtra("score",  0)
        iScoreOpponent = intent.getIntExtra("score_opponent", 0)

        val intentService = Intent(this, SocketService::class.java)
        bindService(intentService, serviceConnection, Context.BIND_AUTO_CREATE)

        if(iScore > iScoreOpponent) {
            txtUsernameWinner.text = strUsername

        } else {
            txtUsernameWinner.text = strUsernameOpponent
        }

        txtScoreFinal1.text = iScore.toString()
        txtScoreFinal2.text = iScoreOpponent.toString()

        btnPlayAgain.setOnClickListener {
            startActivity(Intent(this, FindMatchActivity::class.java))
        }

        btnLeave.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            mBoundSocketService?.sendToServer("close")
        }
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
}
