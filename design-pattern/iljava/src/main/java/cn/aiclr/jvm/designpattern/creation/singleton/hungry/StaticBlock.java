package cn.aiclr.jvm.designpattern.creation.singleton.hungry;

/**
 * <pre>静态代码块实现 单例模式 饿汉式
 *
 * 实现原理
 * 类加载过程 Loading、Linking、Initialization
 *  Linking 阶段的 Prepare 阶段会为静态变量赋默认初始值。
 *  Initialization 阶段，当存在静态变量赋值，静态代码块赋值时
 *    javac 编译器自动收集类中的所有类静态变量的赋值动作和静态代码块中的语句合并成类构造器方法 <clinit>()
 *
 *  jvm 被要求必须保证一个类的 <clinit>() 方法在多线程下被同步加锁。
 *  类只会被加载一次，并放置到 method area
 *
 * <a href="https://aiclr.github.io/develop/classloader/#prepare">Linking 阶段的 Prepare 阶段会为静态变量赋默认初始值</a>
 */
public class StaticBlock {

    // 私有构造器，防止外部实例化
    private StaticBlock() {
    }

    //类静态变量
    private static final StaticBlock INSTANCE;

    //静态代码块
    static {
        INSTANCE = new StaticBlock();
    }

    // 全局访问点
    public static StaticBlock getInstance() {
        return INSTANCE;
    }


    // 示例方法
    public void doSomething() {
        System.out.println("Singleton is working...");
    }
}
