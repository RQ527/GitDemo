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
interface ITest2Service:IProvider {
    fun toTest2Page(context: Context)
}