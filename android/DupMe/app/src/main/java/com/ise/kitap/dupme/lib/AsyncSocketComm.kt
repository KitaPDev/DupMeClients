package com.ise.kitap.dupme.lib

import android.os.AsyncTask
import java.net.Socket

class AsyncSocketComm(tcpSocket: TCPSocket) : AsyncTask<String, Void, String>() {

    var tcpSocket = TCPSocket()

    init{
        if(tcpSocket.isConnected()) {
            this.tcpSocket = tcpSocket

        } else System.err.println("AsyncSocketComm failed...Socket not yet connected!")
    }

    override fun doInBackground(vararg strData: String): String {
        return tcpSocket.requestfromServer(strData.toString())
    }

    override fun onPreExecute() {
        super.onPreExecute()
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
    }
}