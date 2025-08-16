package cn.aiclr.jvm.suggestions.book.java151.chapter01;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <pre>8.不要让旧语法困扰你
 * Java中虽然没有了goto关键字，但是扩展了break和continue关键字，它们的后面都可以加上标号做跳转，完全实现了goto功能
 * <code>:</code> 标号，直接跳出标记的循环
 * 现在使用 break，continue 替代,不过跳出多重循环可以使用 标号
 */
public class Ah {

    private static final Logger logger = LoggerFactory.getLogger(Ah.class);

    public static void main(String[] args) {
        saveDefault:
        save(200);

        saveDefault:
        saveNow(200);
    }


    private static void save(int fee) {
        loop1:
        for (int i = 0; i < 3; i++) {
            loop2:
            for (int j = 0; j < 2; j++) {
                fee++;
                logger.info("{}", fee);
                if (i == 1) {
                    logger.info("i={}", i);
                    logger.info("j={}", j);
                    break loop1;//跳出外层循环 loop1
                }
                if (i == 0) {
                    logger.info("i={}", i);
                    logger.info("j={}", j);
                    break loop2;//跳出内层循环 loop2
                }
            }
        }
        logger.info("最终结果={}", fee);
    }

    private static void saveNow(int fee) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 2; j++) {
                fee++;
                logger.info("{}", fee);
                if (i == 1) {
                    logger.info("i={}", i);
                    logger.info("j={}", j);
                    logger.info("最终结果={}", fee);
                    return;
                }
                if (i == 0) {
                    logger.info("i={}", i);
                    logger.info("j={}", j);
                    break;
                }
            }
        }
    }
}