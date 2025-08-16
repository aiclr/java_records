package cn.aiclr.jvm.suggestions.book.java151.chapter09.ep;


/**
 * <pre>120.不使用 {@link java.lang.Thread#stop()} 方法停止线程
 *
 * 线程启动完毕后，在运行时可能需要终止，
 * Java 提供的终止方法只有一个 {@link java.lang.Thread#stop()}，
 * 但是不建议使用这个方法，因为它有以下三个问题
 * 1）stop 方法是过时的
 *      从 Java 编码规则来说，已经过时的方法不建议采用
 * 2）stop 方法会导致代码逻辑不完整
 *      stop 方法是一种“恶意”的中断，一旦执行 stop 方法，
 *      即终止当前正在运行的线程，不管线程逻辑是否完整，这是非常危险的
 * 3）stop 方法会破坏原子逻辑
 *
 * 如果期望终止一个正在运行的线程，
 *      则不能使用已经过时的 stop 方法，需要自行编码实现，
 *      如此即可保证原子逻辑不被破坏，代码逻辑不会出现异常。
 * 如果我们使用的是线程池（比如 {@link java.util.concurrent.ThreadPoolExecutor} 类），
 *      那么可以通过 {@link java.util.concurrent.ThreadPoolExecutor#shutdown()} 方法逐步关闭池中的线程，它采用的是比较温和、安全的关闭线程方法，完全不会产生类似 stop 方法的弊端
 */
public class Ep {
    /**
     * <pre> 2）stop 方法会导致代码逻辑不完整
     *
     * 子线程是一个 lambda 匿名内部类，
     * 它的 run 方法在执行时会休眠 1 秒钟，
     * 然后再执行后续的逻辑，
     * 而主线程则是休眠 0.1 秒后终止子线程的运行，
     * 也就是说，JVM 在执行 thread.stop() 时，子线程还在执行 sleep(1000)，
     * 此时 stop 方法会清除栈内信息，结束该线程，这也就导致了 run 方法的逻辑不完整，
     * 输出语句 println 代表的是一段逻辑，可能非常重要，比如子线程的主逻辑、资源回收、情景初始化等，
     * 但是因为 stop 线程了，这些就都不再执行，
     * 于是就产生了业务逻辑不完整的情况。这是极度危险的，因为我们不知道子线程会在什么时候被终止，
     * stop 连基本的逻辑完整性都无法保证。
     * 而且此种操作也是非常隐蔽的，
     * 子线程执行到何处会被关闭很难定位，这为以后的维护带来了很多麻烦
     */
    @SuppressWarnings({"removal"})
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            try {
                //子线程休眠1秒
                Thread.sleep(1000);
            } catch (Exception e) {

            }
            //此处如果是子线程的主逻辑、资源回收、情景初始化等，不会被执行
            System.out.println("此处不会执行");
        });
        thread.start();
        try {
            //主线程休眠0.1秒
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //子线程停止
        thread.stop();
    }

}