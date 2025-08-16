package cn.aiclr.jvm.suggestions.book.java151.chapter06;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

/**
 * <pre>83.推荐使用枚举定义常量
 *
 * 注意
 * 在项目开发中，推荐使用枚举常量代替接口常量或类常量
 *
 * {@link java.lang.Enum} 和 {@link java.lang.annotation.Annotation} 都是在 Java 1.5 中引入的
 * {@link java.lang.Enum} 改变了常量的声明方式，
 * {@link java.lang.annotation.Annotation} 耦合了数据和代码。本质上是元数据（metadata），即“描述数据的数据”。它不是代码的逻辑部分，而是用来描述代码的行为或结构。
 *
 * Java 1.5 之前，只有两种方式的声明：类常量和接口常量，
 * 若在项目中使用的是 Java 1.5 之前的版本基本上都是如此定义的。
 * 在 Java 1.5 版及以后有了改进，新增一种常量声明方式：枚举声明常量
 *
 * 1.枚举常量更简单
 *      对比 {@link SeasonDe} 枚举和 {@link SeasonDeOfI}接口常量
 *      枚举常量只需要定义每个枚举项，不需要定义枚举值，
 *      而接口常量（或类常量）则必须定义值，否则编译通不过，即使我们不需要关注其值是多少也必须定义；
 *      其次，虽然两者被引用的方式相同（都是“类名．属性"，如 Season.Spring），
 *      但是枚举表示的是一个枚举项，字面含义是春天，
 *      而接口常量却是一个 int 类型，虽然其字面含义也是春天，但在运算中我们势必要关注其 int 值
 * 2.枚举常量属于稳态型
 *      {@code public void describe(int s,SeasonDe seasonDe)}
 *      在编译期间限定类型，不允许发生越界的情况
 * 3.枚举具有内置方法
 *      列出所有的季节常量
 *      接口常量或类常量可以通过反射来实现
 *      每个枚举都是 {@link java.lang.Enum} 的子类，
 *      该基类提供了诸如获得排序值的 {@link java.lang.Enum#ordinal()} 方法、{@link java.lang.Enum#compareTo(Enum)} 比较方法等，大大简化了常量的访问
 * 4.枚举可以自定义方法
 *      这一点似乎并不是枚举的优点，类常量也可以有自己的方法
 *      但关键是枚举常量不仅可以定义静态方法，
 *      还可以定义非静态方法，
 *      而且还能够从根本上杜绝常量类被实例化
 *
 * 虽然枚举常量在很多方面比接口常量和类常量好用，但是有一点它是比不上接口常量和类常量的，
 * 那就是继承，枚举类型是不能有继承的，也就是说一个枚举常量定义完毕后，除非修改重构，否则无法做扩展，
 * 而接口常量和类常量则可以通过继承进行扩展。
 * 但是，一般常量在项目构建时就定义完毕了，很少会出现必须通过扩展才能实现业务逻辑的场景
 */
public class De {

    private static final Logger logger = LoggerFactory.getLogger(De.class);

    /**
     * <pre>使用接口常量
     * 如果常量非常庞大，校验输入就成了一件非常麻烦的事情，但这是一个不可逃避的过程，
     * 特别是如果我们的校验条件不严格，虽然编译照样可以通过，但是运行期就会产生无法预知的后果
     */
    public static void describe(int s) {
        if (s >= 0 && s < 4) {
            switch (s) {
                case SeasonDeOfI.Summer:
                    logger.info("{} is very hot", SeasonDeOfI.Summer);
                    break;
                case SeasonDeOfI.Winter:
                    logger.info("{} is very cold", SeasonDeOfI.Winter);
                    break;
            }
        }
    }

    /**
     * 使用枚举
     *
     * @param season {@link SeasonDe}
     */
    public static void describe(SeasonDe season) {
        switch (season) {
            case Summer -> logger.info("{} is very hot", SeasonDe.Summer);
            case Winter -> logger.info("{} is very cold", SeasonDe.Winter);
        }
    }

    /**
     * <pre>JLS（JavaLanguage Specification，Java语言规范）提倡
     * 枚举项全部大写，字母之间用下划线分隔，
     * 这也是从常量的角度考虑的（当然，使用类似类名的命名方式也是比较友好的）
     */
    public enum SeasonDe {
        Spring, Summer, Autumn, Winter;

        /**
         * <pre>4.枚举可以自定义方法
         *
         * 列出所有的季节常量
         * 通过 {@link SeasonDe#values()} 方法获得所有的枚举项
         * 每个枚举都是 {@link java.lang.Enum} 的子类，
         * 该基类提供了诸如获得排序值的 {@link java.lang.Enum#ordinal()} 方法、{@link java.lang.Enum#compareTo(Enum)} 比较方法等，大大简化了常量的访问
         */
        public static void showALl() {
            for (SeasonDe season : SeasonDe.values()) {
                logger.info("{}", season);
            }
        }
    }

    /**
     * 接口常量，类常量，必须定义值
     */
    public interface SeasonDeOfI {
        int Spring = 0;
        int Summer = 1;
        int Autumn = 2;
        int Winter = 3;

        static void showALl() {
            Field[] fields = SeasonDeOfI.class.getFields();
            for (Field field : fields) {
                logger.info("{}", field);
            }
        }
    }
}
