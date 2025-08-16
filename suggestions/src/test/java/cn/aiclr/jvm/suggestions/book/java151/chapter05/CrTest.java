package cn.aiclr.jvm.suggestions.book.java151.chapter05;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class CrTest {
    @Test
    @DisplayName("修改 List#subList 子列表会影响源列表")
    void testSubList() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("A");
        arrayList.add("B");

        //构造一个包含 arrayList 列表的字符串列表
        ArrayList<String> arrayList1 = new ArrayList<>(arrayList);

        Assertions.assertNotSame(arrayList, arrayList1);
        Assertions.assertEquals(arrayList, arrayList1);

        //使用subList 生成相同的列表
        List<String> arrayList2 = arrayList.subList(0, arrayList.size());
        arrayList2.add("C");

        Assertions.assertNotEquals(arrayList, arrayList1);
        Assertions.assertEquals(arrayList, arrayList2);
        Assertions.assertNotSame(arrayList, arrayList2);
    }

    /**
     * <pre>{@link String#substring(int, int)}
     * <code>
     *      if (beginIndex == 0 && endIndex == length) {
     *             return this;
     *      }
     * </code>
     */
    @Test
    @DisplayName("String#substring")
    void testStringSubstring() {
        //常量池
        String str = "ABC";
        //新对象 且不在常量池
        String str1 = new String(str);

        Assertions.assertNotSame(str, str1);
        Assertions.assertEquals(str, str1);

        //截取子字符串长度与源字符串一样时，substring 直接 return this
        String str2 = str.substring(0);
        Assertions.assertSame(str, str2);

        //新对象
        String str3 = str.substring(0, 2) + "C";
        Assertions.assertNotSame(str, str3);
        Assertions.assertEquals(str, str3);
    }
}
