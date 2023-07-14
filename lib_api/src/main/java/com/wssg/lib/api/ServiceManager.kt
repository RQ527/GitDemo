package com.wssg.lib.api

import com.alibaba.android.arouter.facade.template.IProvider
import com.alibaba.android.arouter.launcher.ARouter
import kotlin.reflect.KClass

/**
 * ...
 * @author RQ527 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2023/7/14
 * @Description:
 */

/**
 * 对服务获取的封装，便于以后修改为其他依赖注入的框架，建议都通过该文件提供的方法获取服务，
 * 不采用 @Autowired 的方式，便于以后更换实现。
 * 使用方法：
 *     1. 在service包中创建对应的服务接口并继承IProvider接口，命名请加上代表接口的I前缀和Service后缀，例如IAccountService；
 *     2. 创建该接口的实现类，命名尽量只去掉I即可，然后加上路由注解，路由地址统一写到RoutingTable中，例如AccountService；
 *     3. 通过ServiceManager的方式获取实现类。
 */
object ServiceManager {

    /**
     * 写法：
     * ```
     * ServiceManger(IAccountService::class)
     *   .isLogin()
     * ```
     * 还有更简单的写法：
     * ```
     * IAccountService::class.impl
     *   .isLogin()
     * ```
     */
    operator fun <T : Any> invoke(serviceClass: KClass<T>): T {
        return ARouter.getInstance().navigation(serviceClass.java)
    }

    /**
     * 写法：
     * ```
     * ServiceManger<IAccountService>(ACCOUNT_SERVICE)
     *   .isLogin()
     * ```
     */
    @Suppress("UNCHECKED_CAST")
    operator fun <T : Any> invoke(servicePath: String): T {
        return ARouter.getInstance().build(servicePath).navigation() as T
    }

}

/**
 * 写法：
 * ```
 * IAccountService::class.impl
 *   .isLogin()
 * ```
 */
val <T: IProvider> KClass<T>.impl: T
    get() = ServiceManager(this)