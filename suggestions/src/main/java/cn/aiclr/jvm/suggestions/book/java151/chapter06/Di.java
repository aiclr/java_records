package cn.aiclr.jvm.suggestions.book.java151.chapter06;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * <pre>87.使用 {@link java.lang.Enum#valueOf(Class, String)} 前必须进行校验
 *
 * 每个枚举都是java.lang.Enum的子类
 * 都可以访问Enum类提供的方法，
 * 比如hashCode、name、valueOf等，
 * 其中valueOf方法会把一个String类型的名称转变为枚举项，
 * 也就是在枚举项中查找出字面值与该参数相等的枚举项。
 * 虽然这个方法很简单，但是JDK却做了一个对于开发人员来说并不简单的处理
 */
public class Di {

    private static final Logger logger = LoggerFactory.getLogger(Di.class);

    /**
     * <pre>抛出异常（不符合正常逻辑，不能转换就不转，一旦抛出这个异常，后续的代码就不会运行了）
     *
     * {@link java.lang.IllegalArgumentException}
     *
     * {@link java.lang.Enum#valueOf(Class, String)} 方法是不可见的，是 JVM 内置的方法
     * {@link java.lang.Enum#valueOf(Class, String)} 方法先通过反射从枚举类的常量声明中查找，
     * 若找到就直接返回，
     * 若找不到则抛出 {@link java.lang.IllegalArgumentException}。
     * {@link java.lang.Enum#valueOf(Class, String)} 本意是保护编码中的枚举安全性，使其不产生空枚举对象，简化枚举操作，
     * 但是却又引入了一个我们无法避免的 {@link java.lang.IllegalArgumentException} 异常
     *
     * 解决方案：
     * 1.使用 try-catch 捕捉异常
     * 2.扩展枚举类
     */
    public static void searchException(List<String> params) {
        //summer小写，枚举是首字母大写Summer
        for (String name : params) {
            SeasonDi season = SeasonDi.valueOf(name);
            if (null != season) {
                logger.info("{}", season);
            } else
                logger.error("无相关枚举项");
        }
    }

    /**
     * 扩展枚举类
     */
    public static void searchEnumExpand(List<String> params) {
        //扩展枚举类
        for (String name : params) {
            if (SeasonDi.contains(name)) {
                SeasonDi season = SeasonDi.valueOf(name);
                logger.info("{}", season);
            }
        }
    }

    /**
     * try-catch 处理异常
     */
    public static void search(List<String> params) {
        for (String name : params) {
            SeasonDi season = null;
            try {
                season = SeasonDi.valueOf(name);
            } catch (IllegalArgumentException e) {
                logger.error("{}", e.getMessage(), e);
            }

            if (null != season) {
                logger.info("{}", season);
            } else
                logger.info("无对应枚举项");
        }
    }

    enum SeasonDi {
        Spring, Summer, Autumn, Winter;

        /**
         * 扩展枚举类
         */
        public static boolean contains(String name) {
            SeasonDi[] seasonDis = values();
            for (SeasonDi s : seasonDis) {
                if (s.name().equals(name)) {
                    return true;
                }
            }
            return false;
        }
    }
}
