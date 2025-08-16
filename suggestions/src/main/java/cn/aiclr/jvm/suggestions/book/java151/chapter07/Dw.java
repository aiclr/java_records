package cn.aiclr.jvm.suggestions.book.java151.chapter07;

/**
 * <pre>101.注意 Class 类的特殊性
 * Java 语言是先把 Java 源文件编译成后缀为 class 的字节码文件，
 * 然后再通过 ClassLoader 机制把这些类文件加载到内存中，
 * 最后生成实例执行的，这是 Java 处理的基本机制
 *
 * Java 使用一个元类（MetaClass）来描述加载到内存中的类数据，这就是 Class 类，
 * 它是一个描述类的类对象，比如 Dog.class 文件加载到内存中后就会有一个 Class 实例对象描述之。
 * 因为 Class 类是“类中类”，也就有预示着它有很多特殊的地方：
 *  1）无构造函数。Java中的类一般都有构造函数，用于创建实例对象，
 *      但是 Class 类却没有构造函数，不能实例化，
 *      Class 对象是在加载类时由 JVM 通过调用类加载器中的 defineClass 方法自动构造的
 *  2）可以描述基本类型。
 *      虽然 8 个基本类型在 JVM 中并不是一个对象，它们一般存在于栈内存中，
 *      但是 Class 类仍然可以描述它们，例如可以使用 int.class 表示 int 类型的类对象
 *  3）其对象都是单例模式。
 *      一个 Class 的实例对象描述一个类，并且只描述一个类，
 *      反过来也成立，一个类只有一个 Class 实例对象
 *
 * Class 类是 Java 反射的入口，
 * 只有在获得了一个类的描述对象后才能动态地加载、调用，
 * 一般获得一个 Class 对象有三种途径
 *      1)类属性方式，如 String.class
 *      2)对象的 {@link  java.lang.Object#getClass()}方法，如 {@code new String().getClass()}
 *      3){@link java.lang.Class#forName(String)} 方法加载，如 {@code Class.forName("java.lang.String")}
 * 获得了 Class 对象后，
 * 就可以通过 {@link java.lang.Class#getAnnotations()} 获得注解，
 * 通过 {@link java.lang.Class#getMethods()} 获得方法，
 * 通过 {@link java.lang.Class#getConstructors()} 获得构造函数等，这为后续的反射代码铺平了道路
 */
public class Dw {
}

