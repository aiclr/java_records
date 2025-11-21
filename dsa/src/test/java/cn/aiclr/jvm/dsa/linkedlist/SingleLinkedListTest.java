package cn.aiclr.jvm.dsa.linkedlist;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("单向链表")
class SingleLinkedListTest {

    @DisplayName("无序添加")
    @Test
    void add_update_show() {
        SingleLinkedList list = new SingleLinkedList();
        list.show();
        list.add(new HeroSingleNode(2, "lin"));
        list.add(new HeroSingleNode(1, "song"));
        list.add(new HeroSingleNode(3, "wu"));
        list.show();
        list.update(new HeroSingleNode(1, "songjiang"));
        list.update(new HeroSingleNode(3, "wusong"));
        list.show();
    }

    @DisplayName("有序添加")
    @Test
    void add_by_no_update_show_last() {
        SingleLinkedList list = new SingleLinkedList();
        list.show();
        Assertions.assertEquals(0, list.getLength());
        list.addByNo(new HeroSingleNode(2, "lin"));
        list.addByNo(new HeroSingleNode(1, "song"));
        list.addByNo(new HeroSingleNode(2, "wu"));
        list.addByNo(new HeroSingleNode(3, "wu"));

        Assertions.assertEquals(3, list.getLength());
        Assertions.assertEquals("lin", list.findLastNode(2).name);
        Assertions.assertEquals("wu", list.findLastNode(1).name);
        Assertions.assertEquals("song", list.findLastNode(3).name);
        Assertions.assertEquals(2, list.findLastNode(2).no);
        Assertions.assertEquals(3, list.findLastNode(1).no);
        Assertions.assertEquals(1, list.findLastNode(3).no);

        list.show();
        list.update(new HeroSingleNode(1, "songjiang"));
        list.update(new HeroSingleNode(3, "wusong"));
        list.show();

        Assertions.assertEquals(3, list.getLength());
        list.delete(new HeroSingleNode(3, "wusong"));
        list.delete(new HeroSingleNode(3, ""));
        list.delete(new HeroSingleNode(1, "songjiang"));
        list.show();
        Assertions.assertEquals(1, list.getLength());
    }

    @DisplayName("链表反转_逆序打印")
    @Test
    void revers_stack_show() {
        SingleLinkedList list = new SingleLinkedList();
        list.add(new HeroSingleNode(1, "lin"));
        list.add(new HeroSingleNode(2, "song"));
        list.add(new HeroSingleNode(3, "wu"));
        list.add(new HeroSingleNode(4, "lu"));
        list.show();
        list.stackShow();

        list.reversList();
        list.show();
        list.stackShow();
    }
}
