package cn.aiclr.jvm.suggestions.book.java151.chapter07;

import cn.aiclr.jvm.suggestions.book.java151.chapter07.eb.JdkDynamicProxy;
import cn.aiclr.jvm.suggestions.book.java151.chapter07.eb.RealSubject;
import cn.aiclr.jvm.suggestions.book.java151.chapter07.eb.StaticProxy;
import cn.aiclr.jvm.suggestions.book.java151.chapter07.eb.Subject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

class EbTest {

    @Test
    @DisplayName("静态代理")
    void staticProxy() {
        //静态代理类
        StaticProxy staticProxy = new StaticProxy();
        //执行代理类方法
        staticProxy.request();
    }

    /**
     * <pre>动态代理
     * 实现了不用显式创建代理类即实现代理的功能，
     * 例如可以在被代理角色执行前进行权限判断，或者执行后进行数据校验
     *
     * 动态代理很容易实现通用的代理类，
     * 只要在 InvocationHandler 的 invoke 方法中读取持久化数据即可实现，而且还能实现动态切入的效果，
     * 这也是 AOP（Aspect Oriented Programming）编程理念
     */
    @Test
    @DisplayName("动态代理")
    void dynamicProxy() {
        //被代理对象
        RealSubject realSubject = new RealSubject();

        //JDK 动态代理对象
        InvocationHandler jdkDynamicProxy = new JdkDynamicProxy(realSubject);

        //被代理类类加载器
        ClassLoader classLoader = realSubject.getClass().getClassLoader();

        //动态创建 代理对象
        Subject proxyInstance = (Subject) Proxy.newProxyInstance(classLoader, realSubject.getClass().getInterfaces(), jdkDynamicProxy);

        //代理对象 执行方法
        proxyInstance.request();

    }
}
