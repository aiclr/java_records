package cn.aiclr.jvm.suggestions.book.java151.chapter07;

/**
 * <pre>105.动态加载不适合数组
 * 注意：
 * 通过反射操作数组使用 {@link java.lang.reflect.Array} 类，不要采用通用的反射处理 API
 *
 * 如果 {@link java.lang.Class#forName(String)} 要加载一个类，
 * 那它首先必须是一个类 ~~~ 8个基本类型排除在外，它们不是一个具体的类
 * 其次，它必须具有可追索的类路径，否则就会报 {@link java.lang.ClassNotFoundException}
 *
 * 在 Java 中，数组是一个非常特殊的类，虽然它是一个类，但没有定义类路径
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
 *  引用类型（如String[],注意使用英文分号结尾）
 *  [L引用类型路径; （[Ljava.lang.String;）
 *
 * 数组比较特殊，要想动态创建和访问数组，基本的反射是无法实现的，
 * 于是 Java 就专门定义了一个 {@link java.lang.reflect.Array} 数组反射工具类来实现动态探知数组的功能
 */
public class Ea {
}
