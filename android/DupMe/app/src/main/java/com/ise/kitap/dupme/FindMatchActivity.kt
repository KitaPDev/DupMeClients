package com.ise.kitap.dupme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ise.kitap.dupme.lib.TCPSocket
import com.ise.kitap.dupme.lib.TCPSocketHandler

class FindMatchActivity : AppCompatActivity() {

    var tcpSocket = TCPSocketHandler.tcpSocket

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_match)
    }
}
