package cn.aiclr.jvm.dsa.linkedlist;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <pre>单向链表	Singly Linked List	SLL
 * 双向链表	Doubly Linked List	DLL
 * 循环链表	Circular Linked List	CLL
 * 双向循环链表	Doubly Circular Linked List	DCLL 或 CDLL
 */
public class Josephu {

    private static final Logger log = LoggerFactory.getLogger(Josephu.class);

    private HeroSingleNode first = new HeroSingleNode(-1);

    /**
     * 约瑟夫环-出圈
     *
     * @param startNo 从第几位开始数
     * @param no      数到该数字出圈
     */
    public void josephu(int startNo, int no) {
        if (first.next == null) {
            log.error("空单向环形链表");
            return;
        }
        //first 的前一个位置
        HeroSingleNode ahead = first.next;
        while (ahead.next != first) {
            ahead = ahead.next;
        }

        //先找开始数数位置
        for (int i = 1; i < startNo; i++) {
            first = first.next;
            ahead = ahead.next;
        }

        //开始报数
        while (ahead != first) {
            for (int i = 1; i < no; i++) {
                log.info("{}:{}",i, first);
                first = first.next;
                ahead = ahead.next;
            }
            log.info("{} 出圈", first);
            first = first.next;
            ahead.next = first;
        }
    }

    /**
     * 遍历打印
     */
    public void show() {
        if (first.next == null) {
            return;
        }
        HeroSingleNode cur = first;
        while (cur.next != first) {
            log.info("{}", cur);
            cur = cur.next;
        }
        log.info("{}", cur);
    }


    /**
     * 添加
     */
    public void add(HeroSingleNode newNode) {
        if (first.next == null) {
            first = newNode;
            newNode.next = first;
            return;
        }
        HeroSingleNode cur = first;
        while (cur.next != first) {
            cur = cur.next;
        }
        cur.next = newNode;
        newNode.next = first;
    }
}
