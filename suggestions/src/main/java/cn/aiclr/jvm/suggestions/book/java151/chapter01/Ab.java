package cn.aiclr.jvm.suggestions.book.java151.chapter01;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

/**
 * <pre>2.莫让常量蜕变成变量
 * 每次启动结果都会变
 * 务必让常量的值在运行期保持不变
 * 常量就是常量，在编译期就必须确定其值，不应该在运行期更改，否则程序的可读性会非常差
 */
public class Ab {

    private static final Logger logger = LoggerFactory.getLogger(Ab.class);

    public static void main(String[] args) {
        //使用会变化的常量，结果无法预测
        logger.info("1 减去 会变化常量 = {}", Const.RAND_CONST);
    }
}

interface Const {
    // RAND_CONST 常量 但是会变化
    int RAND_CONST = new Random().nextInt();
}