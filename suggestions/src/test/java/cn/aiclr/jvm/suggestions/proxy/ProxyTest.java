package cn.aiclr.jvm.suggestions.proxy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Proxy;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

class ProxyTest {

    private static final Logger logger = Logger.getLogger(ProxyTest.class.getName());

    private static final SimpleFormatter simpleFormatter = new SimpleFormatter();

    @BeforeAll
    static void init() {
        Handler fh;
        try {
            fh = new FileHandler("%t/wombat.log", true);
            fh.setFormatter(simpleFormatter);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        logger.addHandler(fh);
        logger.setLevel(Level.FINEST);
    }

    @Test
    @DisplayName("自定义 Proxy 实现简单环绕代理")
    void testAroundProxy() {
        logger.fine("自定义 Proxy 实现简单环绕代理");
        Foo foo = (Foo) DebugProxy.newInstance(new FooImpl());
        Assertions.assertEquals("2333", foo.bar(null));
    }

    /**
     * SEVERE (highest value)
     * WARNING
     * INFO
     * CONFIG
     * FINE
     * FINER
     * FINEST (lowest value)
     */
    @Test
    @DisplayName("自定义 InvocationHandler 处理 hashCode、equals、toString方法")
    void testProxy() {

        logger.log(Level.SEVERE, "hashCode、equals、toString proxy");
        logger.log(Level.WARNING, "hashCode、equals、toString proxy");
        logger.log(Level.INFO, "hashCode、equals、toString proxy");
        logger.log(Level.CONFIG, "hashCode、equals、toString proxy");
        logger.log(Level.FINE, "hashCode、equals、toString proxy");
        logger.log(Level.FINER, "hashCode、equals、toString proxy");
        logger.log(Level.FINEST, "hashCode、equals、toString proxy");

        Foo foo = new FooImpl();

        Class<?>[] proxyInterface = new Class[]{Foo.class};
        Foo foo2 = (Foo) Proxy.newProxyInstance(Foo.class.getClassLoader(), proxyInterface, new Delegator(proxyInterface, new Object[]{new FooImpl()}));

        logger.info(foo2.toString());

        logger.info(String.valueOf(foo2.equals(foo)));

        logger.info(String.valueOf(foo2.hashCode()));

        Assertions.assertEquals("2333", foo2.bar(null));
    }

}