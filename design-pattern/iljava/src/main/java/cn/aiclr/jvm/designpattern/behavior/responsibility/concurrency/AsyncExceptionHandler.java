package cn.aiclr.jvm.designpattern.behavior.responsibility.concurrency;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AsyncExceptionHandler<T> implements Thread.UncaughtExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(AsyncExceptionHandler.class);

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        log.error("{}{}: 出现异常===>{}", thread.getName(), thread.threadId(), throwable.getMessage(), throwable);
        //todo 重启线程
    }
}
