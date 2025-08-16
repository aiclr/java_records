package cn.aiclr.jvm.suggestions.book.java151.chapter09.ex;

/**
 * <pre>同步方法递归调用时，当前对象一直持有锁，不会每次递归都加锁
 */
public class RecursionSync {

    public static void main(String[] args) {
        Thread t = new Thread(new RecursionFoo());
        t.start();
    }

    /**
     * <pre>在运行时当前线程（Thread-0）获得了 RecursionFoo 实例对象的锁
     * （synchronized 虽然是标注在方法上的，但实际作用的是整个对象），
     * 也就是该线程持有了 RecursionFoo 实例对象的锁，所以它可以多次重入 fun 方法，也就是递归
     * 不会死锁
     * 可以这样来思考该问题，
     *      一个宝箱有 N 把钥匙，
     *      分别由 N 个海盗持有（也就是我们 Java 中的线程了），
     *      但是同一时间只能由一把钥匙打开宝箱，获取宝物，
     *      只有在上一个海盗关闭了宝箱（释放锁）后，
     *      其他海盗才能继续打开锁获取宝物，
     *      这里还有一个规则：
     *          一旦一个海盗打开了宝箱，
     *          则该宝箱内的所有宝物对他来说都是开放的，
     *          即使是“宝箱中的宝箱”（即内箱，递归）对他也是开放的
     */
    static class RecursionFoo implements Runnable {
        @Override
        public void run() {
            fun(10);
        }

        public synchronized void fun(int i) {
            if (--i > 0) {
                for (int j = 0; j < i; j++) {
                    System.out.print("*");
                }
                System.out.println(Thread.currentThread().getName() + "_>" + i);
                fun(i);
            }
        }
    }
}
