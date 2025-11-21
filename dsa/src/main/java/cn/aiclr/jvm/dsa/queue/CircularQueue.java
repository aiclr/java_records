package cn.aiclr.jvm.dsa.queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <pre>模拟环形队列
 * 重复使用
 * 通过取模来维持下标循环
 * 真实容量是 maxsize-1
 */
public class CircularQueue {

    private static final Logger log = LoggerFactory.getLogger(CircularQueue.class);

    private final int maxSize;//最大容量
    private int front;//头
    private int rear;//指向最后一个位置的后一个位置,
    private final int[] arr;//存放数据

    public CircularQueue(int maxSize) {
        this.maxSize = maxSize;
        arr = new int[maxSize];
    }

    public void addQueue(int n) {
        if (full()) {
            log.warn("满队列");
            return;
        }
        arr[rear] = n;
        //1%3=1 rear0+1->1
        //2%3=2 rear1+1->2
        //3%3=0 rear2+1->0
        rear = (rear + 1) % maxSize;
    }

    public int getQueue() {
        if (isEmpty()) {
            throw new RuntimeException("空队列");
        }
        int value = arr[front];
        //1%3=1
        //2%3=2
        //3%3=0
        front = (front + 1) % maxSize;
        return value;
    }

    public int headQueue() {
        if (isEmpty()) {
            throw new RuntimeException("空队列");
        }
        return arr[front];
    }

    public void display() {
        if (isEmpty()) {
            log.warn("空队列");
            return;
        }
        for (int i = front; i < front + size(); i++) {
            log.info("arr[{}]={}", i % maxSize, arr[i % maxSize]);
        }
    }

    /**
     * 真实容量
     */
    public int size() {
        return (rear + maxSize - front) % maxSize;
    }

    public boolean full() {
        //size为3,只有2个位置可以放置
        //0,1,2  (2+1)%3=0满
        //1,2,3  (3+1)%3=1满
        //2,3,4  (4+1)%3=2满
        return (rear + 1) % maxSize == front;
    }

    public boolean isEmpty() {
        return rear == front;
    }
}
