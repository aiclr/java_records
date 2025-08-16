package cn.aiclr.jvm.suggestions.book.java151.chapter06.dj;

/**
 * 通过抽象方法生成产品
 */
public enum EnumAbstractCarFactory {

    FordCar {
        @Override
        public Car create() {
            return new FordCar();
        }
    }, BuickCar {
        @Override
        public Car create() {
            return new BuickCar();
        }
    };

    public abstract Car create();
}
