package cn.aiclr.jvm.suggestions.book.java151.chapter09.er;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TcpServer implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(TcpServer.class);

    /**
     * 创建后即运行
     */
    public TcpServer(int times) {
        Thread t = new Thread(this);
        t.setUncaughtExceptionHandler(new TcpServerExceptionHandler(times));
        t.start();
    }

    @Override
    public void run() {
        //正常业务运行3秒
        for (int i = 0; i < 3; i++) {
            try {
                Thread.sleep(1000);
                logger.info("系统正常运行 {} 秒", i);
            } catch (InterruptedException e) {
                logger.error("{}", e.getMessage(), e);
            }
        }
        //抛出异常
        throw new RuntimeException("手动抛出异常");
    }

    /**
     * 异常处理器
     */
    private static class TcpServerExceptionHandler implements Thread.UncaughtExceptionHandler {

        private int times;

        public TcpServerExceptionHandler(int times) {
            this.times = times;
        }

        @Override
        public void uncaughtException(Thread t, Throwable e) {
            //记录线程异常信息
            if (times > 0) {
                logger.error("线程【{}】出现异常，将自动重启...{}", t.getName(), e.getMessage(), e);
                times--;
                new TcpServer(times);
            } else
                logger.error("线程【{}】出现异常，重启超过重启次数，不再重启！！！{}", t.getName(), e.getMessage(), e);
        }
    }
}
