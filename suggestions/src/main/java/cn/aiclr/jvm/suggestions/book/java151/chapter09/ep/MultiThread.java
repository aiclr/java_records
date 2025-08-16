package cn.aiclr.jvm.suggestions.book.java151.chapter09.ep;

/**
 * <pre> stop 方法会破坏原子逻辑
 *
 * 1）线程 t1 启动，并执行 run 方法，由于没有其他线程持同步代码块的锁，所以 t1 线程执行自加后执行到 sleep 方法即开始休眠，
 *      此时 a=1。
 * 2）JVM 又启动了 5 个线程，也同时运行 run 方法，由于 synchronized 关键字的阻塞作用，这 5 个线程不能执行自增和自减操作，
 *      等待 t1 线程锁释放。
 * 3）主线程执行了 t1.stop 方法，终止了 t1 线程，
 *      注意，由于 a 变量是所有线程共享的，所以其他 5 个线程获得的 a 变量也是 1。
 * 4）其他 5 个线程依次获得 CPU 执行机会，打印出 a 值
 *
 * 原本期望 synchronized 同步代码块中的逻辑都是原子逻辑，
 * 不受外界线程的干扰，
 * 但是结果却出现原子逻辑被破坏的情况，
 * 这也是 stop 方法被废弃的一个重要原因：破坏了原子逻辑
 */
public class MultiThread implements Runnable {
    int a = 0;

    @Override
    public void run() {
        synchronized (MultiThread.class) {
            a++;
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            a--;
            String tn = Thread.currentThread().getName();
            System.out.println(tn + ":a=" + a);
        }
    }

    @SuppressWarnings("removal")
    public static void main(String[] args) {
        MultiThread t = new MultiThread();
        Thread t1 = new Thread(t);
        t1.start();

        for (int i = 0; i < 5; i++) {
            new Thread(t).start();
        }
        t1.stop();
    }
}
