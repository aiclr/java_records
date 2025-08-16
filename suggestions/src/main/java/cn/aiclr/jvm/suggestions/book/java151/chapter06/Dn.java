package cn.aiclr.jvm.suggestions.book.java151.chapter06;

/**
 * <pre>92.注意 {@link java.lang.Override} 不同版本的区别
 *
 * 在多环境部署应用时，需要考虑 {@link java.lang.Override} 在不同版本下代表的意义，
 * 如果是 Java 1.6 版本的程序移植到 1.5 版本环境中，
 * 需要删除实现接口方法上的 {@link java.lang.Override} 注解
 *
 * 注解 {@link java.lang.Override} 用于方法的覆写上，它在编译期有效，
 * 也就是 Java 编译器在编译时会根据该注解检查方法是否真的是覆写，如果不是就报错，拒绝编译。
 * 该注解可以很大程度地解决我们的误写问题，比如子类和父类的方法名少写了一个字符，或者是数字 0 和字母 O 未区分出来等，这基本上是每个程序员都曾经犯过的错误。
 * 在代码中加上 {@link java.lang.Override} 注解基本可以杜绝出现此类问题，但是 {@link java.lang.Override} 有个版本问题
 */
public class Dn {
}

interface FooDn {
    void doSomething();
}

class ImplDn implements FooDn {
    /**
     * <pre>这段代码在 Java 1.6 版本上编译没有任何问题
     * 虽然 doSomething 方法只是实现了接口定义，
     * 严格来说并不能算是覆写，但 @Override 出现在这里可以减少代码中可能出现的错误
     * 如果在 Java 1.5 版本上编译此段代码，就会出现如下错误
     * {@code The method doSomething() of type ImplDn must override a superclass method}
     * 这是个错误，不能继续编译。
     * 原因是 Java 1.5 版中 {@link java.lang.Override} 是严格遵守覆写的定义：
     *      子类方法与父类方法必须具有相同的方法名、输入参数、输出参数（允许子类缩小）、访问权限（允许子类扩大），
     *      父类必须是一个类，不能是一个接口，否则不能算是覆写
     */
    @Override
    public void doSomething() {

    }
}

