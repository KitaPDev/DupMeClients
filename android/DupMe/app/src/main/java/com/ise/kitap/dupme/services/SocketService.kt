package com.ise.kitap.dupme.services

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.Inet4Address
import java.net.Socket

class SocketService : Service() {

    private val myBinder = LocalBinder()
    private val serverIP = "127.0.0.1"
    private val serverPort = 54321

    var socket = Socket()
    var strData = ""
    var dos: DataOutputStream? = null
    var dis: DataInputStream? = null

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

        if (intent != null) {
           strData = intent.getStringExtra("clientMessage")
        }

        Thread(ClientSocket()).start()
        return START_STICKY
    }

    fun requestFromServer(message: String): String {
        dos = DataOutputStream(socket.getOutputStream())
        if(dos != null) {
            println("Send message: $message")
            dos!!.writeUTF(message)
            dos!!.flush()
        }

        dis = DataInputStream(socket.getInputStream())
        return if(dis != null) {
            dis!!.readUTF()

        } else ""
    }

    inner class ClientSocket : Runnable {

        override fun run() {
            try {
                var serverAddress = Inet4Address.getByName(serverIP)
                println("Client Connecting...")

                socket = Socket(serverAddress, serverPort)

            } catch(e: Exception) {
                println("Socket Error")
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
}