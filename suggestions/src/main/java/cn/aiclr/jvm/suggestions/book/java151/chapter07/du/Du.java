package cn.aiclr.jvm.suggestions.book.java151.chapter07.du;

/**
 * <pre>99.严格限定泛型类型采用多重界限
 * 案例
 *      在公交车费优惠系统中，对部分人员（如工资低于 2500 元的上班族并且是站立着的乘客）车费打 8 折、
 *      注意这里的类型参数有两个限制条件：一为上班族；二为是乘客。
 *      具体到我们的程序中就应该是一个泛型参数具有两个上界（Upper Bound）
 *
 * 在 Java 的泛型中，可以使用 “&” 符号关联多个上界并实现多个边界限定，
 *      而且只有上界才有此限定，
 *      下界没有多重限定的情况
 *
 * 多个下界，编码者可自行推断出具体的类型，
 * 比如 {@code ? super Integer} 和 {@code ? extends Double}，
 * 可以更细化为 Number 类型了，
 * 或者 Object 类型了，
 * 无须编译器推断
 */
public class Du {

    public static void main(String[] args) {
        discount(new Me());
    }

    /**
     * <pre>使用 “&” 符号设定多重边界（Multi Bounds），
     * 指定泛型类型 T 必须是 {@link Staff} 和 {@link Passenger} 的共有子类型，
     * 此时变量 t 就具有了所有限定的方法和属性，要再进行判断就易如反掌了
     */
    public static <T extends Staff & Passenger> void discount(T t) {
        if (t.getSalary() < 2500 && t.isStanding()) {
            System.out.println("80%");
        }
    }
}
