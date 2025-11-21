package cn.aiclr.jvm.dsa.hashtab;

/**
 * <pre>哈希表底层实现方式：
 *  数组 + 链表
 *  数组 + 二叉树
 */
public class SimpleHashTab {
    private EmpLinkedList[] empLinkedLists;
    private int size;

    public SimpleHashTab(int size) {
        this.size = size;
        empLinkedLists = new EmpLinkedList[size];
        for (int i = 0; i < size; i++) {
            empLinkedLists[i] = new EmpLinkedList();
        }
    }

    public void add(Emp emp) {
        int empLinkedListNo = hashFun(emp.id);
        empLinkedLists[empLinkedListNo].add(emp);
    }

    public void show() {
        for (int i = 0; i < size; i++) {
            empLinkedLists[i].show(i);
        }
    }

    public Emp find(int id) {
        return empLinkedLists[hashFun(id)].find(id);
    }

    public void del(int id) {
        empLinkedLists[hashFun(id)].del(id);
    }

    //散列函数
    public int hashFun(int id) {
        return id % size;
    }
}
