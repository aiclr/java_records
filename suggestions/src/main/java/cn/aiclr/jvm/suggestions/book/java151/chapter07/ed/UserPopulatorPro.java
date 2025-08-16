package cn.aiclr.jvm.suggestions.book.java151.chapter07.ed;

/**
 * <pre> UserPopulatorPro 类中的方法只要符合基本方法鉴别器条件即会被模板方法调用，{@link AbsPopulatorPro#isInitDataMethod(java.lang.reflect.Method)}
 * 方法的数量也不再受父类的约束，
 * 实现了子类灵活定义基本方法、父类批量调用的功能，并且缩减了子类的代码量
 */
public class UserPopulatorPro extends AbsPopulatorPro {

    public void initUser() {
        logger.info("init user ...");
    }

    public void initPWD() {
        logger.info("init pwd ...");
    }

    public void initJobs() {
        logger.info("init jobs ...");
    }

    public void initSth() {
        logger.info("init sth ...");
    }
}
