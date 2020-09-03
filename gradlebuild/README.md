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