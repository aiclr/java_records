package cn.aiclr.jvm.suggestions.book.java151.chapter07.eb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * JDK 动态代理
 * Java还提供了 {@link java.lang.reflect.Proxy} 用于实现动态代理：
 * 只要提供一个抽象主题角色和具体主题角色，就可以动态实现其逻辑的
 */
public class JdkDynamicProxy implements InvocationHandler {

    private static final Logger logger = LoggerFactory.getLogger(JdkDynamicProxy.class);

    //被代理实例对象
    private final Subject target;

    public JdkDynamicProxy(Subject target) {
        this.target = target;
    }

    private void before() {
        logger.info("StaticProxy before ...");

    }

    private void after() {
        logger.info("StaticProxy after ...");

    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        Object result = method.invoke(target, args);
        after();
        return result;
    }
}
