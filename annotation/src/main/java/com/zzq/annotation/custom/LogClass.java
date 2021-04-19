package com.zzq.annotation.custom;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @interface:用来声明一个注解
 *
 * 作用：打印被注解的类的类名
 *
 *
 */

//运行时处理
@Retention(RetentionPolicy.RUNTIME)
//限定使用范围为「方法」
@Target(ElementType.METHOD)
public @interface LogClass {

    String className();
}
