package cn.aiclr.jvm.designpattern.creation.singleton;

/**
 * <pre>利用枚举实现单例模式
 *   1. 线程安全 自动保证
 *   2. 代码简洁性 非常简洁
 *   3. 防止反射攻击 枚举构造器特殊，JVM 禁止通过反射创建枚举实例
 *   4. 防止序列化破坏 JVM 保证序列化时返回唯一实例
 */
public enum SingletonEnum {
    // 唯一实例（枚举实例）
    INSTANCE;

    // 可添加实例变量
    private String data;

    // 构造器（私有，自动调用）
    SingletonEnum() {
        this.data = "Singleton Enum Instance";
    }

    // 提供公共方法
    public void doSomething() {
        System.out.println("Data: " + data);
    }

    // 可提供 getter/setter
    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
