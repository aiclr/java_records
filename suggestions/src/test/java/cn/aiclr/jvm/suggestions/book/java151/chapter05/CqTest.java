package cn.aiclr.jvm.suggestions.book.java151.chapter05;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Vector;

class CqTest {

    /**
     * <pre> {@link java.util.ArrayList}和  {@link java.util.Vector} 两者都实现了 {@link java.util.List} 接口，也都继承了 {@link java.util.AbstractList} 抽象类
     * {@link java.util.AbstractList#equals(Object)}
     * <code>public boolean equals(Object o) {
     *         if (o == this)
     *             return true;
     *         //是否是List列表，只要实现List接口即可
     *         if (!(o instanceof List))
     *             return false;
     *         //迭代器访问list所有元素
     *         ListIterator<E> e1 = listIterator();
     *         ListIterator<?> e2 = ((List<?>) o).listIterator();
     *         //遍历两个list元素
     *         while (e1.hasNext() && e2.hasNext()) {
     *             E o1 = e1.next();
     *             Object o2 = e2.next();
     *             //只要存在着不相等的就退出
     *             if (!(o1==null ? o2==null : o1.equals(o2)))
     *                 return false;
     *         }
     *         //长度是否也相等
     *         return !(e1.hasNext() || e2.hasNext());
     *     }
     * </code>
     */
    @Test
    void testDef() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("A");

        Vector<String> vector = new Vector<>();
        vector.add("A");

        Assertions.assertEquals(arrayList, vector);
        Assertions.assertEquals(vector, arrayList);
    }

    @Test
    void testSize() {
        ArrayList<String> arrayList = new ArrayList<>(1);
        arrayList.add("A");

        Vector<String> vector = new Vector<>(2);
        vector.add("A");

        Assertions.assertEquals(arrayList, vector);
        Assertions.assertEquals(vector, arrayList);
    }
}
