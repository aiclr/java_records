package cn.aiclr.jvm.suggestions.book.java151.chapter11;

import com.google.common.base.Joiner;
import com.google.common.base.Predicate;
import com.google.common.base.Splitter;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Table;
import com.google.common.primitives.Ints;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>140.推荐使用 Guava 扩展工具包
 * 2008 年 Google 发布了 Google-collections 扩展工具包，
 * 主要是对 JDK 的 Collection 包进行了扩展，
 * 2010 年 Google 发布了 Guava 项目，
 * 其中包含了
 *      collections
 *      caching
 *      primitives support
 *      concurrency libraries
 *      common annotations
 *      I/O 等，
 * 这些都是项目编码中的基本工具包，它的主要功能
 * 1）Collections com.google.common.collect 包中主要包括四部分：
 *      1）不可变集合（Immutable Collections）包括
 *          {@link com.google.common.collect.ImmutableList}
 *          {@link com.google.common.collect.ImmutableMap}
 *          {@link com.google.common.collect.ImmutableSet}
 *          {@link com.google.common.collect.ImmutableSortedMap}
 *          {@link com.google.common.collect.ImmutableSortedSet} 等，
 *          它比不可修改集合（Unmodifiable Collections）更容易使用，效率更高，而且占用的内存更少
 *      2）多值 Map
 *          在 JDK 中，{@link java.util.Map} 中的一个键对应一个值，
 *          在 put 一个键值对时，如果键重复了，则会覆盖原有的值，在大多数情况下这比较符合实际应用，
 *          但有的时候确实会存在一个键对应多个值的情况，
 *          比如我们的通讯录，一个人可能会对应两个或三个号码，此时使用 JDK 的 Map 就有点麻烦了。
 *          在这种情况下，使用 Guava 的 {@link com.google.common.collect.Multimap} 可以很好地解决问题
 *      3）Table 表
 *          在 GIS（Geographic Information System，地理信息系统）中，
 *          经常会把一个地点标注在一个坐标上，
 *          比如把上海人民广场标注在北纬 31.23、东经 121.48 的位置上，
 *          也就是说只要给出了准确的经度和纬度就可以进行精确的定位
 *
 *          两个键决定一个值
 *
 *          这在 Guava 中是使用 {@link com.google.common.collect.Table} 来表示的
 *          其实 Guava 的 {@link com.google.common.collect.Table} 类与我们经常接触的 DBRMS 表非常类似，可以认为它是一个没有 Schema 限定的数据表
 *      4）集合工具类
 *          Guava 的集合工具类分得比较细，
 *          比如 {@link com.google.common.collect.Lists}、{@link com.google.common.collect.Maps}、{@link com.google.common.collect.Sets} 分别对应的是 {@link java.util.List}、{@link java.util.Map}、{@link java.util.Set} 的工具类，
 *          它们的使用方法比较简单
 * 2)字符串操作
 *      Guava 提供了两个非常好用的字符串操作工具：
 *          {@link com.google.common.base.Joiner} 连接器
 *          {@link com.google.common.base.Splitter} 拆分器
 *      当然，字符串的连接和拆分使用 JDK 的方法也可以实现，但是使用 Guava 更简单一些
 * 3)基本类型工具
 *      基本类型工具在 primitives 包中，是以基本类型名 +s 的方式命名的，
 *      比如 {{@link com.google.common.primitives.Ints} 是 int 的工具类，
 *      {@link com.google.common.primitives.Doubles} 是 double 的工具类，
 *      注意这些都是针对基本类型的，而不是针对包装类型的
 *
 * Guava 还提供了其他操作（如 I/O 操作），相对来说功能不是非常强大
 */
public class Fk {

    private static final Logger logger = LoggerFactory.getLogger(Fk.class);

    public static void main(String[] args) {
        //1）Collections
        collections();
        //2)字符串操作工具
        joinerAndSplitter();
        //3)基本类型工具
        baseClass();
    }

    /**
     * 1）Collections
     */
    public static void collections() {
        //不可变集合，of方法有多个重载，其目的就是为了便于在初始化的时候直接生成一个不可变集合
        //不可变列表
        ImmutableList<String> list = ImmutableList.of("A", "B", "C");
        logger.info("{}", list);

        //不可变Map
        ImmutableBiMap<Integer, String> map = ImmutableBiMap.of(1, "壹", 2, "贰", 3, "叁");
        logger.info("{}", map);

        //多值map: 一个键对应多个值
        Multimap<String, String> phoneBook = ArrayListMultimap.create();
        phoneBook.put("张三", "110");
        phoneBook.put("张三", "119");
        //输出的结果是一个包含两个元素的Collection
        logger.info("{} {}", phoneBook, phoneBook.get("张三"));//[110, 119]

        //Table表: 两个键决定一个值
        Table<Double, Double, String> g = HashBasedTable.create();
        g.put(31.23, 121.48, "人民广场");
        logger.info("{} {}", g, g.get(31.23, 121.48));
        //类似于数据库表
        Table<Integer, Integer, String> user = HashBasedTable.create();
        //第一行，第一列，值是张三
        user.put(1, 1, "张三");
        //第一行，第二列，值是李四
        user.put(1, 2, "李四");
        logger.info("{} {}", user, user.get(1, 1));

        //集合工具类
        Map<String, Integer> users = new HashMap<>();
        users.put("张三", 20);
        users.put("李四", 22);
        users.put("王五", 25);
        logger.info("{}", users);
        //lambda
        Map<String, Integer> filtedMap = Maps.filterValues(users, input -> input > 20);
        logger.info("{}", filtedMap);
        //匿名内部类写法
        filtedMap = Maps.filterValues(users, new Predicate<Integer>() {
            @Override
            public boolean apply(Integer input) {
                return input > 22;
            }
        });
        logger.info("{}", filtedMap);
    }

    /**
     * 字符串操作工具
     */
    public static void joinerAndSplitter() {
        //定义连接符号
        Joiner joiner = Joiner.on(", ");
        String str = joiner.skipNulls().join("嘿", "Guava 很不错的。");
        logger.info("{}", str);

        Map<String, String> map = new HashMap<>();
        map.put("张三", "普通员工");
        map.put("李四", "领导");
        //Joiner 不仅能够连接字符串，还能够把 Map 中的键值对串联起来，比直接输出 Map 优雅了许多
        logger.info("\n{}", Joiner.on("\n").withKeyValueSeparator(" 是 ").join(map));

        //Splitter是做字符拆分的
        str = "你好，Guava";
        for (String s : Splitter.on("，").split(str)) {
            logger.info("{}", s);
        }

        //按照固定长度分隔
        //fixedLength 方法，它是按照给定长度进行拆分的，
        // 比如在进行格式化打印的时候，一行最大可以打印 120 个字符，此时使用该方法就非常简单了
        for (String s : Splitter.fixedLength(2).split(str)) {
            logger.info("{}", s);
        }
    }

    /**
     * 基本类型工具
     */
    public static void baseClass() {
        int[] ints = {10, 9, 20, 40, 80};
        //从数组中取最大值
        logger.info("{}", Ints.max(ints));

        List<Integer> integers = new ArrayList<>();
        integers.add(1);
        integers.add(2);
        integers.add(3);
        //把包装类型集合转换为基本类型数组
        ints = Ints.toArray(integers);
        logger.info("{}", ints);
    }

}