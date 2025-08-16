package cn.aiclr.jvm.suggestions.book.java151.chapter07.ds;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>97.警惕泛型是不能协变和逆变的
 *
 * 注意：Java 的泛型是不支持协变和逆变的，只是能够实现协变和逆变
 *
 * 协变（covariance）和逆变（contravariance）？
 * Wiki上是这样定义的：
 *      Within the type system of a programming language,
 *      covariance and contravariance refer to the ordering of types from narrower to wider and their interchangeability or equivalence in certain situations (such as parameters, generics, and return types).
 *
 * 在编程语言的类型框架中
 * 协变和逆变是指宽类型和窄类型在某种情况下（如参数、泛型、返回值）替换或交换的特性，
 * 简单地说，协变是用一个窄类型替换宽类型，
 * 而逆变则是用宽类型覆盖窄类型。
 * 其实，在 Java 中协变和逆变我们已经用了很久了，只是我们没发觉而已
 *
 * 1）泛型不支持协变
 *  {@link java.util.ArrayList} 是 {@link java.util.List} 的子类型，（泛型不支持协变）
 *  {@link java.lang.Integer} 是 {@link java.lang.Number} 的子类型，（支持协变）
 *  里氏替换原则（Liskov Substitution Principle）在此处行不通了，
 *  原因就是 Java 为了保证运行期的安全性，
 *  必须保证泛型参数类型是固定的，
 *  所以不允许一个泛型参数可以同时包含两种类型，
 *  即使是父子类关系也不行
 *  泛型不支持协变，但可以使用通配符（Wildcard）模拟协变
 * 2）泛型不支持逆变
 *  Java 虽然可以允许逆变存在，但在对类型赋值上是不允许逆变的，
 *  不能把一个父类实例对象赋值给一个子类类型变量，
 *  泛型自然也不允许此种情况发生了，
 *  但是它可以使用 super 关键字来模拟实现
 *
 * 泛型既不支持协变也不支持逆变，带有泛型参数的子类型定义与我们经常使用的类类型也不相同
 * Integer 是 Number 的子类型
 * ArrayList<Integer> 是 List<Integer> 的子类型
 * Integer[] 是 Number[] 的子类型
 * List<Integer> 不是 List<Number> 的子类型
 * List<Integer> 不是 List<? extends Integer> 的子类型
 * List<Integer> 不是 List<? super Integer> 的子类型
 */
public class Ds {
    /**
     * base 变量发生协变，
     * base 变量是 {@link Base} 类型，它是父类，而其赋值却是子类实例 {@link Sub}，也就是用窄类型覆盖了宽类型
     * 这也叫多态（Polymorphism），两者同含义
     */
    Base base = new Sub();

    //数组支持协变
    Number[] n = new Integer[10];

    //泛型不支持协变
//        List<Number> lnErr=new ArrayList<Integer>();//编译不通过

    /**
     * <pre>泛型不支持协变，但可以使用通配符（Wildcard）模拟协变
     * {@code ? extends Number} 表示的意思是，允许 {@link java.lang.Number} 所有的子类（包括自身）作为泛型参数类型，
     * 但在运行期只能是一个具体类型，或是 {@link java.lang.Integer} 类型，或是 {@link java.lang.Double} 类型，或是 {@link java.lang.Number} 类型，
     * 也就是说通配符只是在编码期有效，运行期则必须是一个确定类型
     */
    List<? extends Number> ln = new ArrayList<Integer>();

    /**
     * <pre>泛型不支持逆变
     * 但是可以使用 super 关键字来模拟实现
     * {@code ? super Integer} 的意思是可以把所有 Integer 父类型（自身、父类或接口）作为泛型参数，
     * 这里看着就像是把一个 {@link java.lang.Number} 类型的 {@link java.util.ArrayList} 赋值给了 {@link java.lang.Integer} 类型的 {@link java.util.List}，
     * 其外观类似于使用一个宽类型覆盖一个窄类型，它模拟了逆变的实现
     */
    List<? super Integer> li = new ArrayList<Number>();
}
