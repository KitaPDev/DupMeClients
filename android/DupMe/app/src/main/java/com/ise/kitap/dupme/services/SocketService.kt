package com.ise.kitap.dupme.services

import android.app.Service
import android.content.Intent
import android.os.AsyncTask
import android.os.Binder
import android.os.IBinder
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import java.io.*
import java.net.Socket

class SocketService : Service() {

    private val myBinder = LocalBinder()
    private val serverIP = "10.202.227.203"
    private val serverPort = 54321

    var socket = Socket()
    var strData = ""
    var output: BufferedWriter? = null
    var input: BufferedReader? = null

    inner class LocalBinder : Binder() {
        fun getService(): SocketService {
            return this@SocketService
        }
    }

    override fun onBind(p0: Intent?): IBinder? {
        return myBinder
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)

//        if (intent != null) {
//           strData = intent.getStringExtra("clientMessage")
//        }

        Thread(ClientSocket()).start()
        return START_STICKY
    }

    fun requestFromServer(message: String): String {
        return AsyncSocketComm().execute(message).get()
    }

    inner class ClientSocket : Runnable {

        override fun run() {
            try {
                println("Client Connecting...")

                socket = Socket(serverIP, serverPort)

                println("Connected!")

            } catch(e: Exception) {
                println("Socket Error")
                e.printStackTrace()
            }
        }
    }

    override fun sendBroadcast(intent: Intent?) {
        super.sendBroadcast(intent)
        if (intent != null) {
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            socket.close()

        } catch(e: Exception) {
            e.printStackTrace()
        }
        socket = Socket()
    }

    inner class AsyncSocketComm : AsyncTask<String, Void, String>() {

        override fun doInBackground(vararg strData: String): String {

            output = BufferedWriter(OutputStreamWriter(socket.getOutputStream()))
            input = BufferedReader(InputStreamReader(socket.getInputStream()))

            if(output != null) {
                val strMessage = strData[0]
                println("Send message: $strMessage")
                output!!.write(strMessage)
                output!!.newLine()
                output!!.flush()
            }

            return if(input != null) {
                val response = input!!.readLine()
                println("Response: $response")
                response

            } else ""
        }
    }
}