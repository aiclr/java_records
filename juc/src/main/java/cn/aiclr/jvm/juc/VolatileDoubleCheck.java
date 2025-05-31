package cn.aiclr.jvm.juc;

import java.util.Objects;

/**
 * 单例模式 双重检查
 */
public class VolatileDoubleCheck {

    private static volatile VolatileDoubleCheck doubleCheck;

    private VolatileDoubleCheck() {
    }

    public static VolatileDoubleCheck getInstance() {
        if (Objects.isNull(doubleCheck)) {
            synchronized (VolatileDoubleCheck.class) {
                if (Objects.isNull(doubleCheck)) {
                    doubleCheck = new VolatileDoubleCheck();
                }
            }
        }
        return doubleCheck;
    }
}
