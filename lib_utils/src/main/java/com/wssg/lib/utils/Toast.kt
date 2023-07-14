package com.wssg.lib.utils

import android.widget.Toast

/**
 * ...
 * @author RQ527 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2023/7/14
 * @Description:
 */
fun toast(s:CharSequence){
    Toast.makeText(BaseApp.mContext,s,Toast.LENGTH_LONG).show()
}