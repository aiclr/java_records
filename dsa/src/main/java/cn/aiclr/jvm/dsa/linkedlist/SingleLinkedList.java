package cn.aiclr.jvm.dsa.linkedlist;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Stack;

/**
 * <pre>单向链表	Singly Linked List	SLL
 * 双向链表	Doubly Linked List	DLL
 * 循环链表	Circular Linked List	CLL
 * 双向循环链表	Doubly Circular Linked List	DCLL 或 CDLL
 */
public class SingleLinkedList {

    private static final Logger log = LoggerFactory.getLogger(SingleLinkedList.class);

    private final HeroSingleNode head = new HeroSingleNode(0, "");

    /**
     * 逆序打印-百度面试  利用栈
     */
    public void stackShow() {
        if (head.next == null) {
            return;
        }
        Stack<HeroSingleNode> stack = new Stack<>();
        HeroSingleNode cur = head.next;
        while (cur != null) {
            stack.add(cur);
            cur = cur.next;
        }
        while (!stack.empty()) {
            log.info("{}", stack.pop());
        }
    }


    /**
     * <pre>链表反转-头插法-腾讯面试
     * 算法：依次将 cur 添加到 tempHead 头部，并将 tempHead.next 添加到 cur
     */
    public void reversList() {
        //空表，或只有一个节点
        if (head.next == null || head.next.next == null) {
            return;
        }
        //临时头部
        HeroSingleNode tempHead = new HeroSingleNode(0, "");
        //当前需要添加到 临时头部的节点
        HeroSingleNode cur = head.next;
        //临时暂存 cur.next
        HeroSingleNode temp;

        while (cur != null) {
            //暂存 当前节点的 next 节点
            temp = cur.next;
            //将当前节点 cur 的 next 指向临时头部的首节点
            cur.next = tempHead.next;
            //临时头部的首节点指向 当前节点 cur
            tempHead.next = cur;
            //将 当前节点 替换为当前节点的 next 节点
            cur = temp;
        }
        //将原始头部的首节点指向临时头部的首节点
        head.next = tempHead.next;
    }

    /**
     * 查找单链表中倒数第 k 个节点-新浪面试题
     */
    public HeroSingleNode findLastNode(int k) {
        if (head.next == null) {
            return null;
        }
        int length = getLength();

        if (k > length || k <= 0) {
            throw new IllegalArgumentException();
        }
        HeroSingleNode temp = head.next; //从第一个节点找
        int index = length - k; //倒数第 k 个节点,指针移动 index 次,遍历到前一个节点即可，因为 前一个节点的 next 即是目标.
        for (int i = 0; i < index; i++) {
            temp = temp.next;
        }
        return temp;
    }


    /**
     * 获取长度
     */
    public int getLength() {
        int length = 0;
        if (head.next == null) {
            return length;
        }
        HeroSingleNode temp = head.next;
        while (temp != null) {
            length++;
            temp = temp.next;
        }
        return length;
    }

    /**
     * 删除，定位到要删除节点的前一个节点
     */
    public void delete(HeroSingleNode target) {
        //空链
        if (head.next == null) {
            log.error("空单向链表");
            return;
        }
        //首节点命中
        if (head.next.no == target.no) {
            head.next = head.next.next;
            return;
        }
        //前一节点
        HeroSingleNode ahead = head.next;

        //是否找到目标节点标记
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
        } else {
            log.error("未找到 {} 节点", target.no);
        }
    }

    /**
     * 修改，定位到要修改的节点
     */
    public void update(HeroSingleNode newHeroNode) {
        //空链
        if (head.next == null) {
            log.error("空单向链表");
            return;
        }
        //首节点命中
        if (head.next.no == newHeroNode.no) {
            head.next.name = newHeroNode.name;
            return;
        }
        HeroSingleNode ahead = head.next;
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
            log.error("未找到 {} 节点", newHeroNode.no);
        }
    }


    /**
     * 有序 add，按顺序添加，定位到添加位置的前一个节点
     */
    public void addByNo(HeroSingleNode heroNode) {
        HeroSingleNode ahead = head;
        boolean flag = false;//是否重复标记
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
            log.info("{}已存在", heroNode);
        } else {
            heroNode.next = ahead.next;
            ahead.next = heroNode;
        }
    }

    /**
     * 无序 add，定位到 末尾节点
     */
    public void add(HeroSingleNode heroNode) {
        //temp 辅助指针，遍历链表
        HeroSingleNode temp = head;
        while (temp.next != null) {
            temp = temp.next;
        }
        temp.next = heroNode;
    }

    public void show() {
        //是否为空表
        if (head.next == null) {
            log.warn("空单向链表");
            return;
        }
        //temp 辅助指针，遍历链表
        HeroSingleNode temp = head.next;
        while (temp != null) {
            log.info("{}", temp);
            temp = temp.next;
        }
    }
}
