package cn.aiclr.jvm.suggestions.book.java151.chapter03;

import java.io.Serial;
import java.io.Serializable;

/**
 * <pre>43.避免对象的浅拷贝
 * 一个类实现了 Cloneable 接口就表示它具备了被拷贝的能力，
 * 如果再覆写 clone() 方法就会完全具备拷贝能力。
 * 拷贝是在内存中进行的，所以在性能方面比直接通过 new 生成对象要快很多，特别是在大对象的生成上，这会使性能的提升非常显著。
 * 但是对象拷贝也有一个比较容易忽略的问题：浅拷贝（Shadow Clone，也叫做影子拷贝）存在对象属性拷贝不彻底的问题
 *
 * Object 提供了一个对象拷贝的默认方法，即下面代码中的 super.clone() 方法，
 * 但是该方法是有缺陷的，它提供的是一种浅拷贝方式，也就是说它并不会把对象的所有属性全部拷贝一份，而是有选择性的拷贝
 * 1. 基本类型
 *      如果变量是基本类型，则拷贝其值，比如 int、float 等
 * 2. 对象
 *      如果变量是一个实例对象，则拷贝地址引用，也就是说此时新拷贝出的对象与原有对象共享该实例变量，不受访问权限的限制。这在 Java 中是很疯狂的，因为它突破了访问权限的定义：一个 private 修饰的变量，竟然可以被两个不同的实例对象访问，这让 Java 的访问权限体系情何以堪
 * 3. String 字符串
 *      拷贝的也是一个地址，是个引用，但是在修改时，它会从字符串池（String Pool）中重新生成新的字符串，原有的字符串对象保持不变，在此处我们可以认为 String 是一个基本类型
 */
public class Bq implements Serializable {

    @Serial
    private static final long serialVersionUID = 100L;

    public String msg;

    public Bq(String msg) {
        this.msg = msg;
    }
}

class BqShadowClone implements Cloneable {
    public int age;
    public String name;
    public Bq bq;

    public BqShadowClone(int age, String name, Bq bq) {
        this.age = age;
        this.name = name;
        this.bq = bq;
    }

    @Override
    public BqShadowClone clone() throws CloneNotSupportedException {
        return (BqShadowClone) super.clone();
    }
}

class BqDeepClone implements Cloneable {

    public int age;
    public String name;
    public Bq bq;

    public BqDeepClone(int age, String name, Bq bq) {
        this.age = age;
        this.name = name;
        this.bq = bq;
    }

    @Override
    public BqDeepClone clone() throws CloneNotSupportedException {
        BqDeepClone clone = (BqDeepClone) super.clone();
        clone.bq = new Bq(clone.bq.msg);
        return clone;
    }
}
