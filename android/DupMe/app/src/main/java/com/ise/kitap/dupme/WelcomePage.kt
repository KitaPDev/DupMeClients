package com.ise.kitap.dupme

import android.os.Bundle


class WelcomePage : MainActivity() {

    companion object{
        var isAppStart = false
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isAppStart = false
    }


}