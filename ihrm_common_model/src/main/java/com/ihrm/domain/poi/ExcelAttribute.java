package com.ihrm.domain.poi;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解
 */
@Retention(RetentionPolicy.RUNTIME)  //注解不仅被保存到class文件中，jvm加载class文件之后，仍然存在
@Target(ElementType.FIELD) //作用在属性上注解
public @interface ExcelAttribute {
    String name() default "";  //对应的列名称

    int sort();  //excel列的索引

    String format() default "";  //字段类型对应的格式
}
