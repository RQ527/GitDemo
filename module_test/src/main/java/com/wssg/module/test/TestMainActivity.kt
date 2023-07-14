package com.wssg.module.test

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.wssg.lib.api.ServiceManager
import com.wssg.lib.api.server.ITest2Service
import com.wssg.lib.utils.toast

@Route(path = "/test/entry")
class TestMainActivity : AppCompatActivity() {
    companion object {
        fun startActivity(context: Context, args: String) {
            context.startActivity(
                Intent(
                    context,
                    TestMainActivity::class.java
                ).apply { putExtra("我是参数", args) })
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.test_activity_main)
        toast("你进入了Test界面！")
        findViewById<Button>(R.id.button).setOnClickListener {
            ServiceManager(ITest2Service::class).toTest2Page(this)
        }
    }
}