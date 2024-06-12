package com.example.finaldirectionexample01

import android.app.Application
import com.example.finaldirectionexample01.data.AppContainer

class FinalDirectionApplication: Application() {

    val appContainer = AppContainer()

    override fun onCreate() {
        INSTANCE = this
        super.onCreate()
    }

    companion object{
        private var INSTANCE: FinalDirectionApplication? = null

//        fun getInstance() = INSTANCE
        fun getInstance(): FinalDirectionApplication{
            return INSTANCE ?: throw IllegalArgumentException("앱 initialized 되지 않음")
        }
    }

}