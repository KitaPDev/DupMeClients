package com.ise.kitap.dupme

import android.media.AudioManager
import android.media.SoundPool
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class GameplayerActivity : AppCompatActivity() {

//    private var soundPool: SoundPool? = null
//    private var c: Button = Button(this.baseContext)
//    private var d: Button = Button(this.baseContext)
//    private var e: Button = Button(this.baseContext)
//    private var f: Button = Button(this.baseContext)
//    private var g: Button = Button(this.baseContext)
//    private var a: Button = Button(this.baseContext)
//    private var b: Button = Button(this.baseContext)

//    private var sound_c: Int? = null
//    private var sound_d: Int? = null
//    private var sound_e: Int? = null
//    private var sound_f: Int? = null
//    private var sound_g: Int? = null
//    private var sound_a: Int? = null
//    private var sound_b: Int? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gameplayer)

        val c: Button = findViewById(R.id.c)
        val d: Button = findViewById(R.id.d)
        val e: Button = findViewById(R.id.e)
        val f: Button = findViewById(R.id.f)
        val g: Button = findViewById(R.id.g)
        val a: Button = findViewById(R.id.a)
        val b: Button = findViewById(R.id.b)

        val soundPool: SoundPool = SoundPool.Builder().setMaxStreams(5).build()

        val sound_a = soundPool.load(this, R.raw.a, 1)
        val sound_b = soundPool.load(this, R.raw.b, 1)
        val sound_c = soundPool.load(this, R.raw.c, 1)
        val sound_d = soundPool.load(this, R.raw.d, 1)
        val sound_e = soundPool.load(this, R.raw.e, 1)
        val sound_f = soundPool.load(this, R.raw.f, 1)
        val sound_g = soundPool.load(this, R.raw.g, 1)



//        c.setOnClickListener {
//            soundPool.play(sound_c,1F, 1F, 0, 0 , 1F)
//
//        }
//        d.setOnClickListener {
//            soundPool.play(sound_d,1F, 1F, 0, 0 , 1F)
//
//        }
//        e.setOnClickListener {
//            soundPool.play(sound_e,1F, 1F, 0, 0 , 1F)
//
//        }
//        f.setOnClickListener {
//            soundPool.play(sound_f,1F, 1F, 0, 0 , 1F)
//
//        }
//        g.setOnClickListener {
//            soundPool.play(sound_g,1F, 1F, 0, 0 , 1F)
//
//        }
//        a.setOnClickListener {
//            soundPool.play(sound_a,1F, 1F, 0, 0 , 1F)
//
//        }
//        b.setOnClickListener {
//            soundPool.play(sound_b,1F, 1F, 0, 0 , 1F)
//
//        }
        c.setOnClickListener(View.OnClickListener {
            soundPool.play(sound_c,1F, 1F, 0, 0 , 1F)
        })
        d.setOnClickListener(View.OnClickListener {
            soundPool.play(sound_d,1F, 1F, 0, 0 , 1F)
        })
        e.setOnClickListener(View.OnClickListener {
            soundPool.play(sound_e,1F, 1F, 0, 0 , 1F)

        })
        f.setOnClickListener(View.OnClickListener {
            soundPool.play(sound_f,1F, 1F, 0, 0 , 1F)

        })
        g.setOnClickListener(View.OnClickListener {
            soundPool.play(sound_g,1F, 1F, 0, 0 , 1F)

        })
        a.setOnClickListener(View.OnClickListener {
            soundPool.play(sound_a,1F, 1F, 0, 0 , 1F)

        })
        b.setOnClickListener(View.OnClickListener {
            soundPool.play(sound_b,1F, 1F, 0, 0 , 1F)

        })



    }

}
