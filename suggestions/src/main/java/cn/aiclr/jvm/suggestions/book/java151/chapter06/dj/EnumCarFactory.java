package cn.aiclr.jvm.suggestions.book.java151.chapter06.dj;

/**
 * 枚举非静态方法实现工厂方法模式
 */
public enum EnumCarFactory {
    FordCar, BuickCar;

    public Car create() {
        return switch (this) {
            case FordCar -> new FordCar();
            case BuickCar -> new BuickCar();
        };
    }
}
