package cn.aiclr.jvm.suggestions.book.java151.chapter07;

import cn.aiclr.jvm.suggestions.book.java151.chapter07.ec.Animal;
import cn.aiclr.jvm.suggestions.book.java151.chapter07.ec.DecorateAnimal;
import cn.aiclr.jvm.suggestions.book.java151.chapter07.ec.DigFeature;
import cn.aiclr.jvm.suggestions.book.java151.chapter07.ec.FlyFeature;
import cn.aiclr.jvm.suggestions.book.java151.chapter07.ec.Rat;
import org.junit.jupiter.api.Test;

class EcTest {

    @Test
    void test() {
        Animal jerry = new Rat();
        jerry = new DecorateAnimal(jerry, DigFeature.class);
        jerry.doStuff();
        jerry = new DecorateAnimal(jerry, FlyFeature.class);
        jerry.doStuff();
    }
}
