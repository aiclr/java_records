package cn.aiclr.jvm.suggestions.book.java151.chapter03;

import java.io.Serial;
import java.io.Serializable;

/**
 * <pre>44.推荐使用序列化实现对象的拷贝
 * 通过序列化方式来处理，在内存中通过字节流的拷贝来实现，
 * 也就是把母对象写到一个字节流中，再从字节流中将其读出来，这样就可以重建一个新对象了，
 * 该新对象与母对象之间不存在引用共享的问题，也就相当于深拷贝了一个新对象、
 *
 * 采用序列化方式拷贝时还有一个更简单的办法，
 * 使用 Apache 下的 commons 工具包中的 {@link org.apache.commons.lang3.SerializationUtils} 类，更加简洁方便
 * {@link org.apache.commons.lang3.SerializationUtils#deserialize(byte[])}
 * {@link org.apache.commons.lang3.SerializationUtils#serialize(Serializable)}
 */
public class Br implements Serializable {

    @Serial
    private static final long serialVersionUID = 100L;

    public int age;
    public String name;
    public Bq bq;

    public Br(int age, String name, Bq bq) {
        this.age = age;
        this.name = name;
        this.bq = bq;
    }
}
