package cn.aiclr.jvm.suggestions.book.java151.chapter07.ed;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <pre>抽象模板类
 * 它负责数据初始化，
 * 但是具体要初始化哪些数据则是由基本方法决定，
 * 基本方法是一抽象方法，子类必须实现
 */
public abstract class AbsPopulator {

    protected static final Logger logger = LoggerFactory.getLogger(AbsPopulator.class);

    /**
     * 模板方法
     */
    public final void dataInitialing() {
        //调用基本方法
        initUser();
        initPWD();
        initJobs();
    }

    /**
     * 基本方法
     */
    protected abstract void initUser();

    /**
     * 基本方法
     */
    protected abstract void initPWD();

    /**
     * 基本方法
     */
    protected abstract void initJobs();
}
