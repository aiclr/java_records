package cn.aiclr.jvm.suggestions.book.java151.chapter07;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>94.不能初始化泛型参数和数组
 * 泛型类型在编译期被擦除，我们在类初始化时将无法获得泛型的具体参数
 *
 * 类的成员变量是在类初始化前初始化的，
 * 所以要求在初始化前它必须具有明确的类型，
 * 否则就只能声明，不能初始化
 */
public class Dp {

    /**
     * <pre> t、tArray、list都是类变量，都是通过 new 声明了一个类型，看起来非常相似啊！
     * 但这段代码是编译不通过的，
     * 因为编译器在编译时需要获得 T 类型，
     * 但泛型在编译期类型已经被擦除了，
     * 所以 {@code new T()} 和 {@code new T[5]} 都会报错
     *      （可能有读者疑惑了：泛型类型可以擦除为顶级类 {@link java.lang.Object}，
     *      那 T 类型擦除成 {@link java.lang.Object} 不就可以编译了吗？
     *      这样也不行，泛型只是 Java 语言的一部分，
     *      Java 语言毕竟是一个强类型、编译型的安全语言，
     *      要确保运行期的稳定性和安全性就必须要求在编译器上严格检查）。
     * {@code new ArrayList<T>()} 不会报错
     * 这是因为 ArrayList 表面是泛型，其实已经在编译期转型为 Object 了
     * 观察 ArrayList 源码
     * 容纳 ArrayList 元素的定义：其类型是 {@link java.lang.Object}
     *      {@code transient Object[] elementData;}
     * {@link java.lang.Object} 是所有类的父类，数组又允许协变（Covariant），
     * 因此 {@link java.util.ArrayList#elementData} 数组可以容纳所有的实例对象。
     * 处理方式：
     *      元素加入时向上转型为 Object 类型（E类型转为Object），
     *      取出时向下转型为 E 类型（Object转为E类型），
     */
    class FooDp<T> {
        //        private T t = new T();//编译报错
//        private T[] tArrays = new T[5];//编译报错
        private List<T> list = new ArrayList<>();
    }

    /**
     * <pre>泛型数组解决方案
     * 在运行期获得 T 的类型，也就是 tType 参数，
     * 一般情况下泛型类型是无法获取的，
     * 不过，在客户端调用时多传输一个 T 类型的 class 就会解决问题
     */
    class FooDpFix<T> {
        private T t;
        private T[] tArrays;
        private List<T> list = new ArrayList<>();

        public FooDpFix() {
            try {
                Class<?> tType = Class.forName("");
                t = (T) tType.newInstance();
                tArrays = (T[]) Array.newInstance(tType, 5);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
