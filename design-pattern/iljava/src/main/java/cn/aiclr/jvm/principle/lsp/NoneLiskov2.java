package cn.aiclr.jvm.principle.lsp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class NoneLiskov2 {

    public static void main(String[] args) {
        HashMap m = new HashMap();

        Base base = new Base();
        base.func(m);
        base.put(m);

        //子类重载了父类方法，调用子类重载方法；如果子类没有重载父类，则调用的是父类的方法
        //A 没有重载父类方法 ，A的方法属于重写 覆写 Overload
        A a = new A();
        a.func(m);
        a.put(m);

        Map m2 = new HashMap();
        a.func(m2);
        a.put(m2);

        //子类重载了父类方法，调用子类重载方法；如果子类没有重载父类，则调用的是父类的方法
        //B 类重载父类方法
        B b = new B();

        b.func(m);
        //虽然返回值 范围缩小，但仍属于重载，子类方法会执行
        b.put(m);
    }

    public static class Base {

        private static final Logger log = LoggerFactory.getLogger(Base.class);

        public Integer func(HashMap map) {
            log.info("func");
            return 1;
        }

        public Map put(HashMap map) {
            log.info("put");
            return map;
        }
    }

    public static class A extends Base {

        private static final Logger log = LoggerFactory.getLogger(A.class);

        /**
         * 扩大前置条件（覆写 Overload 不是重载 Override）
         *
         * @param map 父类是 HashMap
         * @return
         */
        public Integer func(Map map) {
            log.info("func");
            return 2;
        }

        /**
         * 前置条件扩大，后置条件缩小
         *
         * @param map 父类是 HashMap
         * @return 父类是 Map
         */
        public HashMap put(Map map) {
            log.info("put");
            return (HashMap) map;
        }

    }

    public static class B extends Base {

        private static final Logger log = LoggerFactory.getLogger(B.class);

        /**
         * 重载则执行子类方法
         *
         * @param map
         * @return 后置条件不变 Integer
         */
        @Override
        public Integer func(HashMap map) {
            log.info("func");
            return Integer.valueOf(2);
        }

        /**
         * 后置条件缩小 仍为重载
         *
         * @param map 父类是 HashMap
         * @return 父类是 Map
         */
        @Override
        public HashMap put(HashMap map) {
            log.info("put");
            return map;
        }
    }
}

