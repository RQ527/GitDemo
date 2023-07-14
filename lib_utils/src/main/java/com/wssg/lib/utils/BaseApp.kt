package com.wssg.lib.utils

import android.app.Application

/**
 * ...
 * @author RQ527 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2023/7/14
 * @Description:
 */
open class BaseApp:Application() {
    companion object {
        lateinit var mContext: BaseApp
    }

    override fun onCreate() {
        super.onCreate()
        mContext=this
    }
}