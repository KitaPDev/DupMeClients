package com.ise.kitap.dupme

import android.media.SoundPool
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

}
