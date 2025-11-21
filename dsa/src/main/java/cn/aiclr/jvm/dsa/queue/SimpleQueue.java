package cn.aiclr.jvm.dsa.queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <pre>模拟简单队列
 * 一次性队列,不能重复使用
 */
public class SimpleQueue {
    private static final Logger log = LoggerFactory.getLogger(SimpleQueue.class);

    private final int maxSize;//最大容量
    private int front;//头 出队
    private int rear;//尾 入队
    private final int[] arr;//存放数据

    public SimpleQueue(int maxSize) {
        this.maxSize = maxSize;
        arr = new int[maxSize];
        front = -1;//指向队列头前一个位置
        rear = -1;//指向队列尾前一个位置
    }

    /**
     * 入队 移动尾
     */
    public void addQueue(int n) {
        if (full()) {
            log.warn("队列已满");
            return;
        }
        rear++;
        arr[rear] = n;
    }

    /**
     * 出队 移动 头
     */
    public int getQueue() {
        if (isEmpty()) {
            throw new RuntimeException("空队列");
        }
        front++;
        return arr[front];
    }

    /**
     * 获取队列头元素
     */
    public int headQueue() {
        if (isEmpty()) {
            throw new RuntimeException("空队列");
        }
        return arr[front + 1];
    }

    /**
     *
     */
    public void display() {
        if (isEmpty()) {
            log.warn("空队列");
            return;
        }
        for (int i = front + 1; i <= rear; i++) {
            log.info("arr[{}]={}", i, arr[i]);
        }
    }

    /**
     * 满队逻辑：队尾在数组最后一个位置
     */
    public boolean full() {
        return rear == maxSize - 1;
    }

    /**
     * 判空逻辑：头尾相等（构造函数初始化时设定）
     */
    public boolean isEmpty() {
        return rear == front;
    }
}
