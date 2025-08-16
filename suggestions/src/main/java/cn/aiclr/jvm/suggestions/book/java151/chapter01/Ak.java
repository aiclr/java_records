package cn.aiclr.jvm.suggestions.book.java151.chapter01;

import java.io.Serial;
import java.io.Serializable;

/**
 * <pre>11.养成良好习惯，显式声明UID<br>
 * 类实现Serializable接口的目的是为了可持久化<br>
 * 网络传输，本地存储，为系统的 分布 和 异构 部署提供先决支持条件<br>
 * UID：
 *  1.显示声明
 *      <code>private static final long serialVersionUID=66666L;</code>
 *  2.隐式声明
 *      不声明，编译器在编译的时候生成，
 *      依据是包名，类名，继承关系、非私有方法和属性，以及参数、返回值等诸多因子
 *      计算得出的极度复杂，基本上计算出来的值是唯一的
 * 问题：
 *  分布式下版本问题：
 *      隐式声明UID；
 *          生产者端和消费者端的Ak类有差异，
 *          比如生产者中的Ak增加了一个年龄属性，消费者端没有增加该属性，
 *          反序列化会报InvalidClassException异常
 *          因为序列化和反序列化所对应的类版本发生了变化，JVM不能把数据流转换为实例对象
 *          JVM通过SerialVersionUID，也叫做流标识符（Stream Unique Identifier）判断类是否变化
 *          相同则无变化，不同则抛出InvalidClassException异常
 *          这个机制可以保证一个对象即使在网络或磁盘中“滚过”一次，仍能做到“出淤泥而不染”，完美地实现类的一致性
 *      显示声明UID可以欺骗JVM：
 *          假如Ak类变动不大，通过显示声明SerialVersionUID欺骗JVM，版本不变，可以实现类的向上兼容。
 *          提高代码的健壮性。
 *          显式声明serialVersionUID可以避免对象不一致，但尽量不要以这种方式向JVM“撒谎”。
 *
 * {@link java.io.Serial} Java 14 中引入的（作为预览特性），并在 Java 16 中正式发布。
 * 这个注解的作用是帮助开发者更清晰地标识与 Java 对象序列化（java.io.Serializable）相关的字段、方法和构造函数
 * {@link cn.aiclr.jvm.suggestions.book.java151.chapter01.AkTest#writeObject()}
 * {@link cn.aiclr.jvm.suggestions.book.java151.chapter01.AkTest#readObject()}
 */
public class Ak implements Serializable {

    @Serial
    private static final long serialVersionUID = 66666L;

    private String name;
    private Integer age;

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}