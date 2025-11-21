package cn.aiclr.jvm.dsa.linkedlist;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("双向链表")
class DoubleLinkedListTest {

    @Test
    void test() {
        DoubleLinkedList list = new DoubleLinkedList();
        list.show();
        list.add(new HeroDoubleNode(1, "song"));
        list.add(new HeroDoubleNode(2, "lin"));
        list.add(new HeroDoubleNode(3, "wu"));
        list.add(new HeroDoubleNode(4, "lu"));
        list.show();

        list.update(new HeroDoubleNode(1, "songjiang"));
        list.update(new HeroDoubleNode(5, "songjiang"));
        list.show();

        list.delete(new HeroDoubleNode(2, ""));
        list.show();

        list.addByNo(new HeroDoubleNode(2, "linchong"));
        list.addByNo(new HeroDoubleNode(5, "yang"));
        list.addByNo(new HeroDoubleNode(3, "wusong"));
        list.show();
    }
}
