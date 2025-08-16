package cn.aiclr.jvm.suggestions.book.java151.chapter07;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

/**
 * <pre>100.数组的真实类型必须是泛型类型的子类型
 *
 * 当一个泛型类（特别是泛型集合）转变为泛型数组时，
 * 泛型数组的真实类型不能是泛型类型的父类型（比如顶层类 Object），
 * 只能是泛型类型的子类型（当然包括自身类型），否则就会出现类型转换异常
 */
public class Dv {

    /**
     * <pre>抛异常：
     * <code>
     *     Exception in thread "main" java.lang.ClassCastException: class [Ljava.lang.Object; cannot be cast to class [Ljava.lang.String; ([Ljava.lang.Object; and [Ljava.lang.String; are in module java.base of loader 'bootstrap')
     * 	at cn.aiclr.jvm.suggestions.book.java151.chapter07.Dv.main(Dv.java:50)
     * </code>
     * 1）为什么 Object 数组不能向下转型为 String 数组
     *    数组是一个容器，只有确保容器内的所有元素类型与期望的类型有父子关系时才能转换，
     *    Object 数组只能保证数组内的元素是 Object 类型，
     *    却不能确保它们都是 String 的父类型或子类，所以类型转换失败
     * 2）为什么是 main 方法抛出异常，而不是 toArray 方法
     *    其实，是在 toArray 方法中进行的类型向下转换，而不是 main 方法中。
     *    那为什么异常会在 main 方法中抛出，应该在 toArray 方法的 {@code T[] t = (T[])newObject[list.size()]} 这段代码才对呀？
     *    那是因为泛型是类型擦除的，toArray 方法经过编译后与如下代码相同
     *    <code>
     *      public static Object[] toArray(List list){
     *         Object[] t=(Object[])new Object[list.size()];
     *         for (int i=0;i<list.size();i++){
     *             t[i] =list.get(i);
     *         }
     *         return t;
     *     }
     *     public static void main(String[] args) {
     *         List<String> list= Arrays.asList("A","B");
     *         for (String str:(String[])toArray(list))
     *             System.out.println(str);
     *     }
     *    </code>
     *    toArray 方法返回后会进行一次类型转换，
     *    Object[] 转换成了 String[] ，
     *    于是就抛出 {@link java.lang.ClassCastException} 异常了
     */
    public static void main(String[] args) {
        List<String> list = Arrays.asList("A", "B");
//        for (String str:toArray(list))//异常
//            System.out.println(str);//异常
        for (String str : toArrayPro(list, String.class))
            System.out.println(str);
    }


    /**
     * <pre>List 接口的 toArray  方法可以把一个集合转化为数组，但是使用不方便，
     * {@link java.util.List#toArray()} 方法返回的是一个 Object[] ，所以需要自行转变；
     * {@link java.util.List#toArray(Object[])} 虽然返回的是 T 类型的数组，但是还需要传入一个 T 类型的数组，这也挺麻烦的，
     *
     * 我们期望输入的是一个泛型化的 List，这样就能转化为泛型数组
     */
    public static <T> T[] toArray(List<T> list) {
//        T[] t = new T[list.size()];//泛型 不允许直接实例化，编译不通过
        T[] t = (T[]) new Object[list.size()];
        for (int i = 0; i < list.size(); i++) {
            t[i] = list.get(i);
        }
        return t;
    }

    /**
     * {@link java.lang.reflect.Array#newInstance(Class, int)} 优化
     */
    public static <T> T[] toArrayPro(List<T> list, Class<T> tClass) {
        T[] t = (T[]) Array.newInstance(tClass, list.size());
        for (int i = 0; i < list.size(); i++) {
            t[i] = list.get(i);
        }
        return t;
    }

}

