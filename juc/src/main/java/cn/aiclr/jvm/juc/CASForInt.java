package cn.aiclr.jvm.juc;

/**
 * 手动实现CAS操作
 *
 * 不推荐(因为JDK已经提供了更好的实现)，但可以模拟CAS操作：
 *
 * 在Java中，使用java.util.concurrent.atomic包中的AtomicInteger类来通过CAS(Compare-And-Swap)算法实现对int值的原子性修改。
 */
public class CASForInt {
    private volatile int value;

    public CASForInt(int initialValue) {
        this.value = initialValue;
    }

    public synchronized boolean compareAndSwap(int expectedValue, int newValue) {
        if (value == expectedValue) {
            value = newValue;
            return true;
        }
        return false;
    }

    public int getValue() {
        return value;
    }
}
