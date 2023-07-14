package com.wssg.module.test2

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class Test2MainActivity : AppCompatActivity() {
    companion object{
        fun startActivity(context: Context){
            context.startActivity(Intent(context,Test2MainActivity::class.java))
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test2_main)
    }
}