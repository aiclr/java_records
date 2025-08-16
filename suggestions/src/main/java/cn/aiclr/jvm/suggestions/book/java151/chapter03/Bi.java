package cn.aiclr.jvm.suggestions.book.java151.chapter03;


/**
 * <pre>35.避免在构造函数中初始化其他类
 *
 * 构造函数是一个类初始化必须执行的代码，它决定着类的初始化效率，
 * 如果构造函数比较复杂，而且还关联了其他类，则可能产生意想不到的问题
 *
 * 不要在构造函数中声明初始化其他类，养成良好的习惯
 */
public class Bi {

    public static void main(String[] args) {
        SonBi sonBi = new SonBi();
        sonBi.doSomething();
    }
}

/**
 * <pre>场景：
 * FatherBi 是由框架提供的，
 * SonBi 类是我们自己编写的扩展代码，
 * 而 OtherBi 类则是框架要求的拦截类（ Interceptor 类或者 Handle 类或者 Hook 方法）
 * 死循环
 */
class FatherBi {
    FatherBi() {
        new OtherBi();
    }
}

class SonBi extends FatherBi {
    public void doSomething() {
        System.out.println("111");
    }
}

class OtherBi {
    //死循环
//    public OtherBi(){
//        new SonBi();
//    }

    //优化 不使用组合，使用聚合
    private SonBi sonBi;

    public OtherBi() {
    }

    public void setSonBi(SonBi _sonBi) {
        sonBi = _sonBi;
    }
}
