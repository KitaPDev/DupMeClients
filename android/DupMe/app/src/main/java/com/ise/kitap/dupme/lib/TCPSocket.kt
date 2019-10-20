package com.ise.kitap.dupme.lib

import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.Socket

class TCPSocket(p_strIPAddress: String, p_iPort: Int) {

    var socket = Socket()

    init {
        if(p_strIPAddress.length > 7 && p_iPort != 0) {
            socket = Socket(p_strIPAddress, p_iPort)
        }
    }

    fun sendToServer(p_strData: String) {
        if(socket.isConnected) {
            val dos = DataOutputStream(socket.getOutputStream())
            dos.writeUTF(p_strData)
            dos.flush()

        } else System.err.println("Unable to transmit data...Socket not yet connected!")
    }

    fun requestFromServer(p_strData: String): String {
        if(socket.isConnected) {
            sendToServer(p_strData)

            val dis = DataInputStream(socket.getInputStream())
            return dis.readUTF()

        } else return "Unable to transmit data...Socket not yet connected!"
    }

    fun isConnected(): Boolean {
        return socket.isConnected
    }

    fun close() {
        socket.close()
    }

}