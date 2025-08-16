package cn.aiclr.jvm.suggestions.book.java151.chapter08.ej;

import java.io.IOException;

public class Sub extends Base {

    /**
     * <pre>3）子类构造函数扩展受限
     * 这段代码编译通不过，原因是构造函数 Sub 中没有把 super() 放在第一句话中，
     * 想把父类的异常重新包装后再抛出是不可行的
     * （当然，这里有很多种“曲线”的实现手段，比如重新定义一个方法，然
     * 后父子类的构造函数都调用该方法，那么子类构造函数就可以自由处理异常了），这是 Java 语法限制
     */
//    public Sub() throws Exception {
//        try {
//            //Call to 'super()' must be first statement in constructor body
//            super();
//        } catch (IOException e) {
//            throw e;
//        }
//    }


    /**
     * <pre>父类的无参构造函数抛出了 IOException 异常
     * 子类的无参构造函数默认调用的是父类的构造函数，
     * 所以
     *      子类的无参构造函数不能省略
     *      子类的无参构造也必须抛出 IOException 或其父类
     *
     * @throws Exception
     */
    public Sub() throws Exception {

    }

    /**
     * <pre>覆写要求：
     *      子类普通方法的异常类型必须是父类普通方法抛出异常类型的子类型
     *
     * @throws IOException
     */
    @Override
    public void method() throws IOException {

    }
}
