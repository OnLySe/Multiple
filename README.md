## 项目说明

各知识点使用demo，通过该demo可总结其使用手册。由于有多个module，先在这里留下公用部分配置、代码、样式等。等这部分代码数量增长到一定程度，就建个专用的common module。

所有项目全程使用Kotlin开发，需配置Kotlin环境

```groovy
implementation "org.jetbrains.kotlin:kotlin-stdlib:$kotlin_version"
implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlin_version"
//依赖当前平台所对应的平台库
implementation "org.jetbrains.kotlinx:kotlinx-coroutines-android:$kotlin_coroutines"
//依赖协程核心库
implementation "org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlin_coroutines"
```

配置在Java1.8环境下开发

```groovy
compileOptions {
    sourceCompatibility JavaVersion.VERSION_1_8
    targetCompatibility JavaVersion.VERSION_1_8
}

kotlinOptions {
    jvmTarget = "1.8"
}
```

启用DataBinding

```groovy
buildFeatures {
    dataBinding = true
}
```

## 创建module

新创建的module一律采用Kotlin语言开发！对于新创建的module，需要统一配置，避免此前出现的各module依赖同个依赖库不同版本的情况。大部分需要的依赖已经在common模块中加上去了，而在common模块的依赖采用的是api的方式，而不是传统的implementation，所以可以直接使用common中添加的依赖。需要做的仅仅只是添加common依赖。

```groovy
dependencies {
    implementation project(path: ':common')
}
```

