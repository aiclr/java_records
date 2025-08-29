package cn.aiclr.jvm.designpattern.creation.builder;

/**
 * <pre>
 * 抽象建造者
 * 建造过程的抽象
 *
 * 建造者模式相关 jdk 源码:
 * {@link java.lang.StringBuilder} extends {@link java.lang.AbstractStringBuilder}
 * {@link java.lang.AbstractStringBuilder} implements {@link java.lang.Appendable}
 *
 * {@link java.lang.Appendable} 接口定义了多个 append 方法：
 * 抽象建造者，定义抽象方法。
 * {@link java.lang.Appendable#append(char)}；
 * {@link java.lang.Appendable#append(CharSequence)}；
 * {@link java.lang.Appendable#append(CharSequence, int, int)}；
 *
 * {@link java.lang.AbstractStringBuilder} implements {@link java.lang.Appendable}：具体建造者，但不能实例化
 *
 * {@link java.lang.StringBuilder} extends {@link java.lang.AbstractStringBuilder}：即是指挥者，又是建造者。
 * 建造方法 {@link java.lang.StringBuilder#append(CharSequence)} 具体由 {@link java.lang.AbstractStringBuilder}实现
 */
public abstract class BuilderHouse {

    House house = new House();

    public abstract void buildBasic();

    public abstract void buildWalls();

    public abstract void roofed();

    public House build() {
        return house;
    }
}
