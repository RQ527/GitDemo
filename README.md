# 简单版多模块指南

## 模块化结构

app模块：构建所有模块的壳，依赖所有模块。

lib类模块：多为工具包，类似于三方库，可依赖lib模块，不能依赖module模块

module类模块：多为业务模块，具有单独运行的功能，可依赖lib模块，不能依赖module模块

## 步骤：

新建一个项目。

新建一个app_versions.gradle的gradle文件用于统一管理所有库的版本：

```groovy
ext{
    //是否开启单模块调试
    isDebug=false

    //sdk版本
    app_sdk = [
            compileSdk: 33,
            minSdk: 24,
            targetSdk: 33,
    ]

    //三方库版本
    versions =[
            arouter_version : "1.5.2",
            rxjava_version : "x.x.x"
    ]
}
```

然后在**根目录**下的build.gradle文件下引入该文件：

```groovy
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id 'com.android.application' version '8.0.2' apply false
    id 'com.android.library' version '8.0.2' apply false
    id 'org.jetbrains.kotlin.android' version '1.8.20' apply false
}
//引入版本管理文件
apply from:'app_versions.gradle'
```

## 新建module模块

![image-20230714233855507](https://rq527-1310352304.cos.ap-chongqing.myqcloud.com/image-20230714233855507.png)

![image-20230714234010142](https://rq527-1310352304.cos.ap-chongqing.myqcloud.com/image-20230714234010142.png)

新建module选Phone&Tablet，然后记得将包名里的下划线改为'.'

修改builde.gradle如下：

```groovy
plugins {
    id 'org.jetbrains.kotlin.android'
    id 'kotlin-kapt'//kapt插件，Arouter注解处理器需要
}
if (!isDebug){
    apply plugin:'com.android.library'
}else{
    apply plugin:'com.android.application'
}
android {
    namespace 'com.wssg.module.test'
    compileSdk app_sdk.compileSdk

    defaultConfig {
        if (isDebug){
            applicationId "com.wssg.module.test"
        }
        minSdk app_sdk.minSdk
        targetSdk app_sdk.targetSdk
        versionCode 1
        versionName "1.0"
    }

    buildTypes {
        release {
            minifyEnabled false
        }
    }
    compileOptions {
        //AGP8.0以下不需要java17。1.8或者11即可
        sourceCompatibility JavaVersion.VERSION_17
        targetCompatibility JavaVersion.VERSION_17
    }
    kotlinOptions {
        //AGP8.0以下不需要java17。1.8或者11即可
        jvmTarget = '17'
    }
    //下面这串代码用于单模块调试
    //源集 - 用来设定java目录或者资源目录
    sourceSets{
        main{
            if(isDebug){
                //组件化模式
                manifest.srcFile 'src/main/debug/AndroidManifest.xml'
                java.srcDirs = ['src/main/debug','src/main/java']
            }else{
                //集成模式
                manifest.srcFile 'src/main/AndroidManifest.xml'

                //排除掉debug包的信息
                java{
                    exclude 'src/main/debug'
                }
            }
        }
    }
}
//Arouter注解处理器选项
kapt{
    arguments{
        arg("AROUTER_MODULE_NAME",project.getName())
    }
}
dependencies {

    implementation 'androidx.core:core-ktx:1.8.0'
    implementation 'androidx.appcompat:appcompat:1.4.1'
    implementation 'com.google.android.material:material:1.5.0'
    implementation 'androidx.constraintlayout:constraintlayout:2.1.3'
    //依赖utlis工具模块
    implementation(project(":lib_utils"))
    //依赖api通信模块
    implementation(project(":lib_api"))
    //依赖Arouter注解处理器
    kapt "com.alibaba:arouter-compiler:$versions.arouter_version"
}
```

对于：

```groovy
plugins {
    id 'org.jetbrains.kotlin.android'
    id 'kotlin-kapt'//kapt插件，Arouter注解处理器需要
}
```

id 'org.jetbrains.kotlin.android'是kt在Android上的插件，所有模块都需要。

id 'kotlin-kapt'是kapt插件，所有使用了Arouter注解的模块都需要引入这个插件，并且同时还要声明kapt选项：

```groovy
kapt{
    arguments{
        arg("AROUTER_MODULE_NAME",project.getName())
    }
}
```

还要加入注解处理器依赖：

```groovy
kapt "com.alibaba:arouter-compiler:$versions.arouter_version"
```



```groovy
if (!isDebug){
    apply plugin:'com.android.library'
}else{
    apply plugin:'com.android.application'
}
```

apply plugin:'com.android.library'表明此模块为library模块，作为一个库给app模块依赖，非debug状态关闭。

apply plugin:'com.android.application'表明此模块为application模块，作为一个具有单独运行功能的模块。debug状态开启

对于：

```groovy
//下面这串代码用于单模块调试
    //源集 - 用来设定java目录或者资源目录
    sourceSets{
        main{
            if(isDebug){
                //组件化模式
                manifest.srcFile 'src/main/debug/AndroidManifest.xml'
                java.srcDirs = ['src/main/debug','src/main/java']
            }else{
                //集成模式
                manifest.srcFile 'src/main/AndroidManifest.xml'

                //排除掉debug包的信息
                java{
                    exclude 'src/main/debug'
                }
            }
        }
    }
```

用来设定java文件目录或者资源文件目录，manifest.srcFile声明注册文件的路径，运行时会寻找这个路径下的注册文件，java.srcDirs声明java/kotlin文件目录。之后在debug路径下新建你的DebugActivity并且在debug下的注册文件声明就行了： 

![image-20230714234757660](https://rq527-1310352304.cos.ap-chongqing.myqcloud.com/image-20230714234757660.png)

将你的isDebug设为true，再在AS选择调试的模块：

![image-20230714225439801](https://rq527-1310352304.cos.ap-chongqing.myqcloud.com/image-20230714225439801.png)

点击运行就会进入你的DebugActivity了，如项目所示。

因为我删除了Test依赖：

```groovy
testImplementation 'junit:junit:4.13.2'
androidTestImplementation 'androidx.test.ext:junit:1.1.3'
androidTestImplementation 'androidx.test.espresso:espresso-core:3.4.0'
```

因此你的这两个文件夹可以直接删除：

![image-20230714225835341](https://rq527-1310352304.cos.ap-chongqing.myqcloud.com/image-20230714225835341.png)

直接在文件夹里删除即可。

## 新建lib模块

![image-20230714234333856](https://rq527-1310352304.cos.ap-chongqing.myqcloud.com/image-20230714234333856.png)

lib选Android library，同样将包名中的下划线改为'.'。

新建lib模块和module差不多，比如我这里新建一个lib_api模块用于中转模块之间的通信，修改build.gradle如下：

```groovy
plugins {
    id 'com.android.library'
    id 'org.jetbrains.kotlin.android'
    id 'kotlin-kapt'
}

android {
    namespace 'com.wssg.lib.api'
    compileSdk app_sdk.compileSdk

    defaultConfig {
        minSdk app_sdk.minSdk
        targetSdk app_sdk.targetSdk
    }

    buildTypes {
        release {
            minifyEnabled false

        }
    }
    compileOptions {
        sourceCompatibility JavaVersion.VERSION_17
        targetCompatibility JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = '17'
    }
}
kapt{
    arguments{
        arg("AROUTER_MODULE_NAME",project.getName())
    }
}
dependencies {

    implementation 'androidx.core:core-ktx:1.8.0'
    implementation 'androidx.appcompat:appcompat:1.4.1'
    implementation 'com.google.android.material:material:1.5.0'

    //引入Arouter依赖
    api "com.alibaba:arouter-api:$versions.arouter_version"
    kapt "com.alibaba:arouter-compiler:$versions.arouter_version"
}
```

和module差不多，少了debug状态的判断，lib是不具有单模块调试功能的。

## App模块：

```groovy
plugins {
    id 'com.android.application'
    id 'org.jetbrains.kotlin.android'
}

android {
    namespace 'com.wssg.gitdemo'
    compileSdk app_sdk.compileSdk

    defaultConfig {
        applicationId "com.wssg.gitdemo"
        minSdk app_sdk.minSdk
        targetSdk app_sdk.targetSdk
        versionCode 1
        versionName "1.0"
    }

    buildTypes {
        release {
            minifyEnabled false
        }
    }
    compileOptions {
        sourceCompatibility JavaVersion.VERSION_17
        targetCompatibility JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = '17'
    }
}
dependencies {

    implementation 'androidx.core:core-ktx:1.8.0'
    implementation 'androidx.appcompat:appcompat:1.4.1'
    implementation 'com.google.android.material:material:1.5.0'
    implementation 'androidx.constraintlayout:constraintlayout:2.1.3'
    //在这里引入所有的模块
    implementation(project(":lib_utils"))
    implementation(project(":lib_api"))
    implementation(project(":module_test"))
    implementation(project(":module_test2"))
}
```

## **Arouter的坑：**

光使用Arouter只需要导入：

```groovy
api "com.alibaba:arouter-api:$versions.arouter_version"
```

这里使用api是依赖传递，所有依赖此模块的模块可共享这个依赖。



如果你要使用Arouter的注解必须引入kapt和Arouter的注解处理器，即在使用Arouter注解的模块引入kapt插件：

```groovy
id 'kotlin-kapt'
```

并将当前模块作为kapt可选项：

```groovy
kapt{
    arguments{
        arg("AROUTER_MODULE_NAME",project.getName())
    }
}
```

然后引入Arouter的注解处理器：

```groovy
//依赖Arouter注解处理器
kapt "com.alibaba:arouter-compiler:$versions.arouter_version"
```

这里只需要依赖Arouter的注解处理器，Arouter就不用依赖了，因为在lib_api模块已经依赖了，api将依赖传递了。

