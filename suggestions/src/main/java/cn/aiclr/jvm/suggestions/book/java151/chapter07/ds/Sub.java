package cn.aiclr.jvm.suggestions.book.java151.chapter07.ds;

public class Sub extends Base {

    /**
     * <pre>子类的 doStuff 方法返回值的类型比父类方法要窄
     * 此时doStuff方法就是一个协变方法，
     * 同时根据Java的覆写定义来看，这又属于覆写
     */
    @Override
    public Integer doStuff() {
        return 0;
    }

    /**
     * <pre>子类的 doStuff 方法的参数类型比父类要宽，此时就是一个逆变方法，
     * 子类扩大了父类方法的输入参数，
     * 但根据覆写定义来看，doStuff不属于覆写，只是重载而已。
     * 由于此时的doStuff方法已经与父类没有任何关系了，
     * 只是子类独立扩展出的一个行为，
     * 所以是否声明为doStuff方法名意义不大，
     * 逆变已经不具有特别的意义
     */
    public void putStuff(Number number) {

    }
}
