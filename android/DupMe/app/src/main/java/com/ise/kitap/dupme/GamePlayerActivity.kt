package com.ise.kitap.dupme

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.media.SoundPool
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.IBinder
import android.view.View
import com.ise.kitap.dupme.lib.SharedPreference
import com.ise.kitap.dupme.services.SocketService
import kotlinx.android.synthetic.main.activity_gameplayer.*

class GamePlayerActivity : AppCompatActivity() {

    var mBoundSocketService: SocketService? = null
    var isBound = false
    private val getOpponentKeysThread = GetOpponentKeysThread()

    private var strUsername: String = ""
    private var strUsernameOpponent: String = ""
    private var iScore = 0
    private var iScoreOpponent = 0
    private var lsKeys = ArrayList<String>()
    private var lsKeysOpponent = ArrayList<String>()
    private var turn = 0

    private var bolStart = false
    private var bolNextTurn = true
    private var bolThreadRun = false
    private var bolNewKey = false

    private var strResponse: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gameplayer)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()

        val intentService = Intent(this, SocketService::class.java)
        startService(intentService)
        applicationContext.bindService(intentService, serviceConnection, Context.BIND_AUTO_CREATE)

        val sharedPreference = SharedPreference(this)
        strUsername = sharedPreference.getValueString("username").toString()
        strUsernameOpponent = sharedPreference.getValueString("username_opponent").toString()
        
        txtUsername.text = strUsername
        txtUsernameOpponent.text = strUsernameOpponent
        txtScore.text = iScore.toString()
        txtScoreOpponent.text = iScoreOpponent.toString()
        runOnUiThread(getOpponentKeysThread)

        disableKeys()
        setupKeys()
    }

    private fun playGame() {
        if(bolNextTurn) {
            when(turn) {
                0 -> firstTurn()
                1 -> secondTurn()
                2 -> thirdTurn()
                3 -> fourthTurn()
                4 -> finishMatch()
            }
        }

        if(bolNewKey) {
            updateOpponentKeys(strResponse.toString())
            bolNewKey = false
        }
    }

    private fun firstTurn() {
        if(bolStart) {
            setTimer(10000)
            enableKeys()

        } else {
            setTimer(10000)
            disableKeys()

            bolThreadRun = true
        }
        bolNextTurn = false
        turn += 1
        playGame()
    }

    private fun secondTurn() {
        bolThreadRun = if(bolStart) {
            setTimer(20000)
            enableKeys()

            false
        } else {
            setTimer(20000)
            disableKeys()

            true
        }
        bolNextTurn = false
        turn += 1
        playGame()
    }

    private fun thirdTurn() {
        lsKeys.clear()
        lsKeysOpponent.clear()
        bolStart = !bolStart

        bolThreadRun = if(bolStart) {
            setTimer(10000)
            enableKeys()

            false
        } else {
            setTimer(10000)
            disableKeys()

            true
        }
        bolNextTurn = false
        turn += 1
        playGame()
    }

    private fun fourthTurn() {
        bolThreadRun = if(bolStart) {
            setTimer(20000)
            enableKeys()

            false
        } else {
            setTimer(20000)
            disableKeys()

            true
        }
        bolNextTurn = false
        turn += 1
        playGame()
    }

    private fun setTimer(long: Long){
        val timer = object : CountDownTimer(long, 1000) {
            override fun onFinish() {
                bolStart = !bolStart
            }

            override fun onTick(millisUntilFinished: Long) {
                val strTime = (millisUntilFinished/1000).toString()
                Timer.text = strTime
            }
        }
        timer.start()
    }

    private fun setupKeys() {
        val soundPool: SoundPool = SoundPool.Builder().setMaxStreams(7).build()
        val soundA = soundPool.load(this, R.raw.a, 1)
        val soundB = soundPool.load(this, R.raw.b, 1)
        val soundC = soundPool.load(this, R.raw.c, 1)
        val soundD = soundPool.load(this, R.raw.d, 1)
        val soundE = soundPool.load(this, R.raw.e, 1)
        val soundF = soundPool.load(this, R.raw.f, 1)
        val soundG = soundPool.load(this, R.raw.g, 1)
        //Can use this function to light up button when the opposing player is clicking
        //btnF.performClick()

        btnC.setOnClickListener {
            soundPool.play(soundC,1F, 1F, 0, 0 , 1F)
            if(bolStart) {
                mBoundSocketService?.sendToServer("C")
                lsKeys.add("C")

                if(lsKeysOpponent.isNotEmpty()) {
                    if(lsKeysOpponent[0] == "C") {
                        iScore += 1
                        lsKeysOpponent.removeAt(0)
                    }
                }
            }

            delayBetweenClicks()
        }
        btnD.setOnClickListener {
            soundPool.play(soundD,1F, 1F, 0, 0 , 1F)
            if(bolStart) {
                mBoundSocketService?.sendToServer("D")
                lsKeys.add("D")

                if(lsKeysOpponent.isNotEmpty()) {
                    if(lsKeysOpponent[0] == "D") {
                        iScore += 1
                        lsKeysOpponent.removeAt(0)
                    }
                }

            }
            delayBetweenClicks()
        }
        btnE.setOnClickListener {
            soundPool.play(soundE,1F, 1F, 0, 0 , 1F)
            if(bolStart) {
                mBoundSocketService?.sendToServer("E")
                lsKeys.add("E")

                if(lsKeysOpponent.isNotEmpty()) {
                    if(lsKeysOpponent[0] == "E") {
                        iScore += 1
                        lsKeysOpponent.removeAt(0)
                    }
                }
            }
            delayBetweenClicks()
        }
        btnF.setOnClickListener {
            soundPool.play(soundF,1F, 1F, 0, 0 , 1F)
            if(bolStart) {
                mBoundSocketService?.sendToServer("F")
                lsKeys.add("F")

                if(lsKeysOpponent.isNotEmpty()) {
                    if(lsKeysOpponent[0] == "F") {
                        iScore += 1
                        lsKeysOpponent.removeAt(0)
                    }
                }
            }
            delayBetweenClicks()
        }
        btnG.setOnClickListener {
            soundPool.play(soundG,1F, 1F, 0, 0 , 1F)
            if(bolStart) {
                mBoundSocketService?.sendToServer("G")
                lsKeys.add("G")

                if(lsKeysOpponent.isNotEmpty()) {
                    if(lsKeysOpponent[0] == "G") {
                        iScore += 1
                        lsKeysOpponent.removeAt(0)
                    }
                }
            }
            delayBetweenClicks()
        }
        btnA.setOnClickListener {
            soundPool.play(soundA,1F, 1F, 0, 0 , 1F)
            if(bolStart) {
                mBoundSocketService?.sendToServer("A")
                lsKeys.add("A")

                if(lsKeysOpponent.isNotEmpty()) {
                    if(lsKeysOpponent[0] == "A") {
                        iScore += 1
                        lsKeysOpponent.removeAt(0)
                    }
                }
            }
            delayBetweenClicks()
        }
        btnB.setOnClickListener {
            soundPool.play(soundB,1F, 1F, 0, 0 , 1F)
            if(bolStart) {
                mBoundSocketService?.sendToServer("B")
                lsKeys.add("B")

                if(lsKeysOpponent.isNotEmpty()) {
                    if(lsKeysOpponent[0] == "B") {
                        iScore += 1
                        lsKeysOpponent.removeAt(0)
                    }
                }
            }
            delayBetweenClicks()
        }
    }

    //Piano enabling and disabling right here
    private fun disableButton(view: View) {
        view.isEnabled = false
    }

    private fun disableKeys() {
        disableButton(btnC)
        disableButton(btnA)
        disableButton(btnB)
        disableButton(btnD)
        disableButton(btnE)
        disableButton(btnF)
        disableButton(btnG)
    }

    private fun enableButton(view: View){
        view.isEnabled = true
    }

    private fun enableKeys(){
        enableButton(btnA)
        enableButton(btnB)
        enableButton(btnC)
        enableButton(btnD)
        enableButton(btnE)
        enableButton(btnF)
        enableButton(btnG)
    }

    //Set delay between clicks for better gaming experience
    private fun delayBetweenClicks() {
        disableKeys()
        val timer = object : CountDownTimer(150, 1000) {
            override fun onFinish() {
                enableKeys()
            }

            override fun onTick(millisUntilFinished: Long) {}

        }
        timer.start()
    }

    private fun updateOpponentKeys(response: String) {
        when(response) {
            "C" -> btnC.performClick()
            "D" -> btnD.performClick()
            "E" -> btnE.performClick()
            "F" -> btnF.performClick()
            "G" -> btnG.performClick()
            "A" -> btnA.performClick()
            "B" -> btnB.performClick()
        }

        if(response == lsKeys[0]) {
            iScoreOpponent += 1
            lsKeys.removeAt(0)
        }
    }

    private fun finishMatch() {
        val intent = Intent(this, ScoreboardActivity::class.java)
        intent.putExtra("score", iScore)
        intent.putExtra("score_opponent", iScoreOpponent)

        startActivity(intent)

        if(isBound) {
            unbindService(serviceConnection)
        }
    }

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            val binder = service as SocketService.LocalBinder
            mBoundSocketService = binder.getService()
            isBound = true

            val strResponse = mBoundSocketService!!.requestFromServer("get_start_bit")

            bolStart = strResponse == "1"
            bolNextTurn = true
            playGame()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            isBound = false
        }
    }

    inner class GetOpponentKeysThread : Runnable {

        override fun run() {
            if(bolThreadRun) {
                val strMessage = "get_key"
                strResponse = mBoundSocketService?.requestFromServer(strMessage)
                if (strResponse != null) {
                    println(strResponse)
                    bolNewKey = true
                }
            }
        }
    }
}
