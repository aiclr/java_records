package cn.aiclr.jvm.juc;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * todo 使用Unsafe类
 */
public class UnSafeCASForInt {
    private static final Unsafe unsafe;
    private static final long valueOffset;
    private volatile int value;

    public UnSafeCASForInt(int value) {
        this.value = value;
    }

    static {
        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            unsafe = (Unsafe) theUnsafe.get(null);
            valueOffset = unsafe.objectFieldOffset(UnSafeCASForInt.class.getDeclaredField("value"));
        } catch (Exception ex) {
            throw new Error(ex);
        }
    }

    public boolean compareAndSwap(int expected, int update) {
        return unsafe.compareAndSwapInt(this, valueOffset, expected, update);
    }

    public int getValue() {
        return value;
    }
}
