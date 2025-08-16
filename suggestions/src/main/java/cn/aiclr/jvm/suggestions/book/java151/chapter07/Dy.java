package cn.aiclr.jvm.suggestions.book.java151.chapter07;

/**
 * <pre>103.反射访问 {@link java.lang.reflect.Field} 或 {@link java.lang.reflect.Method} 时将 {@link java.lang.reflect.Field#setAccessible(boolean)}、{@link java.lang.reflect.Method#setAccessible(boolean)} 设置为 true
 *
 * Java 中通过反射执行一个方法的过程如下：
 * 获取一个方法对象，
 * 然后根据 {@link java.lang.reflect.Method#canAccess(Object)}、{@link java.lang.reflect.Method#isAccessible()} 返回值确定是否能够执行，
 * 如果返回值为 false 则需要调用 {@link java.lang.reflect.Method#setAccessible(boolean)} 设置为 true，
 * 最后再调用 {@link java.lang.reflect.Method#invoke(Object, Object...)} 执行方法
 *
 * 习惯用法：
 *      通过反射方式执行方法时，必须在 {@link java.lang.reflect.Method#invoke(Object, Object...)} 之前 {@link java.lang.reflect.Method#canAccess(Object)} 检查属性。
 *      但 {@link java.lang.reflect.Method#canAccess(Object)} 并不是用来决定是否可访问的
 *
 * {@link java.lang.reflect.Method#canAccess(Object)} 的结果并不是我们语法层级理解的访问权限，而是指是否更容易获得，是否进行安全检查
 * 动态修改一个类或方法或执行方法时都会受 Java 安全体系的制约，
 * 而安全的处理是非常消耗资源的（性能非常低），
 * 因此对于运行期要执行的方法或要修改的属性就提供了 {@link java.lang.reflect.Method#setAccessible(boolean)} 可选项：
 * 由开发者决定是否要逃避安全体系的检查。
 *
 * {@link java.lang.reflect.AccessibleObject} 是 {@link java.lang.reflect.Field}、{@link java.lang.reflect.Method}、{@link java.lang.reflect.Constructor} 的父类，
 * 决定其是否可以快速访问而不进行访问控制检查，
 * 在 {@link java.lang.reflect.AccessibleObject} 类中是以 {@link java.lang.reflect.AccessibleObject#override} 变量保存该值的，
 * 但是具体是否快速执行是在 {@link java.lang.reflect.Method} 类的 {@link java.lang.reflect.Method#invoke(Object, Object...)} 方法中决定的
 *
 * {@link java.lang.reflect.Method#canAccess(Object)} 只是用来判断是否需要进行安全检查的，如果不需要则直接执行，
 * 这就可以大幅度地提升系统性能（因为取消了安全检查，所以可以运行 private 方法、访问 private 属性）。
 * 在大量的反射情况下，设置 {@link java.lang.reflect.Method#setAccessible(boolean)} 为 true 可以提升性能20倍以上
 *
 * {@link java.lang.reflect.AccessibleObject} 的其他两个子类 {@link java.lang.reflect.Field} 和 {@link java.lang.reflect.Constructor} 与 {@link java.lang.reflect.Method} 的情形相似：
 * {@link java.lang.reflect.Method#canAccess(Object)} 决定 {@link java.lang.reflect.Field} 和 {@link java.lang.reflect.Constructor} 是否受访问控制检查。
 * 在设置 {@link java.lang.reflect.Field} 或执行 {@link java.lang.reflect.Constructor} 时，
 * 务必要设置 {@link java.lang.reflect.Method#setAccessible(boolean)} 为 true，
 * 这并不仅仅是因为操作习惯的问题，还是在为我们系统的性能考虑
 */
public class Dy {

    int doStuff() {
        return 123;
    }

    public final String doFinalStuff() {
        return "final doStuff";
    }
}

