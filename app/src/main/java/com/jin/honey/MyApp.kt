package com.jin.honey

import android.app.Application
import com.google.firebase.FirebaseApp
import com.naver.maps.map.NaverMapSdk

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        NaverMapSdk.getInstance(this).client = NaverMapSdk.NcpKeyClient(BuildConfig.NAVER_MAP_CLIENT_ID)
    }
}
