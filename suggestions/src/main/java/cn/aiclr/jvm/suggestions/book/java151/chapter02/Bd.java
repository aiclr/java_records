package cn.aiclr.jvm.suggestions.book.java151.chapter02;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

/**
 * <pre> 30.不要随便设置随机种子
 * 在Java中有两种方法可以获得不同的随机数：
 *  通过 {@link java.util.Random} 类获得随机数;
 *  {@link java.lang.Math#random()} 也是通过生成一个 {@link java.util.Random} 类的实例，然后委托 {@link java.util.Random#nextDouble()} 方法，两者是殊途同归，没有差别;
 *
 * 随机数在太多的地方使用了，比如 加密、混淆数据等，
 * 我们使用随机数是期望获得一个唯一的、不可仿造的数字，以避免产生相同的业务数据造成混乱。
 * 在Java项目中通常是通过{@link java.lang.Math#random()} 方法和 {@link java.util.Random} 类来获得随机数的
 *
 * {@link java.util.Random} 类的默认种子（无参构造）是 {@link java.lang.System#nanoTime()} 的返回值（JDK 1.5版本以前默认种子是 {@link java.lang.System#currentTimeMillis()} 的返回值），
 * 注意这个值是距离某一个固定时间点的纳秒数，
 * 不同的操作系统和硬件有不同的固定时间点，
 * 也就是说不同的操作系统其纳秒值是不同的，
 * 而同一个操作系统纳秒值也会不同，随机数自然也就不同。
 * （顺便说下， {@link java.lang.System#nanoTime()} 不能用于计算日期，那是因为“固定”的时间点是不确定的，纳秒值甚至可能是负值，这点与 {@link java.lang.System#currentTimeMillis()}不同。）
 *
 * <code>new Random(1000)</code> 显式地设置了随机种子为 1000，
 * 运行多次，虽然实例不同，但都会获得相同的三个随机数。所以，除非必要，否则不要设置随机种子
 */
public class Bd {

    private static final Logger logger = LoggerFactory.getLogger(Bd.class);

    public static void main(String[] args) {

        Random random = new Random();
        Random random1 = new Random();
        for (int i = 1; i < 4; i++) {
            int res1 = random.nextInt();
            int res2 = random1.nextInt();
            logger.info("{} - {} = {}", res1, res2, res1 - res2);
        }

        //-1244746321
        //1060493871
        //-1826063944
        //在同一台机器上，甭管运行多少次，所打印的随机数都是相同的,因为产生随机数的种子被固定了
        //种子不同，产生不同的随机数
        //种子相同，即使实例不同也产生相同的随机数
        random = new Random(1000);
        random1 = new Random(1000);
        for (int i = 1; i < 4; i++) {
            int res1 = random.nextInt();
            int res2 = random1.nextInt();
            logger.info("{} - {} = {}", res1, res2, res1 - res2);
        }
    }
}
