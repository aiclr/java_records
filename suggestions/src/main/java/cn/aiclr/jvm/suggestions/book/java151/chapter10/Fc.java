package cn.aiclr.jvm.suggestions.book.java151.chapter10;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <pre>133.若非必要，不要克隆对象
 *
 * 注意：克隆对象并不比直接生成对象效率高
 *
 * 通过 {@link java.lang.Object#clone()} 方法生成一个对象时，不会再执行构造函数，
 * 只是在内存中进行数据块的拷贝，
 * 此方法看上去似乎应该比 new 方法的性能好很多，
 * 但是 Java 的缔造者们也认识到 “二八原则”，
 * 80%（甚至更多）的对象是通过 new 关键字创建出来的，
 * 所以对 new 生成对象（分配内存、初始化）时做了充分的性能优化，
 * 事实上，一般情况下 new 生成的对象比 {@link java.lang.Object#clone()} 生成的性能方面要好很多
 */
public class Fc {

    private static final Logger logger = LoggerFactory.getLogger(Fc.class);

    /**
     * <pre>用 new 生成对象比 {@link java.lang.Object#clone()} 方法快很多！
     * 原因是 Apple 的构造函数非常简单，
     * 而且 JVM 对 new 做了大量的性能优化，
     * 而 {@link java.lang.Object#clone()} 方式只是一个冷僻的生成对象方式，并不是主流，
     * 它主要用于构造函数比较复杂，对象属性比较多，
     * 通过 new 关键字创建一个对象比较耗时间的时候
     */
    public static void main(String[] args) throws CloneNotSupportedException {
//        int maxLoops = 100 * 1000;
        int maxLoops = 1000 * 1000;
        Fc fc = new Fc();
        fc.cloneObj(maxLoops, new Apple());
        fc.newObj(maxLoops);
    }

    private void newObj(int maxLoops) {
        long start = System.currentTimeMillis();
        while (--maxLoops > 0) {
            new Apple();
        }
        long end = System.currentTimeMillis();
        logger.info("new {}-{}={}ns", end, start, end - start);//4ns左右
    }

    private void cloneObj(int maxLoops, Apple source) throws CloneNotSupportedException {
        long start = System.currentTimeMillis();
        while (--maxLoops > 0) {
            source.clone();
        }
        long end = System.currentTimeMillis();
        logger.info("clone {}-{}={}ns", end, start, end - start);//8ns左右
    }

    static class Apple implements Cloneable {
        @Override
        protected Object clone() throws CloneNotSupportedException {
            try {
                return super.clone();
            } catch (CloneNotSupportedException e) {
                throw new Error();
            }
        }
    }
}

