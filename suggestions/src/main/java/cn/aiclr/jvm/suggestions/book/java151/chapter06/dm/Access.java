package cn.aiclr.jvm.suggestions.book.java151.chapter06.dm;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 定义权限级别注解
 * 把资源和权限级别关联起来
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Access {
    CommonIdentifier level() default CommonIdentifier.Reader;
}
