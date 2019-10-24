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

    private val binder = LocalBinder()
    private val serverIP = "192.168.43.61"
    private val serverPort = 54321

    var socket = Socket()
    var output: BufferedWriter? = null
    var input: BufferedReader? = null

    inner class LocalBinder : Binder() {
        fun getService(): SocketService = this@SocketService
    }

    override fun onCreate() {
        super.onCreate()
    }

    override fun onBind(p0: Intent?): IBinder? {
        return binder
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        Thread(ClientSocket()).start()
        return START_STICKY
    }

    fun requestFromServer(message: String): String {
        return AsyncSocketComm(true).execute(message).get()
    }

    fun sendToServer(message: String) {
        AsyncSocketComm(false).execute(message)
    }

    inner class ClientSocket : Runnable {

        override fun run() {
            try {
                println("Client Connecting...")

                if(!socket.isConnected) {
                    socket = Socket(serverIP, serverPort)
                }
                output = BufferedWriter(OutputStreamWriter(socket.getOutputStream()))
                input = BufferedReader(InputStreamReader(socket.getInputStream()))

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
            println("Service destroyed...Socket closed.")

        } catch(e: Exception) {
            e.printStackTrace()
        }
    }

    inner class AsyncSocketComm(recvMode: Boolean) : AsyncTask<String, Void, String>() {

        var recvMode = false

        init {
            this.recvMode = recvMode
        }

        override fun doInBackground(vararg strData: String): String {

            if(recvMode) {
                if(output != null) {
                    val strMessage = strData[0]
                    println("Send message: $strMessage")
                    output!!.write(strMessage)
                    output!!.newLine()
                    output!!.flush()
                }

                return if(input != null) {
                    val response = input!!.readLine()
                    println("Server Response: $response")
                    response

                } else ""

            } else {
                if(output != null) {
                    val strMessage = strData[0]
                    println("Send message: $strMessage")
                    output!!.write(strMessage)
                    output!!.newLine()
                    output!!.flush()
                }
                return ""
            }
        }
    }
}