package cn.aiclr.jvm.dsa.hashtab;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmpLinkedList {

    private static final Logger log = LoggerFactory.getLogger(EmpLinkedList.class);

    private Emp head;

    public void add(Emp emp) {
        if (head == null) {
            head = emp;
            return;
        }
        Emp curEmp = head;
        while (curEmp.next != null) {
            curEmp = curEmp.next;
        }
        curEmp.next = emp;
    }

    public void show(int no) {
        if (head == null) {
            log.info("第{}条链表为空", no);
            return;
        }
        log.info("第{}条链表内容为:", no);
        Emp curEmp = head;
        do {
            log.info("{}", curEmp);
            curEmp = curEmp.next;
        } while (curEmp != null);
    }

    public Emp find(int id) {
        if (head == null) {
            return null;
        }
        Emp temp = head;
        while (true) {
            if (id == temp.id) {
                break;
            }
            if (temp.next == null) {
                temp = null;
                break;
            }
            temp = temp.next;
        }
        return temp;
    }

    public void del(int id) {
        if (head == null) {
            return;
        }
        if (head.id == id) {
            head = head.next;
            return;
        }
        Emp temp = head;
        while (true) {
            if (temp.next.id == id) {
                temp.next = temp.next.next;
                break;
            }
            if (temp.next.next == null) {
                break;
            }
            temp = temp.next;
        }
    }
}
