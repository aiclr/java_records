package cn.aiclr.jvm.suggestions.book.java151.chapter05;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.List;

class CtTest {

    /**
     * <pre>subList 是原列表 arrayList 的一个视图，
     * 原数据集 arrayList 修改了，
     * 但是 subList 子列表不会重新生成一个新列表（这点与数据库视图是不相同的），
     * 后面在对子列表继续操作时，就会检测到修改计数器与预期的不相同，于是就抛出了并发修改异常
     */
    @Test
    void testModifySubList() {
        List<String> arrayList = new ArrayList<>();
        arrayList.add("A");
        arrayList.add("B");
        List<String> subList = arrayList.subList(0, 1);
        //此时源集合 未修改
        subList.add("C");

        //修改源集合
        arrayList.add("D");
        Assertions.assertThrows(ConcurrentModificationException.class, () -> subList.add("E"));
    }

    @Test
    void testSubList() {
        List<String> arrayList = new ArrayList<>();
        arrayList.add("A");
        arrayList.add("B");
        List<String> subList = arrayList.subList(0, 2);
        subList.add("C");

        Assertions.assertEquals(arrayList, subList);
        Assertions.assertNotSame(arrayList, subList);

        //生成只读集合
        List<String> unmodifyList = Collections.unmodifiableList(arrayList);
        //修改只读集合会抛异常
        Assertions.assertThrows(UnsupportedOperationException.class, () -> unmodifyList.add("E"));

        //修改源集合
        arrayList.add("D");
        //再修改子集合，会抛出并发修改异常
        Assertions.assertThrows(ConcurrentModificationException.class,()->subList.add("E"));

        Assertions.assertEquals(arrayList, unmodifyList);
        Assertions.assertNotSame(arrayList, unmodifyList);
    }
}
