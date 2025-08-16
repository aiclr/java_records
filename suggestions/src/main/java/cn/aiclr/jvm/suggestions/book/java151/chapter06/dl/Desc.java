package cn.aiclr.jvm.suggestions.book.java151.chapter06.dl;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 定义一个颜色注解
 */
@Retention(RetentionPolicy.RUNTIME)//表示该注解的保留级别
@Target(ElementType.TYPE)//表示该注解可以标注在什么位置
@Inherited//表示该注解会被自动继承
public @interface Desc {
    enum Color {
        White, Grayish, Yellow;
    }

    Color color() default Color.White;
}
