package cn.aiclr.jvm.suggestions.book.java151.chapter01;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class ArTest {

    private static final Logger logger = LoggerFactory.getLogger(ArTest.class);

    /**
     * <pre>String 对象是否是Object实例
     * "String"是一个字符串，字符串继承了Object
     * 故 true
     */
    @Test
    @DisplayName("String 对象是否是Object实例")
    void testStringIsObject() {
        boolean bool = "String" instanceof Object;
        logger.info("\"String\" instanceof Object = {}", bool);
        Assertions.assertTrue(bool);
    }

    /**
     * <pre>String对象是否是String的实例
     * 一个类的对象是这个类（父类）的实例
     * 故 true
     */
    @Test
    @DisplayName("String对象是否是String的实例")
    void testStringIsString() {
        boolean bool = new String() instanceof String;
        logger.info("new String() instanceof String = {}", bool);
        Assertions.assertTrue(bool);
    }

    /**
     * <pre>Object对象是否是String的实例
     * Object 是父类，其对象当然不是 String 类的实例
     * 只要 instanceof 关键字左右两个操作数有继承或实现关系，就可以编译通过
     * false
     */
    @Test
    @DisplayName("Object对象是否是String的实例")
    void testObjectIsString() {
        boolean bool = new Object() instanceof String;
        logger.info("new Object() instanceof String = {}", bool);
        Assertions.assertFalse(bool);
    }


    /**
     * <pre>拆箱类型是否是装箱类型的实例
     * 编译错误
     * 'A'是一个char类型（基本类型），不是一个对象，
     * instanceof 只能用于 对象的判断，不能用于基本类型的判断
     */
    @Test
    @Disabled
    @DisplayName("拆箱类型是否是装箱类型的实例,无法编译")
    void charIsChar() {
//        boolean bool = 'A' instanceof Character;
    }

    /**
     * <pre>空对象是否是String的实例
     * instanceof 规则，左操作数是 null，结果就直接返回 false，不再运算右操作数是什么类，即在使用instanceof 操作符时，不用关心被判断的类（左操作数）是否为null，注意这与 equals，toString 方法不同。
     */
    @Test
    @DisplayName("空对象是否是String的实例")
    void testNullIsString() {
        boolean bool = null instanceof String;
        logger.info("null instanceof String = {}", bool);
        Assertions.assertFalse(bool);
    }

    /**
     * <pre>类型转换后的空对象是否是String的实例
     * (String)null 仍为 null，null 是一个万用类型，也可以说它没有类型，即使左操作数使用类型强制转换还是 null，结果仍为 false
     */
    @Test
    @DisplayName("类型转换后的空对象是否是String的实例")
    void testStringNullIsString() {
        boolean bool = (String) null instanceof String;
        logger.info("(String) null instanceof String = {}", bool);
        Assertions.assertFalse(bool);
    }

    /**
     * <pre>Date对象是否是String的实例
     * 编译错误，Date类和String类没有继承或实现关系
     */
    @Test
    @Disabled
    @DisplayName("Date对象是否是String的实例")
    void DateIsString() {
//        boolean bool = new Date() instanceof String;
    }


    /**
     * <pre>在泛型中判断 String 对象是否是 Date 的实例
     * 返回 false
     * Java 泛型是为编码服务的，在编译成字节码时，T 已经是 Object 类型，传递的实参是 String 类型。
     * 也就是说 T 的表面类型是 Object，实际类型时String。
     * <code>t instanceof Date</code> 等价于 <code>Object instanceof Date</code> 故返回false
     */
    @Test
    @DisplayName("在泛型中判断 String 对象是否是 Date 的实例")
    void testGenericClassIsString() {
        boolean bool = new Ar<String>().isDateInstance("");
        logger.info("new Ar<String>().isDateInstance(\"\") = {}", bool);
        Assertions.assertFalse(bool);
    }

}