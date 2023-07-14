package com.wssg.module.test.server

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import com.wssg.lib.api.server.ITestService
import com.wssg.module.test.TestMainActivity

/**
 * ...
 * @author RQ527 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2023/7/14
 * @Description:
 */
@Route(path = TEST_SERVICE, name = TEST_SERVICE)
class TestServerImpl: ITestService {
    override fun toTestPage(context: Context) {
        TestMainActivity.startActivity(context,"我传了个参数")
    }

    override fun getTestInfo():String {
        return "我是Test模块，我提供了一些test模块的信息给你。"
    }

    override fun init(context: Context?) {
    }
}