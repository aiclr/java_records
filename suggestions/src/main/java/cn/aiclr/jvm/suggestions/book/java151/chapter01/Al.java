package cn.aiclr.jvm.suggestions.book.java151.chapter01;

import java.io.Serial;
import java.io.Serializable;

/**
 * <pre>12.避免用序列化类在构造函数中为不变量赋值
 *
 * 带 final 标识的属性是不变量，也就是说只能赋值一次，不能重复赋值
 *
 * 1. final 属性是一个直接量，反序列化时会重新计算，final 属性会变成凯奇，
 *      使用 v1 版本序列化：<code>public final String name="尼古拉斯";</code>
 *      {@link cn.aiclr.jvm.suggestions.book.java151.chapter01.AlTest#testV1FinalField()}
 *      使用 v2 版本进行反序列化：<code>public final String name="凯奇";</code>
 *      {@link cn.aiclr.jvm.suggestions.book.java151.chapter01.AlTest#testV2FinalField()}
 *
 *      使用 v1 版本序列化：<code>public final String name=new String("尼古拉斯");</code>
 *      {@link cn.aiclr.jvm.suggestions.book.java151.chapter01.AlTest#testV1FinalNewString()}
 *      使用 v2 版本进行反序列化：<code>public final String name=new String("凯奇");</code>
 *      {@link cn.aiclr.jvm.suggestions.book.java151.chapter01.AlTest#testV2FinalNewString()}
 * 2. 反序列化时构造函数不会执行
 *      所以构造函数赋值时，修改成 V2 版本反序列化时，name 是不会变的
 *      {@link cn.aiclr.jvm.suggestions.book.java151.chapter01.AlTest#testV1Constructor()}
 *      {@link cn.aiclr.jvm.suggestions.book.java151.chapter01.AlTest#testV2Constructor()}
 *      JVM从数据流种获取一个 Object 对象，然后根据数据流中的类文件描述信息查看(在序列化时，保存到磁盘的对象文件中包含了类描述信息，注意是类描述信息，不是类)
 *      发现 final 变量 name ，需要重新计算，于是引用 Al 类中的 name 值，而此时 JVM 发现 name 没有赋值，不能引用，于是不再初始化，保持原值，所以结果仍未 v1 版本。
 *
 * 场景介绍
 *  桌面应用，C/S 结构24小时在线，升级的类中有一个 final 变量是构造函数赋值，而且新旧版本发生变化
 *  则应用请求热切的过程中，很可能出现反序列化生产的 final 变量值与新产生的实例值不相同的情况
 */
public class Al implements Serializable {

    @Serial
    private static final long serialVersionUID = 123L;

    public final String name;

    //2.构造函数赋值 v1 序列化
    public Al() {
        name = "尼古拉斯";
    }

    //2.构造函数赋值 v2 反序列化
//    public Al() {
//        name = "凯奇";
//    }

    public String getName() {
        return name;
    }
}

class FinalField implements Serializable {

    @Serial
    private static final long serialVersionUID = 123L;

    //1.直接量 v1 序列化
    public final String name = "尼古拉斯";

    //1.直接量 v2 反序列化
//    public final String name="凯奇";

    public String getName() {
        return name;
    }
}

class FinalNewString implements Serializable {
    @Serial
    private static final long serialVersionUID = 123L;

    //1.直接量 v1 序列化
    public final String name = new String("尼古拉斯");

    //1.直接量 v2 反序列化
//    public final String name = new String("凯奇");

    public String getName() {
        return name;
    }
}