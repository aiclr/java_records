package cn.aiclr.jvm.suggestions.book.java151.chapter02;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <pre>27.谨慎包装类型的大小比较
 *
 * 只要是两个对象之间的比较就应该采用相应的方法，而不是通过Java的默认机制来处理，除非你确定对此非常了解
 */
public class Ba {

    private static final Logger logger = LoggerFactory.getLogger(Ba.class);

    public static void main(String[] args) {

        /**
         * Java 中 “==” 是用来判断两个操作数是否有相等关系的，
         * 如果是基本类型则判断值是否相等，
         * 如果是对象则判断是否是一个对象的两个引用，也就是地址是否相等
         */
        //-128 to 127 范围内 等号判断 true
        Integer i = Integer.valueOf(100);
        Integer i1 = Integer.valueOf(100);
        logger.info("Integer.valueOf(100)==Integer.valueOf(100) is {}", i == i1);
        //否则为 false
        i = Integer.valueOf(128);
        i1 = Integer.valueOf(128);
        logger.info("Integer.valueOf(128)==Integer.valueOf(128) is {}", i == i1);

        Integer i2 = 101;
        Integer i3 = 101;
        logger.info("i2==i3 is {}", i2 == i3);

        int i4 = 101;
        int i5 = 101;
        logger.info("i4==i5 is {}", i4 == i5);


        /**
         * Java 中，“>”和“<”用来判断两个数字类型的大小关系，
         * 注意只能是数字型的判断，
         * 对于 Integer 包装类型，是根据其 intValue() 方法的返回值（也就是其相应的基本类型）进行比较的
         * （其他包装类型是根据相应的 value 值来比较的，如doubleValue、floatValue等）
         */
        Integer j = Integer.valueOf(102);
        logger.info("{} > {}={}", i, j, i > j);

        //正确比较方式
        //i=j  0
        //i>j  1
        //i<j  -1
        logger.info("{}.compareTo({})={}", i, j, i.compareTo(j));
        logger.info("{}.compareTo({})={}", j, i, j.compareTo(i));
        logger.info("{}.compareTo({})={}", i, i1, i.compareTo(i1));
    }
}
