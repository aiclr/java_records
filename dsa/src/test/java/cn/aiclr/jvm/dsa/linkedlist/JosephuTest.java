package cn.aiclr.jvm.dsa.linkedlist;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("单向循环链表")
class JosephuTest {

    @DisplayName("约瑟夫环")
    @Test
    void josephu() {
        Josephu list = new Josephu();
        list.show();
        list.add(new HeroSingleNode(1));
        list.add(new HeroSingleNode(2));
        list.add(new HeroSingleNode(3));
        list.add(new HeroSingleNode(4));
        list.add(new HeroSingleNode(5));
        list.show();
        list.josephu(1, 3);
        list.show();
    }
}
