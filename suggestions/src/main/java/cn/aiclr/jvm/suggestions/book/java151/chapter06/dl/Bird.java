package cn.aiclr.jvm.suggestions.book.java151.chapter06.dl;

/**
 * 对于 {@link java.lang.annotation.Inherited} 注解，
 * 它表示的意思是我们只要把注解 {@link Desc} 加到父类 {@link Bird} 上，
 * 它的所有子类都会自动从父类继承 {@link Desc} 注解，不需要显式声明
 */
@Desc(color = Desc.Color.Grayish)
public abstract class Bird {
    public abstract Desc.Color getColor();
}
