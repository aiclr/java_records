package cn.aiclr.jvm.suggestions.book.java151.chapter03;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * <pre>49.推荐覆写 toString 方法
 * Java 提供的默认 toString 方法不友好，打印出来看不懂: 类名+@+hashCode
 * println 的实现机制：
 *      如果是一个原始类型就直接打印，
 *      如果是一个引用类型，则打印出其 toString 方法的返回值
 * 当 Bean 的属性较多时，自己实现就不可取了，
 * 不过可以使用 apache 的 commons 工具包中的 {@link org.apache.commons.lang3.builder.ToStringBuilder} 类，简洁、实用又方便
 */
public class Bw {
    public static void main(String[] args) {
        System.out.println(new PersonBw("二狗"));
    }
}

class PersonBw {
    private String name;

    public PersonBw(String _name) {
        name = _name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
//        return String.format("%s.name=%s", this.getClass(), name);
    }
}