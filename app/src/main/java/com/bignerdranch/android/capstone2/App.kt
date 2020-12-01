package com.bignerdranch.android.capstone2

import android.app.Application

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        ServiceLocator.init(this)

    }

}
