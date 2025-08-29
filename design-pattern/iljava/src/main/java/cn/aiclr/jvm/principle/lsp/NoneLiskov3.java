package cn.aiclr.jvm.principle.lsp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>子类前置条件范围 小于 父类前置条件
 * 违反里氏替换原则
 */
public class NoneLiskov3 {

    public Base base;

    public void setBase(Base base) {
        this.base = base;
    }

    public static void main(String[] args) {

        Map map = new HashMap();
        HashMap hashMap = new HashMap();

        NoneLiskov3 liskov = new NoneLiskov3();
        //父类
        Base base1 = new Base();
        liskov.setBase(base1);
        liskov.base.get(map);
        liskov.base.get(hashMap);

        // 由于子类方法没有重载父类方法，下方调用的全部是父类方法
        //子类换父类
        Sub sub = new Sub();
        liskov.setBase(sub);
        liskov.base.get(map);
        liskov.base.get(hashMap);


        // 由于子类方法重载父类方法，下方调用的全部是子类方法
        //子类换父类
        Sub1 sub1 = new Sub1();
        liskov.setBase(sub1);
        liskov.base.get(map);
        liskov.base.get(hashMap);
    }

    public static class Base {

        private static final Logger log = LoggerFactory.getLogger(Base.class);

        public int get(Map m) {
            log.info("Base#get");
            return 1;
        }

    }

    public static class Sub extends Base {

        private static final Logger log = LoggerFactory.getLogger(Sub.class);

        /**
         * 不属于父子方法重载，属于方法重写
         *
         * @param hm 前置条件 范围缩小（父类 为 Map）
         */
        public int get(HashMap hm) {
            log.info("Sub#get");
            return 1;
        }
    }

    public static class Sub1 extends Base {

        private static final Logger log = LoggerFactory.getLogger(Sub1.class);

        /**
         * 父子方法重载
         *
         * @param hm
         */
        @Override
        public int get(Map hm) {
            log.info("Sub1#get");
            return 1;
        }
    }
}

