package cn.aiclr.jvm.suggestions.book.java151.chapter04;

/**
 * <pre>55.注意字符串的位置
 * Java 对加号的处理机制：
 *  在使用加号进行计算的表达式中，只要遇到 String 字符串，
 *  则所有的数据都会转换为 String 类型进行拼接，
 *  如果是原始数据，则直接拼接，
 *  如果是对象，则调用 toString 方法的返回值然后拼接
 *
 *  注意：在“+”表达式中，String 字符串具有最高优先级
 */
public class Cc {
    public static void main(String[] args) {
        //1. 1 + 2 = 3
        //2. 3 + " apples" = "3 apples"
        String str1 = 1 + 2 + " apples";
        System.out.println(str1);
       
        //1. "apples:" + 1 = "apples:1"
        //2. "apples:1"+2 = "apples:12"
        String str2 = "apples:" + 1 + 2;
        System.out.println(str2);
    }
}
