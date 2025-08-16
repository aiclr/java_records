package cn.aiclr.jvm.suggestions.book.java151.chapter07.ec;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;

/**
 * 将能力赋予 Animal 实例。装饰主角
 */
public class DecorateAnimal implements Animal {

    //被包装实例
    private final Animal target;

    //使用的包装器 Class 对象
    private final Class<? extends Feature> featureClass;

    public DecorateAnimal(Animal target, Class<? extends Feature> featureClass) {
        this.target = target;
        this.featureClass = featureClass;
    }

    /**
     * <pre>一个装饰类型必然是抽象构建（Component）的子类型，
     * 它必须要实现 doStuff，此处的 doStuff 方法委托给了动态代理执行，
     * 并且在动态代理的控制器 jdkDynamicProxy 中还设置了决定装饰方式和行为的条件（即代码中 InvocationHandler lambda 中的 if 判断语句），
     * 当然，此处也可以通过读取持久化数据的方式进行判断
     */
    @Override
    public void doStuff() {
        InvocationHandler jdkDynamicProxy = (proxy, method, args) -> {
            Object result = null;
            if (Modifier.isPublic(method.getModifiers())) {
                result = method.invoke(featureClass.getConstructor().newInstance(), args);
            }
            target.doStuff();
            return result;
        };

        ClassLoader classLoader = featureClass.getClassLoader();
        //动态代理，由代理觉得 赋予 target 何种 能力
        Feature proxyInstance = (Feature) Proxy.newProxyInstance(classLoader, featureClass.getInterfaces(), jdkDynamicProxy);

        proxyInstance.load();
    }
}
