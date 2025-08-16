package cn.aiclr.jvm.suggestions.book.java151.chapter07;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class DwTest {

    @Test
    void test(){
        Assertions.assertSame(String.class,new String().getClass());
        Assertions.assertSame(String.class,"ABC".getClass());
        Assertions.assertSame(ArrayList.class,new ArrayList<String>().getClass());
    }
}
