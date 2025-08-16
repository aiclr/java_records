package cn.aiclr.jvm.suggestions.book.java151.chapter07;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class DoTest {
    @Test
    @DisplayName("泛型的class对象是相同的")
    void testGenericClass() {
        List<String> stringList = new ArrayList<>();
        List<Integer> integerList = new ArrayList<>();
        Assertions.assertSame(stringList.getClass(), integerList.getClass());
    }

    @Test
    @DisplayName("泛型数组初始化时不能声明泛型类型")
    @Disabled
    void testInit() {
        List<String>[] listArray = null;
//        listArray=new List<String>[];//编译不通过
    }

    @Test
    @DisplayName("instanceof 不允许存在泛型参数")
    @Disabled
    void testInstanceof() {
        List<String> l3 = new ArrayList<>();
//        System.out.println(list instanceof List<String>);//编译不通过
    }
}
