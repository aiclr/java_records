package cn.aiclr.jvm.principle.lsp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <pre>里氏替换原则面向对象设计的基本原则之一。
 * 里氏替换原则中说，任何基类可以出现的地方，子类一定可以出现。
 * LSP 是继承复用的基石，只有当衍生类可以替换掉基类，软件单位的功能不受到影响时，
 * 基类才能真正被复用，而衍生类也能够在基类的基础上增加新的行为。
 * A 和 B 耦合度高
 */
public class Liskov {

    private static final Logger log = LoggerFactory.getLogger(Liskov.class);

    private A a;

    public void setA(A a) {
        this.a = a;
    }

    public static void main(String[] args) {
        Liskov liskov = new Liskov();
        A a = new A();

        liskov.setA(a);
        log.info("{} + {} = {}", 2, 1, liskov.a.func1(2, 1));

        B b = new B();
        liskov.setA(b);
        log.info("{} - {} = {}", 2, 1, liskov.a.func1(2, 1));

        log.info("({} - {}) + 9 = {}", 2, 1, b.func2(2, 1));
    }

    public static class A {

        private static final Logger log = LoggerFactory.getLogger(A.class);

        public int func1(int a, int b) {
            log.info("func1");
            return a + b;
        }
    }

    public static class B extends A {

        private static final Logger log = LoggerFactory.getLogger(B.class);

        @Override
        public int func1(int a, int b) {
            log.info("func1");
            return a - b;
        }

        public int func2(int a, int b) {
            log.info("func2");
            return func1(a, b) + 9;
        }
    }
}

