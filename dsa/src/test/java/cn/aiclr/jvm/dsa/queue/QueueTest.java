package cn.aiclr.jvm.dsa.queue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("模拟队列")
class QueueTest {

    @Test
    @DisplayName("简单一次性队列")
    void simpleQueue() {
        SimpleQueue queue = new SimpleQueue(3);
        queue.addQueue(1);
        queue.addQueue(2);
        queue.addQueue(3);

        //超限
        queue.addQueue(4);

        queue.display();

        //FIFO 队列先进先出
        Assertions.assertEquals(1, queue.headQueue());
        Assertions.assertEquals(1, queue.getQueue());

        Assertions.assertEquals(2, queue.headQueue());
        Assertions.assertEquals(2, queue.getQueue());

        Assertions.assertEquals(3, queue.headQueue());
        Assertions.assertEquals(3, queue.getQueue());

        Assertions.assertThrows(RuntimeException.class, queue::getQueue);

        queue.display();
        //不支持重复入队
        queue.addQueue(4);
    }


    @Test
    @DisplayName("环形队列-可复用")
    void circularQueue() {
        CircularQueue queue = new CircularQueue(4);//实际只有三个位置
        queue.addQueue(1);
        queue.addQueue(2);
        queue.addQueue(3);
        queue.display();
        //rear 3
        //front 0
        Assertions.assertEquals(1,queue.getQueue());
        //f=1,r=3
        queue.addQueue(4);
        //r=0 f=1
        queue.display();
        Assertions.assertEquals(2,queue.getQueue());
        //f=2 r=0
        Assertions.assertEquals(3,queue.getQueue());
        //f=3 r=0
        queue.addQueue(5);
        //r=1 f=3
        queue.display();//2个
        Assertions.assertEquals(4,queue.getQueue());
        //f=1 r=1
        queue.addQueue(6);
        //f=1 r=2
        queue.addQueue(7);
        //f=1 r=0
        queue.display();
    }
}