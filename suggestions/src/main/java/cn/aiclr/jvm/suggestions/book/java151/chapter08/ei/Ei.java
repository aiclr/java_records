package cn.aiclr.jvm.suggestions.book.java151.chapter08.ei;

import java.util.zip.DataFormatException;

/**
 * <pre>113.不要在 finally 块中处理返回值
 *
 * 不要在 finally 代码块中出现 return 语句
 *
 * 在项目中绝对不能在 finally 代码块中出现 return 语句，
 * 这是因为这种处理方式非常容易产生“误解”，会严重误导开发者
 *
 * finally 是用来做异常的收尾处理的，
 * 一旦加上了 return 语句就会让程序的复杂度徒然提升，
 * 而且会产生一些隐蔽性非常高的错误。
 * 与 return 语句相似，
 *      {@code System.exit(0)}
 *      {@code Runtime.getRuntime().exit(0)}
 * 出现在异常代码块中也会产生非常多的错误假象，
 * 增加代码的复杂性
 */
public class Ei {

    /**
     * <pre>该方法抛出受检异常
     * finally 代码块中加入了 return 语句，而这会导致出现以下两个问题：
     * 1）覆盖了 try 代码块中的 return 返回值
     *      当执行 doStuff(-1) 时，doStuff 方法产生了 DataFormatException 异常，
     *      catch 块在捕捉此异常后直接抛出，
     *      之后代码执行到 finally 代码块，就会重置返回值，结果就是 -1 了，
     *      也就是出现了先返回，再执行 finally，再重置返回值的情况
     * 2）屏蔽异常
     *      异常线程在监视到有异常发生时，就会登记当前的异常类型为 DataFormatException，
     *      但是当执行器执行 finally 代码块时，则会重新为 doStuff 方法赋值，
     *      也就是告诉调用者：“该方法执行正确，没有产生异常，返回值是 1”，
     *      于是乎，异常神奇的消失了
     */
    public static int doStuff(int _p) throws Exception {
        try {
            if (_p < 0) {
                throw new DataFormatException("数据格式错误");
            } else {
                return _p;
            }
        } catch (Exception e) {
            throw e;
        } finally {
            //永远不会抛出异常
            return -1;
        }
    }

    /**
     * <pre>该方法的返回值永远是 1，而不会是 -1 或 0（为什么不会执行到“return 0”呢？
     * 原因是 finally 执行完毕后该方法已经有返回值了，后续代码就不会再执行了），
     * 这都是源于异常代码块的处理方式，
     * 在代码中加上 try 代码块就标志着运行时会有一个 Throwable 线程监视着该方法的运行，
     * 若出现异常，则交由异常逻辑处理。
     * 方法是在栈内存中运行的，
     * 并且会按照“先进后出”的原则执行，
     * main 方法调用了 doStuff 方法，
     * 则 main 方法在下层，
     * doStuff 在上层，
     * 当 doStuff 方法执行完 “return a” 时，
     * 此方法的返回值已经确定是 int 类型 1（a变量的值，注意基本类型都是值拷贝，而不是引用），
     * 此后 finally 代码块再修改 a 的值已经与 doStuff 返回者没有任何关系了，
     * 因此该方法永远都会返回 1
     */
    public static int doStuff1() {
        int a = 1;
        try {
            return a;
        } catch (Exception e) {
        } finally {
            a = -1;
        }
        return 0;
    }

    public static Person doStuff2() {
        Person p = new Person("张");
        try {
            return p;
        } catch (Exception e) {

        } finally {
            p.setName("王");
        }
        p.setName("李");
        return p;
    }

}