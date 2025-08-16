package cn.aiclr.jvm.suggestions.book.java151.chapter02;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <pre>23.不要让类型默默转换
 * 基本类型转换时，使用主动声明方式减少不必要的 Bug
 *
 * Java 是先运算然后再进行类型转换的，
 * 具体地说就是因为 disc2 的三个运算参数都是 int 类型，
 * 三者相乘的结果虽然也是 int 类型，
 * 但是已经超过了 int 的最大值，
 * 所以其值就是负值了（为什么是负值？因为过界了就会从头开始），
 * 再转换成 long 型，结果还是负值
 */
public class Aw {

    private static final Logger logger = LoggerFactory.getLogger(Aw.class);

    public static final int LIGHT_SPEED = 30 * 10000 * 1000;

    public static void main(String[] args) {

        long dis1 = LIGHT_SPEED * 1;
        logger.info(" long dis1 = {} * 1; dis1={}", LIGHT_SPEED, dis1);

        /**
         * Java是先运算然后再进行类型转换的，
         * 具体地说就是因为disc2的三个运算参数都是int类型，
         * 三者相乘的结果虽然也是int类型，
         * 但是已经超过了int的最大值，
         * 所以其值就是负值了（为什么是负值？因为过界了就会从头开始），
         * 再转换成long型，结果还是负值
         */
        long dis2 = LIGHT_SPEED * 60 * 8;//-2028888064
        logger.error(" long dis2 = {} * 60 * 8; dis2={}", LIGHT_SPEED, dis2);

        //解决方案
        long dis3 = LIGHT_SPEED * 60L * 8;
        logger.info(" long dis3 = {} * 60L * 8; dis3={}", LIGHT_SPEED, dis3);

        //更优雅通用的做法，主动声明式类型转化（注意不是强制类型转换）
        long dis4 = 1L * LIGHT_SPEED * 60 * 8;
        logger.info(" long dis4 = 1L * {} * 60 * 8; dis4={}", LIGHT_SPEED, dis4);
    }
}