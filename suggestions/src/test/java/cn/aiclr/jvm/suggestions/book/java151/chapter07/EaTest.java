package cn.aiclr.jvm.suggestions.book.java151.chapter07;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;

class EaTest {

    /**
     * <pre>在 Java 中，数组是一个非常特殊的类，虽然它是一个类，但没有定义类路径
     * 数组虽然是一个类，在声明时可以定义为 String[]，
     * 但编译器编译后会为不同的数组类型生成不同的类
     *  元素类型          编译后类型
     *  byte[]              [B
     *  char[]              [C
     *  double[]            [D
     *  float[]             [F
     *  int[]               [I
     *  long[]              [J
     *  short[]             [S
     *  boolean[]           [Z
     * 引用类型（如String[],注意使用英文分号结尾）
     * [L引用类型路径; （[Ljava.lang.String;）
     *
     *  下面代码可以动态加载一个数组类，
     *  但是这没有任何意义，因为它不能生成一个数组对象，
     *  只是把一个 String 类型的数组类和 long 类型的数组类加载到了内存中（如果内存中没有该类的话），
     *  并不能通过 {@link java.lang.reflect.Constructor#newInstance(Object...)} 方法生成一个实例对象，
     *  因为它没有定义数组的长度，
     *  在 Java 中数组是定长的，没有长度的数组是不允许存在的
     */
    @Test
    @DisplayName("将 String 类型和 long 类型数组类 加载到内存中，无意义操作")
    void test() {
        System.out.println(byte[].class);
        System.out.println(char[].class);
        System.out.println(int[].class);
        System.out.println(float[].class);
        System.out.println(double[].class);
        System.out.println(long[].class);
        System.out.println(short[].class);
        System.out.println(boolean[].class);
        System.out.println(String[].class);
        Assertions.assertThrows(ClassNotFoundException.class, () -> Class.forName("java.lang.String[]"));
        Assertions.assertDoesNotThrow(() -> Class.forName("[Ljava.lang.String;"));
        Assertions.assertDoesNotThrow(() -> Class.forName("[J"));
    }

    /**
     * <pre>反射不能定义一个数组
     * 使用 {@link java.lang.reflect.Array} 数组反射类来动态加载
     */
    @Test
    @DisplayName("java.lang.reflect.Array 动态加载数组")
    void testArray() {
        String[] strs = (String[]) Array.newInstance(String.class, 8);
        int[][] ints = (int[][]) Array.newInstance(int.class, 2, 3);
        Assertions.assertEquals(8, strs.length);
        Assertions.assertEquals(2, ints.length);
        Assertions.assertEquals(3, ints[0].length);
    }
}
