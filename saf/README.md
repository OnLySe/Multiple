## 概述

SAF，全程为：[Storage Access Framework](https://developer.android.com/guide/topics/providers/document-provider)，本module主要完成对各系统版本中存储部分功能实现，比如Android Q中实现分区功能。在此module中，默认已经将文件和数据存储在外部存储中的私有目录中。

当然，对于根目录等其他文件夹下的读写也是有这个可能会需要，所以会在module中一一实现。对于存储部分的变更，Android Q中变化较大。

## 测试结果

### 读取



### 写入

在commitId为[cb663d35fe38a62f7f78b30cf42d63ba0181d011](https://gitee.com/fmzq/Multiple/commit/cb663d35fe38a62f7f78b30cf42d63ba0181d011)下，已经初步实现在根目录下创建文件，目前分别在两个手机型号中完成测试

| 手机型号 | 系统版本    | 结果                                   |
| -------- | ----------- | -------------------------------------- |
| 三星S10  | Android 10  | 需要系统确认授予权限后，才会创建文件夹 |
| 一加5    | Android 8.1 | 不需要授予权限，可直接创建文件夹       |

## 注意

在module中单独添加了`DocumentFile`的依赖：

```groovy
implementation "androidx.documentfile:documentfile:1.0.1"
```

在涉及到Android Q系统中，写入文件到根目录中会有所需要。