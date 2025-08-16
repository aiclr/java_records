package cn.aiclr.jvm.suggestions.book.java151.chapter09.ep;

public class SafeStopThread extends Thread {
    int a = 0;

    @Override
    public void run() {
        synchronized (SafeStopThread.class) {
            while (!isInterrupted()) {
                String tn = Thread.currentThread().getName();
                System.out.println(tn + ":a=" + a);
                if (isInterrupted())
                    break;
            }
        }
    }

    /**
     * <pre>interrupt 方法不能终止一个线程状态，
     * 它只会改变中断标志位
     *      （如果在 t1.interrupt() 前后输出 t1.isInterrupted() 则会发现分别输出了 false 和 true ），
     * 如果需要终止该线程，还需要自行进行判断
     */
    public static void main(String[] args) {
        SafeStopThread t1 = new SafeStopThread();
        t1.start();
        System.out.println(t1.isInterrupted());
        t1.interrupt();
        System.out.println(t1.isInterrupted());
    }
}
