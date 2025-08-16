package cn.aiclr.jvm.suggestions.book.java151.chapter02;

/**
 * <pre>24.边界，边界，还是边界
 * int -2147483648 ~ 2147483647
 *
 * 输入 800 结果 true
 * 输入 2147483647 应该异常，程序由于设计问题，却返回正常
 *
 * 2147483647 是int类型的最大值，
 * 输入了一个最大值，使校验条件失效了，
 * order 的值是 2147483647，那再加上 1000 就超出int的范围了，其结果是 -2147482649，那当然是小于正数 2000了！
 * 一句话可归结其原因：数字越界使检验条件失效
 *
 * 在单元测试中，有一项测试叫做边界测试（也有叫做临界测试），
 * 如果一个方法接收的是 int 类型的参数，
 * 那以下三个值是必测的：0、正最大、负最小，其中正最大和负最小是边界值，
 * 如果这三个值都没有问题，方法才是比较安全可靠的。
 * 例子就是因为缺少边界测试，致使生产系统产生了严重的偏差
 */
public class Ax {

    public static final int LIMIT = 2000;

    public static boolean check(int value) {
        int salt = 1000;
        return value >= 0 && value + salt <= LIMIT;
    }
}