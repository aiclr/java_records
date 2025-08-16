package cn.aiclr.jvm.suggestions.book.java151.chapter07.ed;

/**
 * <pre>系统在启动时，查找所有的 {@link AbsPopulator} 实现类，
 * 然后 {@link AbsPopulator#dataInitialing()} 实现数据的初始化。
 * 如果是使用 Spring 作为 IoC 容器的项目，
 * 直接在 {@link AbsPopulator#dataInitialing()} 方法上加上 @PostConstruct 注解，
 * Spring 容器启动完毕后会自动运行 {@link AbsPopulator#dataInitialing()} 方法
 *
 *
 * 初始化一张 User 表需要非常多的操作，
 * 比如先建表，然后筛选数据，之后插入，最后校验，
 * 如果把这些都放到一个 {@link AbsPopulator#dataInitialing()} 方法里会非常庞大（即使提炼出多个方法承担不同的职责，代码的可读性依然很差）
 */
public class UserPopulator extends AbsPopulator {

    @Override
    public void initUser() {
        logger.info("init user ...");
    }

    @Override
    public void initPWD() {
        logger.info("init pwd ...");
    }

    @Override
    public void initJobs() {
        logger.info("init jobs ...");
    }
}
