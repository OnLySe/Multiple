## RxJava使用

由于RxJava目前有三个版本，RxJava、RxJava2、RxJava3，第一个版本已经不是主流，主要还是在使用后面两个版本，所以不对第一个版本做额外的使用记录。

由于RxJava2与RxJava3有一定的兼容，但用法又有所区分，平时在项目中也不会一起用，所以在这里分开，单独使用。在build.gradle中添加配置：

```groovy
flavorDimensions "rxJavaVersion"

productFlavors {

    //TODO 渠道名不要用大写开头！使用多渠道依赖会有问题
    rxJava2 {
        dimension "rxJavaVersion"
        //applicationIdSuffix值应以“."为开头
        applicationIdSuffix ".v2"
    }

    rxJava3 {
        dimension "rxJavaVersion"
        applicationIdSuffix ".v3"
    }
}
```

即添加两个产品变体。后续会为这两个变体单独添加源码集，并在依赖中予以区分。在开发过程中，在对应的源码集中开发。在使用和查看界面效果时，只需要在Build Variants窗口中切换构建变体就可以了

```groovy
rxJava2Implementation 'io.reactivex.rxjava2:rxjava:2.1.1'
rxJava2Implementation 'io.reactivex.rxjava2:rxandroid:2.0.1'

rxJava3Implementation 'io.reactivex.rxjava3:rxjava:3.0.0'
//RxAndroid的依赖包
rxJava3Implementation 'io.reactivex.rxjava3:rxandroid:3.0.0'
```

