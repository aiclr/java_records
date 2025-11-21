package cn.aiclr.jvm.dsa.linkedlist;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <pre>单向链表	Singly Linked List	SLL
 * 双向链表	Doubly Linked List	DLL
 * 循环链表	Circular Linked List	CLL
 * 双向循环链表	Doubly Circular Linked List	DCLL 或 CDLL
 */
public class DoubleLinkedList {

    private static final Logger log = LoggerFactory.getLogger(DoubleLinkedList.class);

    private final HeroDoubleNode head = new HeroDoubleNode(0, "");

    /**
     * 有序 add，按顺序添加，定位到添加位置的前一个节点
     */
    public void addByNo(HeroDoubleNode heroNode) {

        HeroDoubleNode ahead = head;

        boolean flag = false;

        while (true) {
            //是否为空表
            if (ahead.next == null) {
                break;
            }

            if (ahead.next.no > heroNode.no) {
                break;
            }

            if (ahead.next.no == heroNode.no) {
                flag = true;
                break;
            }

            ahead = ahead.next;
        }

        if (flag) {
            log.info("{} 已存在", heroNode);
        } else {
            //注意下面语句的顺序
            heroNode.next = ahead.next;
            if (ahead.next != null) {
                ahead.next.pre = heroNode;
            }
            heroNode.pre = ahead;
            ahead.next = heroNode;
        }

    }


    /**
     * 删除，定位到要删除节点的前一个节点
     */
    public void delete(HeroDoubleNode target) {
        //为空
        if (head.next == null) {
            log.warn("空双向链表");
            return;
        }

        if (head.next.no == target.no) {
            //指向新节点
            head.next = head.next.next;
            if (head.next != null) {
                //新节点 pre 指向前一节点
                head.next.pre = head;
            }
            return;
        }

        HeroDoubleNode ahead = head.next;
        boolean flag = false;
        while (ahead.next != null) {
            if (ahead.next.no == target.no) {
                flag = true;
                break;
            }
            ahead = ahead.next;
        }
        if (flag) {
            ahead.next = ahead.next.next;
            if (ahead.next != null) {
                ahead.next.pre = ahead;
            }
        } else {
            log.warn("未找到{}节点", target.no);
        }
    }

    /**
     * 修改，定位到要修改的节点
     */
    public void update(HeroDoubleNode newHeroNode) {
        //为空
        if (head.next == null) {
            log.warn("空双向链表");
            return;
        }
        if (head.next.no == newHeroNode.no) {
            head.next.name = newHeroNode.name;
            return;
        }

        HeroDoubleNode ahead = head.next;
        boolean flag = false;
        while (ahead.next != null) {
            if (ahead.next.no == newHeroNode.no) {
                flag = true;
                break;
            }
            ahead = ahead.next;
        }
        if (flag) {
            ahead.next.name = newHeroNode.name;
        } else {
            log.warn("未找到{}节点", newHeroNode.no);
        }
    }

    /**
     * 无序add，定位到 末尾节点
     */
    public void add(HeroDoubleNode heroNode) {
        //temp 辅助指针，遍历链表
        HeroDoubleNode temp = head;
        while (temp.next != null) {
            temp = temp.next;
        }
        //形成双向链表
        temp.next = heroNode;
        heroNode.pre = temp;
    }

    /**
     * 遍历
     */
    public void show() {
        //是否为空表
        if (head.next == null) {
            log.warn("空双向链表");
            return;
        }
        //temp 辅助指针，遍历链表
        HeroDoubleNode temp = head.next;
        //遍历完链表
        while (temp != null) {
            log.info("{}", temp);
            temp = temp.next;
        }
    }
}
