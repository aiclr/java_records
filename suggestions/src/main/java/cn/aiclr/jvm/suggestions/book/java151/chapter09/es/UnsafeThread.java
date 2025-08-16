package cn.aiclr.jvm.suggestions.book.java151.chapter09.es;

public class UnsafeThread implements Runnable {

    private volatile int count = 0;

    public int getCount() {
        return count;
    }

    /**
     * <pre>
     * run方法的主要逻辑是共享资源 count 的自加运算，
     * 为 count 变量加上了 volatile 关键字，确保是从主内存中读取和写入的
     *
     * UnsafeThread 类的消耗CPU计算是必须的，
     * 其目的是加重线程的负荷，以便出现单个线程抢占整个CPU资源的情景，
     * 否则很难模拟出 volatile 线程不安全的情况
     *
     * count++ 表示的是先取出 count 的值然后再加 1，也就是 count = count + 1，
     * 在某两个紧邻的时间片段内会发生如下神奇的事情：
     * （1）第一个时间片段
     *      A 线程获得执行机会，因为有关键字 volatile 修饰，所以它从主内存中获得 count 的最新值 998，
     *      接下来的事情又分为两种类型：
     *          1） 如果是单 CPU，此时调度器暂停 A 线程执行，出让执行机会给 B 线程，
     *              于是 B 线程也获得了 count 的最新值 998。
     *          1） 如果是多 CPU，此时线程 A 继续执行，
     *              而线程 B 也同时获得 count 的最新值 998。
     * （2）第二个时间片段
     *      1）如果是单 CPU，B 线程执行完加 1 动作（这是一个原子处理），count 的值为 999，
     *          由于是 volatile 类型的变量，所以直接写入主内存，
     *          然后 A 线程继续执行，计算的结果也是 999 ，重新写入主内存中。
     *      2）如果是多 CPU，A 线程执行完加 1 动作后修改主内存的变量 count 为 999，
     *          线程 B 执行完毕后也修改主内存中的变量为 999
     * 这两个时间片段执行完毕后，原本期望的结果为 1000，但运行后的值却为 999，
     * 这表示出现了线程不安全的情况。
     * 这也是我们要说明的：
     *      volatile 关键字并不能保证线程安全，
     *      它只能保证当线程需要该变量的值时能够获得最新的值，
     *      而不能保证多个线程修改的安全性
     */
    @Override
    public void run() {
        Math.hypot(Math.pow(92456789, 10), Math.cos(10));
        count++;
    }
}
