package cn.aiclr.jvm.suggestions.book.java151.chapter05;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class CnTest {

    @Test
    @DisplayName("Arrays.asList 创建的 List 不支持 add、remove 等修改操作")
    void test() {
        List<String> list = Arrays.asList("张三", "李四", "王二");
        Assertions.assertThrows(UnsupportedOperationException.class, () -> list.add("赵一"));
        Assertions.assertThrows(UnsupportedOperationException.class, () -> list.remove(2));
    }
}
