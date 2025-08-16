package cn.aiclr.jvm.suggestions.book.java151.chapter01;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.lang.Math.abs;

/**
 * <pre>10.不要在本类中覆盖静态导入的变量和方法
 *
 * 编译器有一个“最短路径”原则，
 * 如果能够在本类中查找到的变量，常量，方法，就不会到其他包或父类、接口中查找，以确保本类中的属性、方法优先
 *
 * 如果要变更一个被静态导入的方法，最好的办法是在原始类中重构，而不是在本类中覆盖
 */
public class Aj {

    private static final Logger logger = LoggerFactory.getLogger(Aj.class);

    //覆盖静态导入的变量和方法
    //常量名与与静态导入的PI相同
    public final static String PI = "祖冲之";

    //方法名与静态导入的相同
    //import static java.lang.Math.abs;
    public static int abs(int abs) {
        return 0;
    }

    public static void main(String[] args) {
        logger.info("{}", PI);
        logger.info("{}", abs(100));
    }

}