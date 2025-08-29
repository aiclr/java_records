package cn.aiclr.jvm.designpattern.structural.proxy.proxydynamic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyFactory {

    private static final Logger log = LoggerFactory.getLogger(ProxyFactory.class);

    //被代理对象
    private Object target;

    public ProxyFactory(Object target) {
        this.target = target;
    }

    /**
     * <pre>给目标对象生成一个代理对象
     * {@code public static Object newProxyInstance(ClassLoader loader, Class<?>[] interfaces, InvocationHandler h)} 参数说明：
     *   1. ClassLoader loader：指定当前目标对象使用的类加载器，获取加载器的方法固定
     *   2. Class<?>[] interfaces：目标对象实现的接口类型使用泛型方法确认类型
     *   3. InvocationHandler h: 事件处理，执行目标对象的方法,会触发事情处理器方法,会把当前执行的目标对象方法作为参数传入
     */
    public Object getProxyInstance() {
        return Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
                        log.info("JDK 动态代理");
                        //通过反射机制调用目标对象方法
                        return method.invoke(target, objects);
                    }
                }
        );
    }

    public Object getProxyInstance1() {
        return Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                new InvocationHandlerImpl());
    }

    private class InvocationHandlerImpl implements InvocationHandler {

        /**
         * 通过反射机制调用目标对象方法
         *
         * @param proxy  the proxy instance that the method was invoked on
         * @param method the {@code Method} instance corresponding to
         *               the interface method invoked on the proxy instance.  The declaring
         *               class of the {@code Method} object will be the interface that
         *               the method was declared in, which may be a superinterface of the
         *               proxy interface that the proxy class inherits the method through.
         * @param args   an array of objects containing the values of the
         *               arguments passed in the method invocation on the proxy instance,
         *               or {@code null} if interface method takes no arguments.
         *               Arguments of primitive types are wrapped in instances of the
         *               appropriate primitive wrapper class, such as
         *               {@code java.lang.Integer} or {@code java.lang.Boolean}.
         * @return
         * @throws Throwable
         */
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            log.info("JDK 动态代理");
            return method.invoke(target, args);
        }
    }
}
