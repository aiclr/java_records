package cn.aiclr.jvm.suggestions.book.java151.chapter07.eb;

/**
 * 静态代理
 */
public class StaticProxy implements Subject {

    //被代理实例对象
    private Subject target = null;

    /**
     * 默认被代理对象
     */
    public StaticProxy() {
        target = new RealSubject();
    }

    /**
     * 构造器接收被代理对象
     *
     * @param target 被代理实例对象
     */
    public StaticProxy(Subject target) {
        this.target = target;
    }

    private void before() {
        logger.info("StaticProxy before ...");
    }

    private void after() {
        logger.info("StaticProxy after ...");
    }

    @Override
    public void request() {
        before();
        target.request();
        after();
    }
}
