package cn.aiclr.jvm.suggestions.book.java151.chapter09.ew;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 内部锁任务
 */
public class SyncTask extends Task implements Runnable {

    public SyncTask(AtomicInteger tickets) {
        super(tickets);
    }

    @Override
    public void run() {
        //内部锁 synchronized 锁定的是 SyncTask.class
        synchronized (SyncTask.class) {
            doSth();
        }
    }
}
