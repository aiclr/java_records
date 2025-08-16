package cn.aiclr.jvm.suggestions.book.java151.chapter02;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

/**
 * <pre>21.用偶判断，不用奇判断
 * 使用 & 与运算 判断奇偶
 */
public class Au {

    private static final Logger logger = LoggerFactory.getLogger(Au.class);

    public static void main(String[] args) {
        logger.info("-1 / 2 = {}", -1 / 2);
        logger.error("-1 % 2 = {}", -1 % 2);

        logger.info("-1 & 1 = {}", -1 & 1);
        logger.info("-2 & 1 = {}", -2 & 1);
        logger.info("0 & 1 = {}", 0 & 1);
        logger.info("1 & 1 = {}", 1 & 1);
        logger.info("2 & 1 = {}", 2 & 1);

        logger.info("模拟java取余%算法: remainder(-1, 2)={}", remainder(-1, 2));
        Scanner input = new Scanner(System.in);
        logger.info("请输入多个数字判断奇数偶数：");
        //1 2 0 -1 -2
        while (input.hasNextInt()) {
            int i = input.nextInt();
            String str;
            //用偶判断
            str = i % 2 == 0 ? "偶" : "奇";
            logger.info("用 [i % 2 == 0] 判断 {} % 2 = {} -> {}", i, i % 2, str);

            //用奇判断
            str = i % 2 == 1 ? "奇" : "偶";
            logger.error("用 [i % 2 == 1] 判断 {} % 2 = {} -> {}", i, i % 2, str);

            //用偶判断
            str = (i & 1) == 0 ? "偶" : "奇";
            logger.info("用 [(i & 1) == 0] 判断 {} & 1 = {} -> {}", i, i & 1, str);

            //用奇判断
            str = (i & 1) == 1 ? "奇" : "偶";
            logger.info("用 [(i & 1) == 1] 判断 {} & 1 = {} -> {}", i, i & 1, str);
        }
    }

    /**
     * 模拟java取余%算法
     */
    public static int remainder(int dividend, int divisor) {
        return dividend - dividend / divisor * divisor;
    }
}