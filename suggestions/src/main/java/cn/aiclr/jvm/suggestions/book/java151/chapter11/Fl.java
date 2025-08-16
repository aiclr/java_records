package cn.aiclr.jvm.suggestions.book.java151.chapter11;

import cn.aiclr.jvm.suggestions.book.java151.chapter09.ew.ReentrantLockAndSync;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.mutable.MutableInt;
import org.apache.commons.text.RandomStringGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <pre>141.Apache 扩展包
 * Apache Commons 通用扩展包基本上是每个项目都会使用的，只是使用的多少不同而已，
 * 一般情况下
 * 1）lang3 包用作 JDK 的基础语言扩展
 *      lang3 是 Apache Commons 团队发布的工具包，要求 jdk 版本在 1.5 以上，
 *      相对于 lang 来说完全支持 java5 的特性，废除了一些旧的 API。
 *      该版本无法兼容旧有版本，于是为了避免冲突改名为 lang3
 *      Apache 的 Lang 功能实在是太实用了，它的很多工具类都是我们在开发过程中经常会用到的，
 *      虽然采用 JDK 的原始类也可以实现，但会花费更多的精力，
 *      而且 Lang 的更新频度很高，用它时不用担心会有太多的 Bug
 *      1）字符串操作工具类
 *          JDK 提供了 String 类，也提供了一些基本的操作方法，
 *          但是要知道 String 类在项目中是应用最多的类，
 *          这也预示着 JDK 提供的 String 工具不足以满足开发需求，
 *          Lang 包弥补了这个缺陷，
 *          它提供了诸如
 *              {@link org.apache.commons.lang3.StringUtils}（基本的 String 操作类）、
 *              {@link org.apache.commons.text.StringEscapeUtils}（String 的转义工具）、
 *              {@link org.apache.commons.text.RandomStringGenerator}（随机字符串工具）等非常实用的工具
 *      2）{@link org.apache.commons.lang3.ObjectUtils} 工具类
 *          每个类都有 equals、hashCode、toString 方法，
 *          如果我们自己编写的类需要覆写这些方法，就需要考虑很多的因素了，特别是 equals 方法
 *      3）可变的基本类型
 *          基本类型都有相应的包装类型，
 *          但是包装类型不能参与加、减、乘、除运算，要运算还得转化为基本类型
 *      4）其他 Utils 工具
 * 2）BeanUtils JavaBean 的操作工具包，
 *      不仅可以实现属性的拷贝、转换等，还可以建立动态的 Bean，甚至建立一些自由度非常高的 Bean
 *      1）属性拷贝
 *          在分层开发时经常会遇到 PO（Persistence Object）和 VO（Value Object）之间的转换问题，
 *          有多种方法可以解决之，比如自己写代码 PO.setXXX(VO.getXXX())，但是在属性较多的时候容易出错，
 *          最好的办法就是使用 BeanUtils 来操作
 *      2）动态 Bean 和自由 Bean
 *          定义一个 Bean 必然会需要一个类，比如 User、Person 等，而且还必须在编译期定义完毕，生成 .class 文件，
 *          虽然 Bean 是一个有固定格式的数据载体，严格要求确实没错，但在某些时候这限制了 Bean 的灵活性，
 *          比如要在运行期生成一个动态 Bean，或者在需要生成无固定格式的 Bean 时可以使用 BeanUtils 包解决该问题
 *      3）转换器
 *          期望把一个 Bean 的所有 String 类型属性在输出之前都加上一个前缀
 *          一个一个进行属性过滤
 *          或者使用反射来检查属性类型是否是 String，然后加上前缀
 *          这样是可以解决，但不优雅
 *          可以使用 BeanUtils 包解决
 * 3）Collections 用作集合扩展，
 *      1）Bag
 *          Bag 是 Collections 中的一种，它可以容纳重复元素，
 *          与 List 的最大不同点是它提供了重复元素的统计功能，
 *          比如一个盒子中有 100 个球，现在要计算出蓝色球的数量，使用 Bag 就很容易实现
 *      2）lazy 系列
 *          在集合中的元素被访问时它才会生成，这也就涉及一个元素的生成问题了，可通过 Factory 的实现类来完成
 *      3）双向 Map
 *          JDK 中的 Map 要求键必须唯一，
 *          而双向 Map（Bidirectory Map）则要求键、值都必须唯一，
 *          也就是键值是一一对应的，
 *          此类 Map 的好处就是既可以根据键进行操作，也可以反向根据值进行操作
 * 4）DBCP 用作数据库连接池等
 */
public class Fl {

    private static final Logger logger = LoggerFactory.getLogger(ReentrantLockAndSync.class);

    public static void main(String[] args) {
        lang3();
        mutable();

        Person jack = new Person("jack", 12);
        Person tom = new Person("tom", 12);
        logger.info("{} equals {} = {}", jack, tom, jack.equals(tom));
    }

    /**
     * 字符串操作工具类
     */
    public static void lang3() {
        String str = "abcdawdaw";
        //判空
        logger.info("{}", StringUtils.isEmpty(str));

        //a出现次数
        logger.info("{}", StringUtils.countMatches(str, "a"));

        //随机生成，长度为10的仅字母的字符串
        RandomStringGenerator generator = new RandomStringGenerator.Builder()
                .withinRange('a', 'z') // 或 ('A', 'Z')，或 ('a', 'z') 与 ('A', 'Z') 同时支持
                .filteredBy(Character::isLetter) // 确保只包含字母
                .get();
        logger.info("{}", generator.generate(10));

        //随机生成，长度为10的ASCII字符串
        generator = new RandomStringGenerator.Builder()
                .withinRange(' ', '~')// ASCII 可打印字符范围：\u0020 (32) 到 \u007E (126)
                .get();

        logger.info("{}", generator.generate(10));


    }

    /**
     * 可变的基本类型
     */
    public static void mutable() {
        //声明一个可变的int类型
        MutableInt mi = new MutableInt(10);
        //mi加10
        mi.add(10);
        logger.info("{}", mi);

        //自加1
        mi.increment();
        logger.info("{}", mi);
    }

    /**
     * Object 工具类
     */
    static class Person {

        private final String name;

        private final int age;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public String toString() {
            //自定义输出格式
            return new ToStringBuilder(this)
                    .append("姓名", name)
                    .append("年龄", age)
                    .toString();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Person person = (Person) o;
            //只有姓名相同，就认为两个对象相等
            return new EqualsBuilder()
                    .appendSuper(super.equals(o))
                    .append(age, person.age)
                    .isEquals();
        }

        @Override
        public int hashCode() {
            //自定义hashCode
            return HashCodeBuilder.reflectionHashCode(this);
        }

    }

}
