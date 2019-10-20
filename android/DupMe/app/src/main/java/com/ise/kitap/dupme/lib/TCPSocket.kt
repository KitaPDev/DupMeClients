package com.ise.kitap.dupme.lib

import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.net.Socket

class TCPSocket {

    var socket = Socket()

    fun establishConnection(p_strIPAddress: String, p_iPort: Int) {
        socket = Socket(p_strIPAddress, p_iPort)
    }

    fun txToServer(p_strData: String) {
        if(socket.isConnected) {
            var oos = ObjectOutputStream(socket.getOutputStream())
            oos.writeUTF(p_strData)
            oos.flush()

        } else System.err.println("Unable to transmit data...Socket not yet connected!")
    }

    fun requestfromServer(p_strData: String): String {
        if(socket.isConnected) {
            txToServer(p_strData)

            var ois = ObjectInputStream(socket.getInputStream())
            return ois.readUTF()

        } else return "Unable to transmit data...Socket not yet connected!"
    }

    fun isConnected(): Boolean {
        return socket.isConnected
    }

    fun close() {
        socket.close()
    }

}