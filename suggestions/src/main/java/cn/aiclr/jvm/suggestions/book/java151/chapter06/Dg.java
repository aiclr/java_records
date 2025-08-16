package cn.aiclr.jvm.suggestions.book.java151.chapter06;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <pre>85.小心 switch 带来的空值异常
 *
 * assert 默认是不启用的 设置一下 jvm 的参数，参数是 {@code -enableassertions} 或者 {@code -ea}
 */
public class Dg {

    private static final Logger logger = LoggerFactory.getLogger(Dg.class);

    /**
     * <pre>目前 Java 中的 switch 语句只能判断
     * byte、short、char、int 类型（JDK 7 已经允许使用 String 类型），这是 Java 编译器的限制。
     * 枚举类型 编译时，编译器判断出 switch 语句后的参数是枚举类型，
     * 然后就会根据枚举的排序值继续匹配
     */
    public static void doSports(SeasonDg seasonDf) {
        //复习断言，不应该使用断言来控制业务
//        assert null != seasonDf : "枚举参数不能为null";
        //推荐
        if (null == seasonDf)
            throw new IllegalArgumentException("Unexpected value: null");
        switch (seasonDf) {
            case Spring -> logger.info("春天放风筝");
            case Summer -> logger.info("夏天游泳");
            case Autumn -> logger.info("秋天打猎");
            case Winter -> logger.info("冬天溜冰");
            default -> throw new IllegalArgumentException("Unexpected value: " + seasonDf);
        }
    }

    /**
     * <pre>doSports 方法内的 switch 与下面代码相同
     *
     * switch 语句是先计算 season 变量的排序值(声明顺序)，
     * 然后与枚举常量的每个排序值进行对比的。
     * 在我们的例子中 season 变量是null值，
     * 无法执行 ordinal 方法，于是报空指针异常
     */
    public static void doSportsOfOrdinal(SeasonDg seasonDf) {
        //复习断言，不应该使用断言来控制业务,断言可以用于调试
        assert null != seasonDf : "枚举参数不能为null";
        //推荐
//        if (null == seasonDf)
//            throw new IllegalArgumentException("Unexpected value: null");

        //null.ordinal()空指针
        switch (seasonDf.ordinal()) {
            case 0 -> logger.info("春天放风筝");
            case 1 -> logger.info("夏天游泳");
            case 2 -> logger.info("秋天打猎");
            case 3 -> logger.info("冬天溜冰");
            default -> throw new IllegalArgumentException("Unexpected value: " + seasonDf);
        }
    }

    public enum SeasonDg {
        Spring, Summer, Autumn, Winter;
    }
}
