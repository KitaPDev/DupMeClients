package com.ise.kitap.dupme

import android.media.SoundPool
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import kotlinx.android.synthetic.main.activity_gameplayer.*

class GamePlayerActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gameplayer)
        val soundPool: SoundPool = SoundPool.Builder().setMaxStreams(7).build()

        val soundA = soundPool.load(this, R.raw.a, 1)
        val soundB = soundPool.load(this, R.raw.b, 1)
        val soundC = soundPool.load(this, R.raw.c, 1)
        val soundD = soundPool.load(this, R.raw.d, 1)
        val soundE = soundPool.load(this, R.raw.e, 1)
        val soundF = soundPool.load(this, R.raw.f, 1)
        val soundG = soundPool.load(this, R.raw.g, 1)

        btnC.setOnClickListener {
            soundPool.play(soundC,1F, 1F, 0, 0 , 1F)
        }
        btnD.setOnClickListener {
            soundPool.play(soundD,1F, 1F, 0, 0 , 1F)
        }
        btnE.setOnClickListener {
            soundPool.play(soundE,1F, 1F, 0, 0 , 1F)

        }
        btnF.setOnClickListener {
            soundPool.play(soundF,1F, 1F, 0, 0 , 1F)

        }
        btnG.setOnClickListener {
            soundPool.play(soundG,1F, 1F, 0, 0 , 1F)

        }
        btnA.setOnClickListener {
            soundPool.play(soundA,1F, 1F, 0, 0 , 1F)

        }
        btnB.setOnClickListener {
            soundPool.play(soundB,1F, 1F, 0, 0 , 1F)

        }


    }

    private fun setTime(long: Long){
        val timer = object : CountDownTimer(long, 1000) {
            override fun onFinish() {
                Timer.text = "Next player turn"
            }

            override fun onTick(millisUntilFinished: Long) {
                Timer.text = (millisUntilFinished/1000).toString() + "seconds"
            }

        }
        timer.start()
    }
    //Piano enabling and disabling right here
    private fun disableButton(view: View) {
        view.isEnabled = false
    }

    private fun disablePiano() {
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

    private fun enablePiano(){
        enableButton(btnA)
        enableButton(btnB)
        enableButton(btnC)
        enableButton(btnD)
        enableButton(btnE)
        enableButton(btnF)
        enableButton(btnG)
    }




}
