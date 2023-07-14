package com.wssg.module.test2.server

import android.content.Context
import android.content.Intent
import com.alibaba.android.arouter.facade.annotation.Route
import com.wssg.lib.api.server.ITest2Service
import com.wssg.module.test2.Test2MainActivity

/**
 * ...
 * @author RQ527 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2023/7/14
 * @Description:
 */
@Route(path = TEST_SERVICE2)
class Test2ServerImpl:ITest2Service {
    override fun toTest2Page(context: Context) {
        context.startActivity(Intent(context,Test2MainActivity::class.java))
    }

    override fun init(context: Context?) {
    }
}