package cn.aiclr.jvm.suggestions.book.java151.chapter08;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * <pre>116.异常只为异常服务
 *
 * 异常只能用在非正常的情况下，不能成为正常情况的主逻辑，
 * 也就是说，异常只是主场景中的辅助场景，不能喧宾夺主
 */
public class El {
    /**
     * <pre>增加if判断语句，增加了代码量，
     * 但是却会减少 {@link java.io.FileNotFoundException} 异常出现的几率，提高了程序的性能和稳定性
     */
    public static void main(String[] args) {
        File file = new File("/tmp/readme.md");
        if (file.exists() && !file.isDirectory()) {
            try (FileInputStream fis = new FileInputStream(file)) {
                //...
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * <pre>异常原本是正常逻辑的一个补充，但是有时候会被当作主逻辑使用
     * 判断一个枚举是否包含 String 枚举项
     * 根据 valueOf 方法是否抛出异常来进行判断，
     * 如果抛出异常（一般是 IllegalArgumentException 异常），则认为是不包含，
     * 若不抛出异常则可以认为包含该枚举项，
     * 看上去这段代码很正常，但是其中却有三个错误：
     * 1）异常判断降低了系统性能。
     * 2）降低了代码的可读性，
     *      只有详细了解 valueOf 方法的人才能读懂这样的代码，
     *      因为 valueOf 抛出的是一个非受检异常
     * 3）隐藏了运行期可能产生的错误，catch 到异常，但没有做任何处理
     */
    public static <T extends Enum<T>> boolean Contain(Class<T> c, String name) {
        boolean result = false;
        try {
            Enum.valueOf(c, name);
            result = true;
        } catch (RuntimeException e) {
            //只要是抛出异常，则认为是不包含
        }
        return result;
    }

    /**
     * 不在主逻辑中使用异常可以解决上述三个问题
     */
    public static <T extends Enum<T>> boolean ContainPro(Class<T> c, String name) {
        //遍历枚举项
        for (T t : c.getEnumConstants()) {
            //枚举项名称是否相等
            if (t.name().equals(name)) {
                return true;
            }
        }
        return false;
    }
}

