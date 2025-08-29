package cn.aiclr.jvm.designpattern.creation.singleton;

public class StaticInnerClass {
    // 私有构造器，防止外部实例化
    private StaticInnerClass() {
    }

    /**
     * <pre>静态内部类
     * <a href="https://aiclr.github.io/develop/classloader/#%E7%B1%BB%E7%9A%84%E4%B8%BB%E5%8A%A8%E4%BD%BF%E7%94%A8%E5%92%8C%E8%A2%AB%E5%8A%A8%E4%BD%BF%E7%94%A8">涉及类的 主动使用 和 被动使用，以及类加载的 Initialization 阶段</a>
     * 外部类调用静态内部类属于被动使用，静态内部类加载时不会执行类加载的 Initialization 阶段。
     * 故外部类调用静态内部类时，静态内部类开始执行类加载的 Initialization 阶段，即 {@code <clinit>()}此方法由 jvm 保证线程安全。
     *
     * 防止反射攻击（相对安全）,虽然构造器是私有的，但可通过反射破坏。不过由于 INSTANCE 是在内部类中创建的，反射很难绕过类加载机制，比普通懒汉式更安全（但不如枚举）。
     *
     * 适用场景
     *  日志管理器（Logger）
     *  配置管理器（ConfigManager）
     *  线程池、连接池
     *  缓存管理器
     *  工具类的唯一实例
     */
    public static class Singleton {
        private static final StaticInnerClass instance = new StaticInnerClass();
    }

    // 全局访问点
    public static StaticInnerClass getInstance() {
        return Singleton.instance;
    }

    // 示例方法
    public void doSomething() {
        System.out.println("Singleton is working...");
    }
}
