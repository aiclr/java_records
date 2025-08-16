package cn.aiclr.jvm.suggestions.book.java151.chapter01;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <pre>3.三元操作符的类型务必一致
 * 保证三元操作符中的两个操作数类型一致，即可减少可能错误的发生
 * 三元操作符类型的转换规则
 *      1.若两个操作数不可转换，则不做转换，返回值为Object类型
 *      2.若两个操作数是明确类型的表达式（比如变量），则按照正常的二进制数字来转换，int转long，long转float
 *      3.若两个操作数中有一个是数字S，另一个是表达式，且其类型表示为T，那么若数字S在T的范围内，则转换为T类型；若S超出了T类型的范围，则T转换为S类型
 *      4.若两个操作数都是直接量(字面量 Literal)数字，则返回值类型为范围较大者

 * 当第二，第三位操作数分别为 基本类型 和 对象 时，
 * 其中的对象就会 拆箱 为 基本类型 进行操作
 * 由于使用了三目运算符，并且第二、第三位操作数分别是基本类型和对象。
 * 所以对对象进行拆箱操作，由于该对象为null，
 * 所以在拆箱过程中调用 null.booleanValue() 的时候就报了 NPE (空指针)
 * {@link cn.aiclr.jvm.suggestions.book.java151.chapter01.AcTest#testNPE()}
 * {@link cn.aiclr.jvm.suggestions.book.java151.chapter01.AcTest#testNull()}
 */
public class Ac {

    private static final Logger logger = LoggerFactory.getLogger(Ac.class);

    public static void main(String[] args) {

        Integer a = null;
//        Integer b=false?1:a;//抛出空指针异常
        logger.error("Integer b=false?1:a; 会抛出空指针异常");

        Integer c = 1;
        Integer b1 = false ? c : a;
        logger.info("false ? c : a = {}", b1);

        int i = 80;
        String s = String.valueOf(i < 100 ? 90 : 100);
        String s1 = String.valueOf(i < 100 ? 90 : 100.0);
        logger.info("两者变得不相等了 {}", s.equals(s1));
    }
}