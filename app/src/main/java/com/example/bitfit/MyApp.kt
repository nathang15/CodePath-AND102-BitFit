package com.example.bitfit

import android.app.Application
import com.example.bitfit.AppDatabase

class MyApplication : Application() {
    val db by lazy { AppDatabase.getInstance(this) }
}