package cn.aiclr.jvm.designpattern.behavior.visitor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <pre>访问者模式
 *
 * 封装一些作用于某种数据结构的各元素的操作,它可以在不改变数据结构的前提下定义作用于这些元素的新的操作。
 * 主要将数据结构与数据操作分离,解决数据结构和操作耦合性问题。
 *
 * 原理：在被访问的类里面加一个对外提供接待访问者的接口。
 * 场景：需要对一个对象结构中的对象进行很多不同操作(这些操作彼此没有关联).同时避免让这些操作污染这些对象的类。
 *
 * 符合单一职责原则,扩展性灵活性高。
 * 数据结构相对稳定的系统。
 *
 * 具体 {@link Element} 对 {@link Visitor} 公布细节，{@link Visitor} 关注其他类的内部细节,违背了迪米特法则,会造成 {@link Element} 变更比较困难。
 * 违背依赖倒转原则，{@link Visitor} 依赖具体的 {@link Element} {@link ElementMan} 和 {@link ElementWoman} ,而不直接依赖抽象元素。
 * 因此访问者模式适用于系统有比较稳定的数据结构,又有经常变化的功能需求。
 *
 * 抽象访问者 {@link Visitor} 为该对象结构中的 Element 的每一个类声明一个 visitor 操作：
 * {@link #manVisitor(ElementMan)}
 * {@link #womanVisitor(ElementWoman)}
 */
public abstract class Visitor {
    static Logger log = LoggerFactory.getLogger(Visitor.class);

    public abstract void manVisitor(ElementMan man);

    public abstract void womanVisitor(ElementWoman woman);
}
