package cn.aiclr.jvm.suggestions.book.java151.chapter05;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

class DcTest {

    private static final Logger logger = LoggerFactory.getLogger(DcTest.class);

    @Test
    @DisplayName("修改 SortedSet 内元素不会重新排序")
    void testChangeSortedSet() {
        SortedSet<Dc> set = new TreeSet<>();
        set.add(new Dc(180));
        set.add(new Dc(175));
        logger.info("{}", set);
        //修改元素不会重新排序
        set.first().setHeight(185);
        logger.info("{}", set);
    }

    /**
     * <pre>{@link java.util.TreeSet} {@code public TreeSet(Collection<? extends E> c)} 会重新排序
     * <code>
     *      public TreeSet(Collection<? extends E> c) {
     *         this();
     *         addAll(c);
     *     }
     * </code>
     */
    @Test
    @DisplayName("new TreeSet<>(list) 会重新排序")
    void testCopyAndSort() {
        SortedSet<Dc> set = new TreeSet<>();
        set.add(new Dc(180));
        set.add(new Dc(175));
        logger.info("{}", set);
        //修改元素不会重新排序
        set.first().setHeight(185);
        logger.info("{}", set);
        //重新排序
        List<Dc> list = new ArrayList<>(set);
        set = new TreeSet<>(list);
        logger.info("{}", set);
    }

    /**
     * <pre>{@link java.util.TreeSet} 的 {@code public TreeSet(SortedSet<E> s)} 这个构造函数只是原 Set 的浅拷贝，如果里面有相同的元素，不会重新排序
     * <code>
     *      public TreeSet(SortedSet<E> s) {
     *         this(s.comparator());
     *         addAll(s);
     *     }
     * </code>
     */
    @Test
    @DisplayName("new TreeSet<>(set) 浅拷贝不会重新排序")
    void testCopyButNotSort() {
        SortedSet<Dc> set = new TreeSet<>();
        set.add(new Dc(180));
        set.add(new Dc(175));
        logger.info("{}", set);
        //修改元素不会重新排序
        set.first().setHeight(185);
        logger.info("{}", set);
        //浅拷贝不重新排序
        set = new TreeSet<>(set);
        logger.info("{}", set);
    }
}
