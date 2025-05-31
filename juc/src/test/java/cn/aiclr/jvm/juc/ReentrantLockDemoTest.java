package cn.aiclr.jvm.juc;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReentrantLockDemoTest {

    @Test
    @DisplayName("按顺序循环打印线程名称")
    void loopTest() throws InterruptedException {
        ReentrantLockDemo loop = new ReentrantLockDemo();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                loop.showA();
            }
        }, "A").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                loop.showB();
            }
        }, "B").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                loop.showC();
            }
        }, "C").start();
    }
}