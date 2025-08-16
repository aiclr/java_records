package cn.aiclr.jvm.suggestions.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * <pre>Java 的泛型类型只存在于编译期，
 * 因为该工具只支持继承的泛型类，
 * 如果是在 Java 编译时已经确定了泛型类的类型参数，那当然可以通过泛型获得
 *
 * 对于反射效率问题，不要做任何的提前优化和预期，这基本上是杞人忧天，
 * 很少有项目是因为反射问题引起系统效率故障的（除非是拷贝工的垃圾代码，这不在我们的讨论范围之内），
 * 而且根据二八原则，80%的性能消耗在20%的代码上，
 * 这20%的代码才是我们关注的重点，不要单单把反射作为重点关注对象
 */
public class GenericUtils {

    /**
     * 获得一个泛型类的第一个泛型类型
     *
     * @param clz
     * @param <T>
     * @return
     */
    public static <T> Class<T> getGenericClassType(Class<?> clz) {
        Type type = clz.getGenericSuperclass();
        if (type instanceof ParameterizedType pt) {
            Type[] types = pt.getActualTypeArguments();
            if (types.length > 0 && types[0] instanceof Class) {
                //若有多个泛型参数，依据位置索引返回
                return (Class<T>) types[0];
            }
        }
        return (Class<T>) Object.class;
    }
}
