package cn.aiclr.jvm.suggestions.book.java151.chapter06;

import java.util.EnumSet;

/**
 * <pre>89.枚举项的数量限制在 64 个以内
 * 注意：
 *  枚举项数量不要超过 64，否则建议拆分
 *
 *  为了更好地使用枚举，Java 提供了两个枚举集合：{@link java.util.EnumSet} 和 {@link java.util.EnumMap}，这两个集合的使用方法都比较简单，
 *  {@link java.util.EnumSet} 表示其元素必须是某一枚举的枚举项，
 *  {@link java.util.EnumMap} 表示 Key 值必须是某一枚举的枚举项，
 *  由于枚举类型的实例数量固定并且有限，相对来说 {@link java.util.EnumSet} 和 {@link java.util.EnumMap} 的效率会比其他 Set 和 Map 要高
 *
 *  在项目中一般会把枚举用作常量定义，可能会定义非常多的枚举项，
 *  然后通过 {@link java.util.EnumSet} 访问、遍历，但它对不同的枚举数量有不同的处理方式
 *
 * 枚举项的排序值 ordinal 是从 0、1、2 ... 依次递增的，没有重号，没有跳号，
 * {@link java.util.RegularEnumSet} 就是利用这一点把每个枚举项的 ordinal 映射到一个 long 类型的每个位上的，
 * 注意看 {@link java.util.RegularEnumSet#addAll()} 的 elements 元素，
 * <code>
 *
 *      private long elements = 0L;
 *
 *      void addAll() {
 *         if (universe.length != 0)
 *             elements = -1L >>> -universe.length;
 *     }
 * </code>
 * 它使用了无符号右移操作，
 * 并且操作数是负值，
 * 位移也是负值，这表示是负数（符号位是1）的“无符号左移”：
 * 符号位为0，并补充低位，
 * 简单地说，Java 把一个不多于 64 个枚举项的枚举映射到了一个 long 类型 变量上。
 * 这才是 {@link java.util.EnumSet} 处理的重点，其他的 size 方法、contains 方法等都是根据 elements 计算出来的。
 * 想想看，一个 long 类型的数字包含了所有的枚举项，其效率和性能肯定是非常优秀的
 *
 * long 类型是 64 位的，所以 {@link java.util.RegularEnumSet} 类型也就只能负责枚举项数量不大于 64 的枚举
 * 大于 64 则由 {@link java.util.JumboEnumSet} 处理
 * <code>
 *      public static <E extends Enum<E>> EnumSet<E> noneOf(Class<E> elementType) {
 *         Enum<?>[] universe = getUniverse(elementType);
 *         if (universe == null)
 *             throw new ClassCastException(elementType + " not an enum");
 *
 *         if (universe.length <= 64)
 *             return new RegularEnumSet<>(elementType, universe);
 *         else
 *             return new JumboEnumSet<>(elementType, universe);
 *     }
 * </code>
 * {@link java.util.JumboEnumSet} 类把枚举项按照 64 个元素一组拆分成了多组，
 * 每组都映射到一个 long 类型的数字上，
 * 然后该数组再放置到 elements 数组中。
 * 简单来说 {@link java.util.JumboEnumSet} 类的原理与 {@link java.util.RegularEnumSet} 相似，
 * 只是 {@link java.util.JumboEnumSet} 使用了 long 数组 容纳更多的枚举项 {@code  private long elements[];}
 */
public class Dk {
    /**
     * <pre>追踪一下 {@link java.util.EnumSet#allOf(Class)} 和 {@link java.util.EnumSet#noneOf(Class)} 方法
     * 发现当枚举项数量小于等于64时，创建一个 {@link java.util.RegularEnumSet} 实例对象，
     * 大于64时则创建一个 {@link java.util.JumboEnumSet} 实例对象
     */
    public static void main(String[] args) {
        EnumSet<ConstDk> cs = EnumSet.allOf(ConstDk.class);
        EnumSet<LargeConstDk> lcs = EnumSet.allOf(LargeConstDk.class);
        System.out.println(cs.size());//64
        System.out.println(lcs.size());//65
        System.out.println(cs.getClass());//class java.util.RegularEnumSet
        System.out.println(lcs.getClass());//class java.util.JumboEnumSet
    }

    /**
     * 64项
     */
    enum ConstDk {
        A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z,
        AA, AB, AC, AD, AE, AF, AG, AH, AI, AJ, AK, AL, AM, AN, AO, AP, AQ, AR, AS, AT, AU, AV, AW, AX, AY, AZ,
        BA, BB, BC, BD, BE, BF, BG, BH, BI, BJ, BK, BL;
    }

    /**
     * 65项
     */
    enum LargeConstDk {
        A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z,
        AA, AB, AC, AD, AE, AF, AG, AH, AI, AJ, AK, AL, AM, AN, AO, AP, AQ, AR, AS, AT, AU, AV, AW, AX, AY, AZ,
        BA, BB, BC, BD, BE, BF, BG, BH, BI, BJ, BK, BL, BM;
    }
}
