package cn.aiclr.jvm.suggestions.book.java151.chapter07;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <pre>95.强制声明泛型的实际类型
 *
 * 无法从代码中推断出泛型类型的情况下，即可强制声明泛型类型
 *
 * {@link java.util.Arrays#asList(Object[])} 可以把一个变长参数或数组转变为列表，
 * 但是它有一个缺点：它所生成的 List 长度是不可改变的。
 * 如果期望生成的列表长度是可变，那就需要自己来写一个数组的工具类。
 */
public class Dq {

    public static void main(String[] args) {
        /**
         * <pre>变量 list 是一个常规用法，没有任何问题，
         * 泛型实际的参数类型是 String，返回的结果也就是一个容纳 String 元素的 List 对象
         */
        List<String> list = ArrayUtilsDq.asList("A", "B");
        System.out.println(list);

        /**
         * <pre>变量list1中容纳的是什么元素呢？
         * 我们无法从代码中推断出list1列表到底容纳的是什么元素
         * （因为它传递的参数是空，编译器也不知道泛型的实际参数类型是什么），
         * 不过，编译器会很“聪明”地推断出最顶层类 Object 就是其泛型类型
         */
        List list1 = ArrayUtilsDq.asList();
        System.out.println(list1);

        List<Integer> list11 = ArrayUtilsDq.<Integer>asList();
        System.out.println(list11);

        /**
         * <pre>变量 list2 有两种类型的元素：整数类型和浮点类型，
         * 那它生成的 List 泛型化参数应该是什么呢？
         * 是 Integer 和 Float 的父类 Number？
         * 你太高看编译器了，它不会如此推断的，
         * 当它发现多个元素的实际类型不一致时就会直接确认泛型类型是 Object，
         * 而不会去追索元素类的公共父类是什么，
         * 但是对于 list22，我们更期望它的泛型参数是 Number，都是数字嘛！参照 list2 变量，代码修改如下
         */
        List list2 = ArrayUtilsDq.asList(1, 2, 3.1);
        System.out.println(list2);

        List<Number> list22 = ArrayUtilsDq.<Number>asList(1, 2, 3.1);
        System.out.println(list22);
    }

    class ArrayUtilsDq {
        /**
         * <pre> 把一个变长参数变为列表，并且长度可变
         *
         * 使用 {@link java.lang.SafeVarargs} 时，确保方法不会修改 参数 数组内容。
         * 如果你不确定方法是否安全，不要使用此注解。
         * 可以用 List<T> 替代 varargs，避免堆污染问题。
         *  <code>
         * //不安全的例子
         * @SafeVarargs
         * public static <T> void badMethod(T... args) {
         *     Object[] array = args;
         *     array[0] = "I'm a String"; // 堆污染
         * }
         * //如果调用:
         * badMethod(1, 2, 3); // 实际是 Integer[]
         * //会导致运行时异常 java.lang.ArrayStoreException: java.lang.String
         *  </code>
         */
        @SafeVarargs
        public static <T> List<T> asList(T... t) {
            List<T> list = new ArrayList<>();
            Collections.addAll(list, t);
            return list;
        }
    }
}
