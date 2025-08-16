package cn.aiclr.jvm.suggestions.book.java151.chapter01;


import java.util.Date;

/**
 * <pre>18.避免 instanceof 非预期结果
 *      instanceof 是一个简单的二元操作符，它是用来判断一个对象是否是一个类实例
 *      其操作类似于 >= , == 非常简单
 */
public class Ar<T> {
    public boolean isDateInstance(T t) {
        return t instanceof Date;
    }
}

