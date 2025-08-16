package cn.aiclr.jvm.suggestions.book.java151.chapter09.ew;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 任务要具备多线程能力时必须实现 Runnable 接口
 */
public class Task {

    private static final Logger logger = LoggerFactory.getLogger(Task.class);

    protected volatile AtomicInteger tickets;

    public Task(AtomicInteger tickets) {
        this.tickets = tickets;
    }

    public void doSth() {
        try {
            //模拟方式，使用sleep方法将线程的状态从运行状态转变为等待状态
            Thread.sleep(10);
        } catch (InterruptedException e) {
            //异常处理
            logger.error("{}", e.getMessage(), e);
        }
        tickets.decrementAndGet();
        logger.info("线程名称 = {},执行时间点 = {}，tickets={}", Thread.currentThread().getName(), Calendar.getInstance().get(Calendar.MILLISECOND), tickets);
    }
}
