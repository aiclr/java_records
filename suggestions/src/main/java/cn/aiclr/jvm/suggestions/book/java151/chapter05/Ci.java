package cn.aiclr.jvm.suggestions.book.java151.chapter05;

import java.util.Arrays;

/**
 * <pre>61.若有必要，使用变长数组
 * Java 中的数组是定长的，一旦经过初始化声明就不可改变长度，
 * 通过对数组扩容“婉转”地解决该问题
 */
public class Ci {
    /**
     * <pre>数组扩容
     * 采用的是 Arrays 数组工具类的 copyOf 方法
     * 产生了一个 newLength 长度的新数组，
     * 并把原有的值拷贝了进去，之后就可以对超长的元素进行赋值
     */
    public static void main(String[] args) {
        //初始化 60
        int length = 60;
        Ci[] classes = new Ci[length];
        System.out.println(classes.length);

        //扩容到 80
        int newLength = 80;
        classes = Arrays.copyOf(classes, newLength);
        System.out.println(classes.length);
    }
}