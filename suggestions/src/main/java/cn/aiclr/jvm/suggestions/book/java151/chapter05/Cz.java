package cn.aiclr.jvm.suggestions.book.java151.chapter05;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>78.减少 {@link java.util.HashMap} 中元素的数量
 *
 * 注意：
 *      尽量让 {@link java.util.HashMap} 中的元素少量并简单
 *
 * 在系统开发中，我们经常会使用 {@link java.util.HashMap} 作为数据集容器，或者是用缓冲池来处理，一般很稳定，
 * 但偶尔也会出现内存溢出的问题（如 {@link java.lang.OutOfMemoryError} 错误），
 * 而且这经常是与 {@link java.util.HashMap} 有关的，
 * 比如我们使用缓冲池操作数据时，大批量的增删查改操作就可能会让内存溢出，下面建立一段模拟程序，重现该问题
 *
 * {@link java.util.HashMap} 底层的数组变量 {@link java.util.HashMap#table}
 * 它是 {@link java.util.HashMap.Node} 类型的数组，保存的是一个一个的键值对（在我们的例子中 {@link java.util.HashMap.Node} 是由两个 {@link java.lang.String} 类型组成的）。
 * 对我们的例子来说，{@link java.util.HashMap} 比 {@link java.util.ArrayList} 多了一次封装，把 {@link java.lang.String} 类型的键值对转换成 {@link java.util.Map.Entry} 对象后再放入数组，
 * 这就多了40万个对象，这应该是问题产生的第一个原因
 *
 * {@link java.util.HashMap} 的长度也是可以动态增加的，它的扩容机制与 {@link java.util.ArrayList} 稍有不同
 * 在插入键值对时，会做长度校验，
 * 如果大于或等于阀值（{@link java.util.HashMap#threshold} 变量），则数组长度增大一倍。
 * 默认是当前长度与加载因子的乘积
 * <code>
 *      final V putVal(int hash, K key, V value, boolean onlyIfAbsent,
 *                    boolean evict) {
 *                    //...
 *                      if (++size > threshold)
 *                          resize();
 *                    //...
 *      }
 * </code>
 *
 * 默认的加载因子 {@link java.util.HashMap#loadFactor} 是 0.75，{@code static final float DEFAULT_LOAD_FACTOR = 0.75f;}
 * 也就是说只要 {@link java.util.HashMap#size} 大于数组长度的 0.75 倍时，就开始扩容，
 * 经过计算得知（怎么计算的？查找2的N次幂大于40万的最小值即为数组的最大长度，再乘以0.75就是最后一次扩容点，计算的结果是N=19）
 * 在 map 的 size 为 393216 时，符合了扩容条件，
 * 于是 393216 个元素准备开始大搬家，
 * 那首先要申请一个长度为 1048576（当前长度的两倍嘛，2的19次方再乘以2，即2的20次方）的数组，
 * 但问题是此时剩余的内存只有 7MB 了，不足以支撑此运算，于是就报内存溢出了！这是第二个原因，也是最根本的原因！
 *
 * {@link java.util.ArrayList} 的扩容策略，它是在小于数组长度的时候才会扩容 1.5 倍，
 * 经过计算得知，{@link java.util.ArrayList} 的 size 在超过 80 万后（一次加两个元素，40万的两倍），最近的一次扩容会是在 size 为 1005308 时，
 * 也就是说，如果程序设置了增加元素的上限为 502655，同样会报内存溢出，
 * 因为它也要申请一个 1507963 长度的数组，如果没这么大的地方，就会报错
 *
 * {@link java.util.HashMap} 比 {@link java.util.ArrayList}
 * 1. 多了一个层 {@link java.util.Map.Entry} 的底层对象封装，多占用了内存，
 * 2. 并且它的扩容策略是2倍长度的递增，
 * 3. 同时还会依据阀值判断规则进行判断，
 * 因此相对于 {@link java.util.ArrayList} 来说，它就会先出现内存溢出
 *
 * 可以在声明时指定 {@link java.util.HashMap} 的默认长度和加载因子来减少此问题的发生。
 * 可以不再频繁地进行数组扩容，但仍然避免不了内存溢出问题，因为键值对的封装对象 {@link java.util.Map.Entry} 还是少不了的，
 * 内存依然增长较快
 */
public class Cz {

    private static final Logger logger = LoggerFactory.getLogger(Cz.class);

    /**
     * <pre>模拟内存溢出
     *
     * 启动参数设置 -Xms63M -Xmx63M
     * 异常信息
     * Exception in thread "main" java.lang.OutOfMemoryError: GC overhead limit exceeded
     * at java.util.HashMap.newNode(HashMap.java:1747)
     * at java.util.HashMap.putVal(HashMap.java:631)
     * at java.util.HashMap.put(HashMap.java:612)
     * at cn.aiclr.jvm.suggestions.book.java151.chapter05.Cz.main(Cz.java:125)
     *
     * 错误解释:
     *      GC overhead limit exceeded 异常  jdk1.6 新增的错误类型。JVM 的一种保护机制，默认情况下：
     *      如果 JVM 检测到超过 98% 的 CPU 时间用在垃圾回收（GC）上;
     *      并且每次 GC 回收的内存不足堆大小的 2%;
     *      那么 JVM 就会抛出这个错误，终止程序。
     *
     * 这通常意味着程序：
     *      创建了大量对象；或者没有及时释放不再使用的对象；
     *      导致堆内存耗尽，GC 不断尝试回收却收效甚微。
     *
     * 频繁的进行内存回收(最起码已经进行了5次连续的垃圾回收)，
     * JVM 就会曝出 java.lang.OutOfMemoryError: GC overhead limit exceeded 错误
     * 如果没有这个异常，经过垃圾回收释放的 2% 可用内存空间会快速的被填满，迫使 GC 再次执行，
     * 出现频繁的执行 GC 操作，服务器会因为频繁的执行 GC 操作而达到 100% 的使用率，服务器运行变慢，
     * 应用系统会出现卡死现象，平常只需几毫秒就可以执行的操作，现在需要更长时间，甚至是好几分钟才可以完成
     *
     * 解决方法：
     * 1、增加 heap 堆内存。(OpenJDK21 80M即不报错)
     * 2、增加堆内存后错误依旧，获取 heap 内存快照，使用 Eclipse MAT 工具，找出内存泄露发生的原因并进行修复。
     * 3、优化代码以使用更少的内存或重用对象，而不是创建新的对象，从而减少垃圾收集器运行的次数。如果代码中创建了许多临时对象(例如在循环中)，应该尝试重用它们。
     * 4、升级 JDK 到 1.8，最起码也是 1.7，并使用 G1 垃圾回收算法。
     * 5、除了使用命令 -xms1g -xmx2g 设置堆内存之外，尝试在启动脚本中加入配置:
     *      <code>
     *      -XX:+UseG1GC #（增加这个即可实现作者的异常）
     *      -XX:G1HeapRegionSize=n
     *      -XX:MaxGCPauseMillis=m
     *      -XX:ParallelGCThreads=n
     *      -XX:ConcGCThreads=n
     *      </code>
     */
    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
        final Runtime rt = Runtime.getRuntime();
        rt.addShutdownHook(
                new Thread(() -> {
                    long heapMaxSize = rt.maxMemory() >> 20;
                    logger.info("最大可用内存：{}MB", heapMaxSize);
                    long total = rt.totalMemory() >> 20;
                    logger.info("堆内存大小：{}MB", total);
                    long free = rt.freeMemory() >> 20;
                    logger.info("空闲内存：{}MB", free);
                })
        );
        //放入近40万键值对
        for (int i = 0; i < 393217; i++) {
            //仅是增加
            map.put("key" + i, "value" + i);
        }
        //Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
        //	at java.base/java.util.HashMap.resize(HashMap.java:710)
        //	at java.base/java.util.HashMap.putVal(HashMap.java:669)
        //	at java.base/java.util.HashMap.put(HashMap.java:618)
        //	at cn.aiclr.jvm.suggestions.book.java151.chapter05.Cz.main(Cz.java:125)
        // 最大可用内存：64M
        // 对内存大小：64M
        // 空闲内存：2M
    }
}

/**
 * <pre>与ArrayList做一个对比，把相同数据插入到ArrayList中并不会内存泄漏
 *
 * 最大可用内存：64M
 * 对内存大小：64M
 * 空闲内存：12M
 */
class TestArrayList {

    private static final Logger logger = LoggerFactory.getLogger(TestArrayList.class);

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        final Runtime rt = Runtime.getRuntime();
        rt.addShutdownHook(
                new Thread(() -> {
                    long heapMaxSize = rt.maxMemory() >> 20;
                    logger.info("最大可用内存：{}MB", heapMaxSize);
                    long total = rt.totalMemory() >> 20;
                    logger.info("堆内存大小：{}MB", total);
                    long free = rt.freeMemory() >> 20;
                    logger.info("空闲内存：{}MB", free);
                })
        );
        //放入近40万键值对
        for (int i = 0; i < 393217; i++) {
            //仅是增加
            list.add("key" + i);
            list.add("value" + i);
        }
    }
}
