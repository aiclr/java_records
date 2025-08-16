package cn.aiclr.jvm.suggestions.book.java151.chapter07.du;

/**
 * <pre>Me 这种类型的人物有很多，
 * 比如做系统分析师也是一个职员，也坐公交车，但他的工资实现就和 Me 不同，
 * 再比如 Boss 级的人物，偶尔也坐公交车，对大老板来说他也只是一个职员，他的实现类也不同，
 * 也就是说如果我们使用 {@code T extends Me}是限定不了需求对象的,可以考虑使用多重限定
 */
public class Me implements Staff, Passenger {
    @Override
    public boolean isStanding() {
        return true;
    }

    @Override
    public int getSalary() {
        return 2000;
    }
}
