package com.wssg.gitdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.wssg.lib.api.ServiceManager
import com.wssg.lib.api.impl
import com.wssg.lib.api.server.ITestService

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.btn_test).setOnClickListener {
            //使用服务提供跳转
            ServiceManager(ITestService::class).toTestPage(this)
        }
        findViewById<Button>(R.id.btn_getTestInfo).setOnClickListener {
            //使用服务的另外一种写法
            findViewById<TextView>(R.id.textView).text = ITestService::class.impl.getTestInfo()
        }
    }
}