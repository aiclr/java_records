package cn.aiclr.jvm.principle.lsp;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 里氏替换原则  降低继承的耦合度
 */
public class Liskov1 {

    private static final Logger log = LoggerFactory.getLogger(Liskov1.class);

    public static void main(String[] args) {
        A a = new A();
        log.info("{}", a.func1(1, 2));

        B b = new B(a);
        log.info("{}", b.func1(1, 2));
        log.info("{}", b.func2(1, 2));
        log.info("{}", b.func3(1, 2));
        log.info("{}", b.func(1, 2));
    }

    public static class Base {

        private static final Logger log = LoggerFactory.getLogger(Base.class);

        public int func(int a, int b) {
            log.info("func");
            return a + b + b;
        }

    }

    public static class A extends Base {
        private static final Logger log = LoggerFactory.getLogger(A.class);

        public int func1(int a, int b) {
            log.info("func1");
            return a + b;
        }

    }

    /**
     * B A 使用组合替换继承关系
     */
    public static class B extends Base {

        private static final Logger log = LoggerFactory.getLogger(B.class);

        A a;

        public B(A a) {
            this.a = a;
        }

        public int func1(int a, int b) {
            log.info("func1");
            return a - b;
        }

        public int func2(int a, int b) {
            log.info("func2");
            return func1(a, b) + 9;
        }

        public int func3(int a, int b) {
            log.info("func3");
            return this.a.func1(a, b) + 9;
        }

    }
}


