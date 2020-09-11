## 创建密钥

密钥创建和配置，关键命令行：

>  ```
> 格式：
> keytool -genkey -alias <alias名称> -keypass <alias密码> -keyalg RSA -keysize 2048 -validity 36500 -keystore <keystore路径> -storepass <keystore密码>
> 
> keytool -importkeystore -srckeystore <kestore文件> -destkeystore <kestore文件> -deststoretype pkcs12
>  ```

举例：

```
命令行>keytool -genkey -alias zzqAlias -keypass zzq123456 -keyalg RSA -keysize 2048 -validity 36500 -keystore release.jks -storepass zzq123456
您的名字与姓氏是什么?
  [Unknown]:  zzq
您的组织单位名称是什么?
  [Unknown]:  zzq
您的组织名称是什么?
  [Unknown]:  zzq
您所在的城市或区域名称是什么?
  [Unknown]:  shenzhen
您所在的省/市/自治区名称是什么?
  [Unknown]:  guangdong
该单位的双字母国家/地区代码是什么?
  [Unknown]:
CN=zzq, OU=zzq, O=zzq, L=shenzhen, ST=guangdong, C=Unknown是否正确?
  [否]:  y


Warning:
JKS 密钥库使用专用格式。建议使用 "keytool -importkeystore -srckeystore release.jks -destkeystore release.jks -deststoretype pkcs12" 迁移到行业标准格式 PKCS12。

```

创建后，会有警告，要求JKS秘钥库迁移到行业标准格式PKCS12，并给出了命令进行迁移：

```
命令行>keytool -importkeystore -srckeystore release.jks -destkeystore release.jks -deststoretype pkcs12
输入源密钥库口令:
已成功导入别名 zzqalias 的条目。
已完成导入命令: 1 个条目成功导入, 0 个条目失败或取消

Warning:
已将 "release.jks" 迁移到 Non JKS/JCEKS。将 JKS 密钥库作为 "release.jks.old" 进行了备份。

```

在输入完源密钥库口令后，按回车，就会完成迁移了，完成迁移后，在release.jks所在目录下会生成release.jks.old文件

## 输出

### APP页面输出

为方便测试，APP会在页面将配置信息打印并显示出来

### 记录

在配置产品变种并同步完成后，会与构建类型合并生成多个构建变种。自定义变种后，系统将不再提供默认debug和release变种

```groovy
//命名变体维度。所有变种都必须属于一个指定的变种维度，即一个产品变种组
flavorDimensions "version"

//配置产品变种
productFlavors  {

    official {
        //正式版本
        dimension "version"
        applicationIdSuffix ".official"
        //            versionNamePrefix "official"
    }

    zzqTest {
        //个人测试版本
        dimension "version"
        applicationIdSuffix ".zzqtest"
        //            versionNamePrefix "zzqTest"
    }
}

buildTypes {
    release {
        minifyEnabled true
        proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
    }

    debug {
        minifyEnabled false
        debuggable true
        applicationIdSuffix ".debug"
    }

    releaseToTest{
        initWith release
        applicationIdSuffix ".test.release"
    }
}
```

Android Studio提供了Build Variants工具，在其窗口下，指定的Active Build Variant可设置为officialDebug，那么通过Run，安装到手机的安装包就是official渠道下的debug构建类型。如下：

![image-20200903143913883](https://zzq-markdown.oss-cn-shenzhen.aliyuncs.com/image/20200903144010.png)