package cn.aiclr.jvm.suggestions.book.java151.chapter07;

import java.util.List;

/**
 * <pre>96.不同的场景使用不同的泛型通配符
 *
 * 如果一个泛型结构即用作“读”操作又用作“写”操作，不限定，使用确定的泛型类型即可，如 List<E>
 *
 * Java 泛型支持通配符（Wildcard），
 * 可以单独使用一个 “？” 表示任意类，
 * 也可以使用 extends 关键字表示某一个类（接口）或其子类型，
 * 还可以使用 super 关键字表示某一个类（接口）或其父类型
 *
 * 1)泛型结构只参与 “读” 操作则限定上界（使用 extends 关键字）,利用父类读所有子类。
 * 2)泛型结构只参与 “写” 操作则限定下界（使用 super 关键字），仅写入本类及子类。
 *
 * 对于是要限定上界还是限定下界，
 * JDK 的 {@link java.util.Collections#copy(java.util.List, java.util.List)} 方法是一个非常好的例子，
 * 它实现了把源列表中的所有元素拷贝到目标列表中对应的索引位置上
 *
 * 源列表是用来提供数据的，所以 src 变量需要限定上界，带有 extends 关键字
 * 目标列表是用来写入数据的，所以 dest 变量需要界定下界，带有 super 关键字
 * <code>
 *      public static <T> void copy(List<? super T> dest, List<? extends T> src) {
 *         int srcSize = src.size();
 *         if (srcSize > dest.size())
 *             throw new IndexOutOfBoundsException("Source does not fit in dest");
 *
 *         if (srcSize < COPY_THRESHOLD ||
 *             (src instanceof RandomAccess && dest instanceof RandomAccess)) {
 *             for (int i=0; i<srcSize; i++)
 *                 dest.set(i, src.get(i));
 *         } else {
 *             ListIterator<? super T> di=dest.listIterator();
 *             ListIterator<? extends T> si=src.listIterator();
 *             for (int i=0; i<srcSize; i++) {
 *                 di.next();
 *                 di.set(si.next());
 *             }
 *         }
 *     }
 * </code>
 */
public class Dr {

    /**
     * <pre>不知道 list 到底存放的是什么元素，
     * 只能推断出是 E 类型的父类（当然，也可以是 E 类型，下同，不再赘述），
     * 但问题是 E 类型的父类是什么呢？无法再推断，只有运行时才知道，那么编码期就完全无法操作
     * 当然，你可以把它当作是 Object 类来处理，需要时再转换成 E 类型 ---- 这完全违背了泛型的初衷
     * 在这种情况下，“读”操作如果期望从 List 集合中读取数据就需要使用 extends 关键字了，
     * 也就是要界定泛型的上界
     */
    public static <E> void readSuper(List<? super E> list) {
        for (Object obj : list) {
            //...
        }
    }

    /**
     * <pre>推断出 List 集合中取出的是 E 类型的元素。
     * 具体是什么类型的元素就要等到运行时才能确定了，
     * 但它一定是一个确定的类型
     *
     * 比如 {@code read(Arrays.asList("A"))} 调用该方法时，
     * 可以推断出 List 中的元素类型是 String，
     * 之后就可以对 List 中的元素进行操作了，
     * 如加入到另外的 List<E> 集合中，或者作为 Map<E,V> 的键等
     */
    public static <E> void readExtends(List<? extends E> list) {
        for (E e : list) {
            //...
        }
    }


    class Parent {

    }

    class Son extends Parent {

    }

    class GrandSon extends Son {
    }

    /**
     * <pre>编译失败，
     * 失败的原因是 list 中的元素类型不确定，
     * 也就是编译器无法推断出泛型类型到底是什么，
     * 是 Integer 类型？
     * 是 Double ？
     * 还是 Byte ？
     * 这些都符合 extends 关键字的定义，
     * 由于无法确定实际的泛型类型，所以编译器拒绝了此类操作
     * 在此种情况下，只有一个元素是可以 add 进去的：
     *      null 值，这是因为 null 是一个万用类型，它可以是所有类的实例对象，所以可以加入到任何列表中
     */
    public static void writeExtends(List<? extends Son> list) {
//        list.add(new Parent());//编译失败
//        list.add(new Son());//编译失败
//        list.add(new GrandSon());//编译失败

        //Object 不可以，因为 Object 不是 Number 的子类
//        list.add(new Object());

        //null是一个万用类型，它可以是所有类的实例对象，所以可以加入到任何列表中
        list.add(null);
    }

    /**
     * <pre>在这种“写”操作的情况下，使用 super 关键字限定泛型类型的下界才是正道
     * 甭管它是 Integer 类型的 123，还是 Float 类型的 3.14，都可以加入到 list 列表中，
     * 因为它们都是 Number 类型，这就保证了泛型类的可靠性
     */
    public void writeSuper(List<? super Son> list) {
//        list.add(new Parent());
        list.add(new Son());
        list.add(new GrandSon());
        //Object 不可以，因为 Object 不是 Number 的子类
//        list.add(new Object());
        list.add(null);
    }

    public void read(List<? extends Son> list) {
        for (Son son : list) {
            //...
        }
    }
}
