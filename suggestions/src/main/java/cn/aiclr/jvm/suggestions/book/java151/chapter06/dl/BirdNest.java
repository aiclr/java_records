package cn.aiclr.jvm.suggestions.book.java151.chapter06.dl;

/**
 * 鸟窝工厂方法
 */
public enum BirdNest {

    Sparrow;

    /**
     * 获取 {@link Sparrow} 类上的颜色注解（此类并未加注解，但是父类加了注解）
     */
    public Bird reproduce() {
        Desc desc = Sparrow.class.getAnnotation(Desc.class);
        return desc == null ? new Sparrow() : new Sparrow(desc.color());
    }
}
