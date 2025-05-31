package cn.aiclr.jvm.juc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

class CASForIntTest {

    @Test
    void atomicInteger() {
        AtomicInteger atomicInt = new AtomicInteger(0);

        // 使用CAS设置新值
        boolean success = atomicInt.compareAndSet(0, 1); // 如果当前值是0，则设置为1
        Assertions.assertTrue(success);
        Assertions.assertEquals(1, atomicInt.get());

        // 再次尝试CAS
        success = atomicInt.compareAndSet(0, 2); // 当前值已经是1，不是0，所以不会修改
        Assertions.assertFalse(success);
        Assertions.assertEquals(1, atomicInt.get());

        // 递增操作也是基于CAS实现的
        int newValue = atomicInt.incrementAndGet();
        Assertions.assertEquals(2, newValue);
    }

    @Test
    void compareAndSwap() {
        CASForInt atomicInt = new CASForInt(0);

        // 使用CAS设置新值
        boolean success = atomicInt.compareAndSwap(0, 1); // 如果当前值是0，则设置为1
        Assertions.assertTrue(success);
        Assertions.assertEquals(1, atomicInt.getValue());

        // 再次尝试CAS
        success = atomicInt.compareAndSwap(0, 2); // 当前值已经是1，不是0，所以不会修改
        Assertions.assertFalse(success);
        Assertions.assertEquals(1, atomicInt.getValue());
    }

    @Test
    void unsafeCompareAndSwap() {
        UnSafeCASForInt atomicInt = new UnSafeCASForInt(0);

        // 使用CAS设置新值
        boolean success = atomicInt.compareAndSwap(0, 1); // 如果当前值是0，则设置为1
        Assertions.assertTrue(success);
        Assertions.assertEquals(1, atomicInt.getValue());

        // 再次尝试CAS
        success = atomicInt.compareAndSwap(0, 2); // 当前值已经是1，不是0，所以不会修改
        Assertions.assertFalse(success);
        Assertions.assertEquals(1, atomicInt.getValue());
    }
}