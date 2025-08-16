package cn.aiclr.jvm.suggestions.book.java151.chapter03;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>39.使用匿名类的构造函数
 *
 * 匿名类虽然没有名字，但也是可以有构造函数的，它用构造函数块来代替，
 * 那下面的3个输出就很清楚了：虽然父类相同，但是类还是不同的
 */
public class Bm {

    public static void main(String[] args) {
        List l1 = new ArrayList();

        //l2代表的是一个匿名类的声明和赋值，它定义了一个继承于ArrayList的匿名类，只是没有任何的覆写方法而已
        List l2 = new ArrayList() {

        };

        //l3代表的是一个匿名类的声明和赋值，它定义了一个继承于ArrayList的匿名类，只是没有任何的覆写方法而已
        //与l2相比多了一个初始化块而已，可以起到构造函数的功能
        List l3 = new ArrayList() {
            {
                System.out.println("匿名内部类初始化代码块");
            }
        };

        System.out.println(l1.getClass() == l2.getClass());
        System.out.println(l1.getClass() == l3.getClass());
        System.out.println(l2.getClass() == l3.getClass());

        System.out.println(l1.getClass());
        System.out.println(l2.getClass());
        System.out.println(l3.getClass());


        class SubBm extends ArrayList {
        }

        List l22 = new SubBm() {
        };

        /**
         * 一个类肯定有一个构造函数，且构造函数的名称和类名相同，
         * 那问题来了：匿名类的构造函数是什么呢？它没有名字呀！
         * 很显然，初始化块 就相当于它的构造函数。
         * 当然，一个类中的初始化代码块可以是多个，也就是说可以出现如下代码：
         * List l3=new ArrayList(){{}{}{}{}{}{}{}};
         */
        class SubBm2 extends ArrayList {
        }
        List l33 = new SubBm2() {
            {
                System.out.println("初始化代码块");
            }
        };

    }


}
