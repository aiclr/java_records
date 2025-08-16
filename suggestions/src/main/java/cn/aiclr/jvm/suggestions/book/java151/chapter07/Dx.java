package cn.aiclr.jvm.suggestions.book.java151.chapter07;

/**
 * <pre>102.适时选择 getDeclared××× 和 get×××
 *
 * Java 的 Class 类提供了很多的 getDeclared××× 方法和 get××× 方法，
 * 例如
 * {@link java.lang.Class#getDeclaredMethod(String, Class[])} 和 {@link java.lang.Class#getMethod(String, Class[])}成对出现，
 * {@link java.lang.Class#getDeclaredConstructors()} 和 {@link java.lang.Class#getConstructors()}也是成对出现
 *
 * {@link java.lang.Class#getMethods()} 方法获得的是所有 public 访问级别的方法，包括从父类继承的方法，
 * {@link java.lang.Class#getDeclaredMethods()} 获得是自身类的所有方法，包括公用（public）方法、私有（private）方法等，而且不受限于访问权限
 *
 * Java 之所以如此处理，
 * 是因为反射本意只是正常代码逻辑的一种补充，
 * 而不是让正常代码逻辑产生翻天覆地的变动，
 * 所以 public 的属性和方法最容易获取，
 * 私有属性和方法也可以获取，但要限定本类。
 *
 * 列出所有继承自父类的方法，
 * 先获得父类，
 * 然后使用 {@link java.lang.Class#getDeclaredMethods()}，
 * 之后持续递归即可
 */
public class Dx {
    public void publicMethod() {

    }

    private void privateMethod() {

    }
}

