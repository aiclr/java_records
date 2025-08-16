package cn.aiclr.jvm.suggestions.book.java151.chapter08.ej;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * <pre>114.不要在构造函数中抛出异常
 *
 * 在构造函数中不要抛出异常，尽量曲线救国
 *
 * Java 的异常机制有三种:
 * 1)Error 类及其子类表示的是错误，
 *      它是不需要程序员处理也不能处理的异常，比如 VirtualMachineError 虚拟机错误，ThreadDeath 线程僵死等
 * 2)RuntimeException 类及其子类表示的是非受检异常，是系统可能会抛出的异常，
 *      程序员可以去处理，也可以不处理，最经典就是 NullPointerException 空指针异常和 IndexOutOfBoundsException 越界异常
 * 3)Exception 类及其子类（不包含非受检异常）表示的是受检异常，
 *      这是程序员必须处理的异常，不处理则程序不能通过编译，比如 IOException 表示 I/O 异常，SQLException 表示数据库访问异常
 *
 * 从 Java 语法上来说，完全可以在构造函数中抛出异常，三类异常都可以，
 * 但是从系统设计和开发的角度来分析，则尽量不要在构造函数中抛出异常
 * 1)构造函数抛出错误是程序员无法处理的
 *      在构造函数执行时，若发生了 VirtualMachineError 虚拟机错误，那就没招了，只能抛出，
 *      程序员不能预知此类错误的发生，也就不能捕捉处理
 * 2)构造函数不应该抛出非受检异常
 *      1）加重了上层代码编写者的负担
 *          捕捉这个 RuntimeException 异常吧，那谁来告诉我有这个异常呢？只有通过文档来约束了，
 *              一旦 Person 类的构造函数经过重构后再抛出其他非受检异常，那 main 方法不用修改也是可以通过测试的，
 *              但是这里就可能会产生隐藏的缺陷，而且还是很难重现的缺陷。
 *          不捕捉这个 RuntimeException 异常，这是我们通常的想法，
 *              既然已经写成了非受检异常，main 方法的编码者完全可以不处理这个异常嘛，大不了不执行 Person 的方法！
 *              这是非常危险的，一旦产生异常，整个线程都不再继续执行，
 *              或者连接没有关闭，或者数据没有写入数据库，或者产生内存异常，这些都是会对整个系统产生影响
 *     2）后续代码不会执行
 *          main 方法的实现者原本只是想把 p 对象的建立作为其代码逻辑的一部分，
 *          执行完 seeMovie 方法后还需要完成其他逻辑，
 *          但是因为没有对非受检异常进行捕捉，异常最终会抛出到 JVM 中，
 *          这会导致整个线程执行结束后，后面所有的代码都不会继续执行了，
 *          这就对业务逻辑产生了致命的影响
 * 3)构造函数尽可能不要抛出受检异常
 *      1）在构造函数中抛出受检异常的三个不利方面
 *          1）导致子类代码膨胀
 *              例子中子类的无参构造函数不能省略，
 *              原因是父类的无参构造函数抛出了 IOException 异常，
 *              子类的无参构造函数默认调用的是父类的构造函数，
 *              所以子类的无参构造也必须抛出 IOException 或其父类
 *          2）违背了里氏替换原则
 *              里氏替换原则是说“父类能出现的地方子类就可以出现，而且将父类替换为子类也不会产生任何异常”
 *              Java 的构造函数允许子类的构造函数抛出更广泛的异常类,
 *              这正好与类方法的异常机制相反，(子类方法的异常类型必须是父类方法的子类型)。
 *              子类的方法可以抛出多个异常，但都必须是被覆写方法的子类型，
 *                  Sub 类的 method 方法抛出的异常必须是 Exception 的子类或 Exception 类，
 *                  这是 Java 覆写的要求
 *              因为构造函数没有覆写的概念，只是构造函数间的引用调用而已，
 *              所以在构造函数中抛出受检异常会违背里氏替换原则，使我们的程序缺乏灵活性
 *          3）子类构造函数扩展受限
 *              子类存在的原因就是期望实现并扩展父类的逻辑，但是父类构造函数抛出异常却会让子类构造函数的灵活性大大降低
 */
public class Ej {

    private static final Logger logger = LoggerFactory.getLogger(Ej.class);

    /**
     * <pre>违法里氏替换原则
     * 期望把 new Base() 替换成 new Sub()，而且代码能够正常编译和运行。
     * 非常可惜，编译通不过，
     * 原因是 Sub 的构造函数抛出了 Exception 异常，
     * 它比父类的构造函数抛出的异常范围要宽，
     * 必须增加新的 catch 块才能解决
     */
    public static void main(String[] args) {

        try {
            Base b = new Base();
        } catch (IOException e) {
            logger.error("{}", e.getMessage(), e);
        }

        try {
            Base b = new Sub();
        } catch (IOException e) {
            logger.error("{}", e.getMessage(), e);
        } catch (Exception e) {
            logger.error("{}", e.getMessage(), e);
        }

        //Person 构造器 抛出 非受检异常, 不使用 try-catch 处理后续代码不会执行
        Person person = null;
        try {
            person = new Person(10);
        } catch (Exception e) {
            logger.error("{}", e.getMessage(), e);
        }
        //不使用 try-catch 处理,可能不会执行后续代码
        if (person != null) {
            person.seeMovie();
        }
    }
}