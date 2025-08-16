package cn.aiclr.jvm.suggestions.book.java151.chapter04;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <pre>53.注意方法中传递的参数要求
 * 有这样一个简单需求：
 * 写一个方法，实现从原始字符串中删除与之匹配的所有子字符串，
 * 比如在“蓝蓝的天，白云飘”中，删除“白云飘”，输出“蓝蓝的天，”
 *
 * 注意：{@link String#replaceAll(String, String)} 传递的第一个参数是正则表达式
 */
public class Ca {

    private static final Logger logger = LoggerFactory.getLogger(Ca.class);

    public static void main(String[] args) {
        logger.info("\"{}\".replaceAll(\"{}\",\"\")={}", "ABA", "A", removeErr("ABA", "A"));
        logger.info("\"{}\".replaceAll(\"{}\",\"\")={}", "$B$", "$", removeErr("$B$", "$"));
        logger.info("\"{}\".replace(\"{}\",\"\")={}", "ABA", "A", remove("ABA", "A"));
        logger.info("\"{}\".replace(\"{}\",\"\")={}", "$B$", "$", remove("$B$", "$"));
    }

    /**
     * <pre>删除字符串，有问题
     * {@link String#replaceAll(String, String)} 该方法确实需要传递两个 String 类型的参数，也确实进行了字符串替换，
     * 但是它要求第一个参数是一个正则表达式，符合正则表达式的字符串才会被替换。
     * 对上面的例子来说，第一个测试案例传递进来的是一个字符串“好”，这是一个全匹配查找替换，处理得非常正确，
     * 第二个测试案例传递进来的是 “$” 符号，“$” 符号在正则表达式中表示的是字符串的结束位置，
     * 也就是说执行完 {@link String#replaceAll(String, String)} 后，在字符串结尾的地方加上了空字符串，其结果还是 “$是$”，
     * 所以测试失败也就在所难免了。
     * 问题清楚了，解决方案也就出来了：
     * 使用 {@link String#replace(CharSequence, CharSequence)} 方法替代即可，是 {@link String#replaceAll(String, String)} 方法的简化版，
     * 可传递两个 String 参数继续替换，与我们的编码意图是相吻合的
     */
    public static String removeErr(String source, String sub) {
        return source.replaceAll(sub, "");
    }

    /**
     * <pre>删除字符串
     * {@link String#replace(CharSequence, CharSequence)} 方法是在 1.5 版本以后才开始提供的，
     * 在此之前如果要对一个字符串进行全替换，只能使用 {@link String#replaceAll(String, String)} 方法，
     * 不过由于 {@link String#replaceAll(String, String)} 方法的第一个参数使用了正则表达式，
     * 而且参数类型是 String，
     * 所以很容易让使用者误解，稍有不慎就会导致严重的替换错误
     */
    public static String remove(String source, String sub) {
        return source.replace(sub, "");
    }
}
