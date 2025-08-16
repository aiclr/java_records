package cn.aiclr.jvm.suggestions.book.java151.chapter05;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.RandomAccess;
import java.util.stream.Stream;

class CoTest {

    private static final int num = 80 * 1000;
    private static ArrayList<Integer> scores0 = new ArrayList<>();
    private static LinkedList<Integer> scores1 = new LinkedList<>();

    public static Stream<Arguments> provideData() {
        return Stream.of(scores0, scores1).map(Arguments::of);
    }

    @BeforeAll
    static void beforeEach() {
        for (int i = 0; i < num; i++) {
            Integer item = new Random().nextInt(150);
            scores0.add(item);
            scores1.add(item);
        }
    }

    /**
     * <pre>for下标方式遍历性能高
     *
     * 每次调用 {@link java.util.LinkedList#get(int)} 都会遍历集合。故不要使用 for-i遍历。
     *
     * {@link java.util.ArrayList} 实现了 {@link java.util.RandomAccess} 接口（随机存取接口），
     * 这也就标志着ArrayList是一个可以随机存取的列表。
     * 对我们的ArrayList来说也就标志着其数据元素之间没有关联，
     * 即两个位置相邻的元素之间没有相互依赖和索引关系，可以随机访问和存储
     */
    @ParameterizedTest
    @MethodSource("provideData")
    @DisplayName("fori")
    void testForI(List<Integer> list) {
        int sum = 0;
        for (int i = 0; i < list.size(); i++) {
            sum += list.get(i);
        }
        System.out.println(sum);
    }

    /**
     * <pre>foreach 遍历是 iterator 迭代器的变形用法
     * 迭代器：
     *      提供一种方法访问一个容器对象中的各个元素，同时又无须暴露该对象的内部细节
     *
     * 也就是说对于{@link java.util.ArrayList} 需要先创建一个迭代器容器，
     * 然后屏蔽内部遍历细节，对外提供 hasNext、next 等方法。
     * 问题是 {@link java.util.ArrayList} 实现了 {@link java.util.RandomAccess} 接口，已表明元素之间本来没有关系，
     * 可是，为了使用迭代器就需要强制建立一种互相“知晓”的关系，
     * 比如上一个元素可以判断是否有下一个元素，以及下一个元素是什么等关系
     * 这也就是通过 foreach 遍历 {@link java.util.ArrayList} 耗时的原因
     */
    @ParameterizedTest
    @MethodSource("provideData")
    @DisplayName("foreach")
    void testForeach(List<Integer> list) {
        int sum = 0;
        for (Integer item : list) {
            sum += item;
        }
        System.out.println(sum);
    }

    @ParameterizedTest
    @MethodSource("provideData")
    @DisplayName("迭代器遍历")
    void testIterator(List<Integer> list) {
        int sum = 0;
        Iterator<Integer> iterator = list.iterator();
        while (iterator.hasNext()) {
            sum += iterator.next();
        }
        System.out.println(sum);
    }

    @ParameterizedTest
    @MethodSource("provideData")
    @DisplayName("随机存取的遍历下标；有序存取，使用foreach")
    void test(List<Integer> list) {
        int sum = 0;
        if (list instanceof RandomAccess) {
            //随机存取的遍历下标
            for (int i = 0; i < list.size(); i++) {
                sum += list.get(i);
            }
        } else {
            //有序存取，使用foreach
            for (Integer item : list) {
                sum += item;
            }
        }
        System.out.println(sum);
    }
}
