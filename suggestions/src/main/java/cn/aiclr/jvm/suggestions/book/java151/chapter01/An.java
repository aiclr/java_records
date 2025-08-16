package cn.aiclr.jvm.suggestions.book.java151.chapter01;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;

/**
 * <pre>14.使用序列化类的私有方法巧妙解决部分属性持久化问题
 *
 * 部分属性持久化可以把不需要持久化的属性加上瞬态关键字（transient关键字）
 *
 *  场景:
 *      基本工资可以反序列化
 *      绩效需要保密不能反序列化得到
 *  解决方案：
 *      1.在 bonus 上加上瞬态关键字 transient
 *          加上瞬态关键字 AnSalary 类就失去了分布式部署的功能
 *      2.新增业务对象,增加 Person 类，完全为保密服务，符合 ocp 原则，而且对原系统没有侵入性，但是增加工作量
 *      3.请求端过滤，
 *          请求端过滤掉 bonus 属性，可行不符合规范，造成 AnSalary 类的安全性让其他系统承担
 *      4.变更传输契约
 *          改用 xml 传输，或者重建一个 WebService 服务，成本高
 *      5.AnPerson 失去了分布式部署能力，但是该类只有属性，涉及计算的可能性不大，对该类做分布式意义不大
 *          实现了 Serializable 接口，可以实现两个私有方法 writeObject 和 readObject ，可以影响和控制序列化和反序列化的过程
 *          使用了序列化独有的 序列化回调 机制
 *          java 调用 ObjectOutputStream 类把一个对象转换成流数据时，会通过反射（Reflection）检查被序列化的类是否有私有 writeObject 方法
 *              没有，则由ObjectOutputStream按照默认规则继续序列化
 *              有，则会通过该方法读取属性值
 *          同理，从流数据恢复成实例对象，会检查是否有一个私有 readObject 方法
 *  关键点：
 *      1.out.defaultWriteObject()
 *          告知 JVM 按照默认的规则写入对象，惯例的写法是写在第一句话里
 *      2.in.defaultReadObject()
 *          告知 JVM 按照默认规则读入对象，惯例的写法是写在第一句话里
 *      3.out.writeXX 和 in.readXX
 *          分别是写入和读出相应的值，类似一个队列，先进先出，如果有复杂数据逻辑，建议封装 Collection 对象处理
 *   {@link cn.aiclr.jvm.suggestions.book.java151.chapter01.AlTest#testWriteObject()}
 *   {@link cn.aiclr.jvm.suggestions.book.java151.chapter01.AlTest#testReadObject()}
 */
public class An {
}


/**
 * 薪酬类
 */
class AnSalary implements Serializable {

    @Serial
    private static final long serialVersionUID = 123L;

    //基本工资
    private int basePay;

    //工龄
    private int seniority;

    //绩效工资  需要保密
    private int bonus;

    public AnSalary(int basePay, int seniority, int bonus) {
        this.basePay = basePay;
        this.seniority = seniority;
        this.bonus = bonus;
    }

    public int getBasePay() {
        return basePay;
    }

    public void setBasePay(int basePay) {
        this.basePay = basePay;
    }

    public int getSeniority() {
        return seniority;
    }

    public void setSeniority(int seniority) {
        this.seniority = seniority;
    }

    public int getBonus() {
        return bonus;
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
    }
}

/**
 * 人员类
 */
class AnPerson implements Serializable {

    @Serial
    private static final long serialVersionUID = 123L;

    private String name;

    private AnSalary anSalary;

    public AnPerson(String name, AnSalary anSalary) {
        this.name = name;
        this.anSalary = anSalary;
    }

    //第5种优化方案
    @Serial
    private void writeObject(java.io.ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        //将基本工资添加到 ObjectOutputStream 先进先出
        out.writeInt(anSalary.getBasePay());
        //将工龄添加到 ObjectOutputStream 先进先出
        out.writeInt(anSalary.getSeniority());

    }

    @Serial
    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        //从 ObjectInputStream 读取 基本工资 工龄 先进先出
        anSalary = new AnSalary(in.readInt(), in.readInt(), 0);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AnSalary getAnSalary() {
        return anSalary;
    }

    public void setAnSalary(AnSalary anSalary) {
        this.anSalary = anSalary;
    }
}