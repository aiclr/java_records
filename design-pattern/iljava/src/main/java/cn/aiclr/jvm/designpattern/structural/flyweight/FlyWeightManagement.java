package cn.aiclr.jvm.designpattern.structural.flyweight;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>管理共享数据
 * 从此管理类获取共享数据
 *
 * 查看 jdk 享元模式源码 {@link Integer#valueOf(int)}。
 * 当使用 {@link Integer#valueOf(int)} 声明 {@link Integer} 时，
 * 如果值在 [-128,127] 范围内时，使用 享元模式，返回的是同一个 Integer 对象。
 * [-128,127] 范围右边界可通过 jvm 参数配置 VM.getSavedProperty("java.lang.Integer.IntegerCache.high")。
 * 默认 Integer.IntegerCache.high = 127
 * {@code i >= -128 && i <= Integer.IntegerCache.high}
 * Integer 对象保存在 Integer.IntegerCache.cache[] 数组内
 * 数组索引 index 与 Integer 对象对应
 */
public class FlyWeightManagement {

    private FlyWeightManagement() {
    }

    private static final FlyWeightManagement INSTANCE = new FlyWeightManagement();

    public static FlyWeightManagement getInstance() {
        return INSTANCE;
    }

    //存放共享内容的集合
    Map<String, FlyWeight> flyWeight = new HashMap<>();

    public FlyWeight get(String key) {
        if (!flyWeight.containsKey(key)) {
            flyWeight.put(key, new StateShared(key));
        }
        return flyWeight.get(key);
    }

    public int count() {
        return flyWeight.size();
    }
}
