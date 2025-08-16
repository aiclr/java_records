package cn.aiclr.jvm.suggestions.book.java151.chapter12.fr;

/**
 * <pre>一个类实现了两个接口，把两个职责融合在一个类中。
 * 你会觉得这个 Phone 有两个原因引起了变化，
 * 是的，但是别忘记了我们是面向接口编程的，
 *
 * 我们对外公布的是接口而不是实现类。
 *
 * 而且，如果真要实现类的单一职责，就必须使用上面的组合模式，
 * 这会引起类间的耦合过重、类的数量增加等问题，人为地增加了设计的复杂性
 */
public class Phone implements Connection, Transfer {
    @Override
    public void dial() {

    }

    @Override
    public void huangUp() {

    }

    @Override
    public void chat() {

    }
}
