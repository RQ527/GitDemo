package com.wssg.lib.api.server

import android.content.Context
import com.alibaba.android.arouter.facade.template.IProvider

/**
 * ...
 * @author RQ527 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2023/7/14
 * @Description:
 */
interface ITestService:IProvider {
    fun toTestPage(context: Context)
    fun getTestInfo():String
}