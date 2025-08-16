package cn.aiclr.jvm.suggestions.book.java151.chapter09;

/**
 * <pre>129.适当设置阻塞队列长度
 *
 * 注意：阻塞队列的长度是固定的
 *
 * 阻塞队列 {@link java.util.concurrent.BlockingQueue} 扩展了 {@link java.util.Queue}、{@link java.util.Collection} 接口，
 * 对元素的插入和提取使用了“阻塞”处理，
 * {@link java.util.Collection} 下的实现类一般都采用了长度自行管理方式（也就是变长）
 *
 * 阻塞队列和非阻塞队列一个重要区别：
 *      1）阻塞队列的容量是固定的，
 *      2）非阻塞队列则是变长的。
 *      3）阻塞队列可以在声明时指定队列的容量，
 *           若指定的容量，则元素的数量不可超过该容量，
 *           若不指定，队列的容量为 {@link java.lang.Integer#MAX_VALUE}
 * 原因：
 *      1）阻塞队列是为了容纳（或排序）多线程任务而存在的，
 *          其服务的对象是多线程应用，
 *      2）而非阻塞队列容纳的则是普通的数据元素
 *
 * 阻塞队列的这种机制对异步计算是非常有帮助的，
 * 例如我们定义深度为 100 的阻塞队列容纳 100 个任务，
 * 多个线程从该队列中获取任务并处理，
 * 当所有的线程都在繁忙，
 * 并且队列中任务数量已经为 100 时，也预示着系统运算压力非常巨大，
 * 而且处理结果的时间也会比较长，
 * 于是在第 101 个任务期望加入时，队列拒绝加入，而且返回异常，
 * 由系统自行处理，避免了异步运算的不可知性
 * 注意阅读下方的源码
 *
 * {@link java.util.AbstractQueue#add(Object)}
 * <code>
 *     public boolean add(E e) {
 *          //调用offer方法尝试写入
 *         if (offer(e))
 *             return true;
 *         else
 *             throw new IllegalStateException("Queue full");//写入失败，抛出队列已满异常
 *     }
 * </code>
 *
 * {@link java.util.concurrent.ArrayBlockingQueue#offer(Object)}
 * <code>
 *     public boolean offer(E e) {
 *         Objects.requireNonNull(e);
 *         final ReentrantLock lock = this.lock;
 *         //申请锁，只允许同时一个线程操作
 *         lock.lock();
 *         try {
 *              //元素计数器的计数与数组长度相同，标识队列已满
 *             if (count == items.length)
 *                 return false;
 *             else {
 *                  //队列未满，插入元素
 *                 enqueue(e);
 *                 return true;
 *             }
 *         } finally {
 *              //释放锁
 *             lock.unlock();
 *         }
 *     }
 * </code>
 *
 *
 *
 * {@link java.util.concurrent.ArrayBlockingQueue#put(Object)}
 *  如果应用期望无论等待多长时间都要运行该任务，不希望返回异常
 *  需要用 {@link java.util.concurrent.ArrayBlockingQueue#put(Object)}方法，
 *  它的作用也是把元素加入到队列中，
 *  但它与 add、offer方法不同，
 *  它会等待队列空出元素，再让自己加入进去，
 *  通俗地讲，{@link java.util.concurrent.ArrayBlockingQueue#put(Object)} 方法提供的是一种“无赖”式的插入，
 *  无论等待多长时间都要把该元素插入到队列中
 *
 *  {@link java.util.concurrent.ArrayBlockingQueue#put(Object)} 方法的目的就是确保元素肯定会加入到队列中，
 *  问题是此种等待是一个循环，会不停地消耗系统资源，
 *  当等待加入的元素数量较多时势必会对系统性能产生影响
 *  JDK 已经想到了这个问题，它提供了带有超时时间的 {@link java.util.concurrent.ArrayBlockingQueue#offer(Object, long, java.util.concurrent.TimeUnit)} 方法，
 *  其实现方法与 {@link java.util.concurrent.ArrayBlockingQueue#put(Object)} 比较类似，
 *  只是使用 {@link java.util.concurrent.locks.Condition#awaitNanos(long)} 法来判断当前线程已经等待了多少纳秒，
 *  超时则返回 false
 * <code>
 *     public void put(E e) throws InterruptedException {
 *         Objects.requireNonNull(e);
 *         final ReentrantLock lock = this.lock;
 *         //可中断锁
 *         lock.lockInterruptibly();
 *         try {
 *              //队列满，等待其他线程移除元素
 *             while (count == items.length)
 *                 notFull.await();
 *             //插入元素
 *             enqueue(e);
 *         } finally {
 *              //释放锁
 *             lock.unlock();
 *         }
 *     }
 * </code>
 *
 * 超时时间 {@link java.util.concurrent.ArrayBlockingQueue#offer(Object, long, java.util.concurrent.TimeUnit)}
 * <code>
 *     public boolean offer(E e, long timeout, TimeUnit unit)
 *         throws InterruptedException {
 *
 *         Objects.requireNonNull(e);
 *         long nanos = unit.toNanos(timeout);
 *         final ReentrantLock lock = this.lock;
 *         lock.lockInterruptibly();
 *         try {
 *             while (count == items.length) {
 *                 if (nanos <= 0L)
 *                     return false;
 *                 nanos = notFull.awaitNanos(nanos);
 *             }
 *             enqueue(e);
 *             return true;
 *         } finally {
 *             lock.unlock();
 *         }
 *     }
 * </code>
 */
public class Ey {
}

