package cn.aiclr.jvm.suggestions.book.java151.chapter06.dl;

/**
 * <pre>90.小心注解继承
 *
 * 元注解（Meta-Annotation）: {@link java.lang.annotation.Inherited} 它表示一个注解是否可以自动被继承
 *
 * Java 从 1.5 版开始引入了注解（Annotation），
 * 其目的是在不影响代码语义的情况下增强代码的可读性，
 * 并且不改变代码的执行逻辑，
 * 对于注解始终有两派争论，
 * 正方认为注解有益于数据与代码的耦合，“在有代码的周边集合数据”；
 * 反方认为注解把代码和数据混淆在一起，增加了代码的易变性，削弱了程序的健壮性和稳定性
 *
 * 关于 {@link java.lang.annotation.Inherited} 元注解 有利有弊，
 * 利的地方是一个注解只要标注到父类，所有的子类都会自动具有与父类相同的注解，整齐、统一而且便于管理
 * 弊的地方是单单阅读子类代码，我们无从知道为何逻辑会被改变，因为子类没有明显标注该注解。
 * 总体上来说，使用 {@link java.lang.annotation.Inherited} 元注解的弊大于利，
 * 特别是一个类的继承层次较深时，如果注解较多，则很难判断出是哪个注解对子类产生了逻辑劫持
 */
public class Dl {
    public static void main(String[] args) {
        Bird bird = BirdNest.Sparrow.reproduce();
        System.out.println(bird.getColor());
    }
}
