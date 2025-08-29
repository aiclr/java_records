package cn.aiclr.jvm.designpattern.creation.singleton.lazy;

/**
 * 懒汉式单例模式
 */
public class SyncMethod {

    // 私有构造器，防止外部实例化
    private SyncMethod() {
        // 模拟耗时初始化（更容易暴露重排序问题）
        try {
            Thread.sleep(1); // 增加构造函数耗时
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static SyncMethod INSTANCE;

    /**
     * 同步方法 线程安全 效率低
     */
    public static synchronized SyncMethod getInstance() {
        if (INSTANCE == null)
            INSTANCE = new SyncMethod();
        return INSTANCE;
    }

    //未使用 volatile
    private static SyncMethod INSTANCE_NOT_SAFE;

    /**
     * <pre>同步代码块 双重检查 但是未使用 volatile 标记
     * jvm 字节码指令重排，仍导致多线程不安全
     *
     * 此情况极难复现
     */
    public static SyncMethod syncBlockNotSafe() {
        if (INSTANCE_NOT_SAFE == null) {
            synchronized (SyncMethod.class) {
                if (INSTANCE_NOT_SAFE == null) {
                    INSTANCE_NOT_SAFE = new SyncMethod();
                }
            }
        }
        return INSTANCE_NOT_SAFE;
    }

    /**
     * volatile 防止 jvm 字节码指令重排，导致多线程不安全
     */
    private static volatile SyncMethod INSTANCE_DoubleCheck;

    /**
     * 同步代码块+双重检查 线程安全 效率高
     */
    public static SyncMethod getInstanceDoubleCheck() {
        if (INSTANCE_DoubleCheck == null) {
            synchronized (SyncMethod.class) {
                if (INSTANCE_DoubleCheck == null)
                    INSTANCE_DoubleCheck = new SyncMethod();
            }
        }
        return INSTANCE_DoubleCheck;
    }


    // 示例方法
    public void doSomething() {
        System.out.println("Singleton is working...");
    }
}
