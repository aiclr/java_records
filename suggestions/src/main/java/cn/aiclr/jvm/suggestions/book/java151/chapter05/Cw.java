package cn.aiclr.jvm.suggestions.book.java151.chapter05;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <pre>75.集合中的元素必须做到 compareTo 和 equals 同步
 *
 * indexOf 方法查找时: 遍历每个元素，然后比较 equals 方法的返回值，equals 判断元素是否相等
 * binarySearch 二分法查找时: 依据的是每个元素的 compareTo 方法返回值，compareTo 是判断元素在排序中的位置是否相同
 *
 * 一个是决定排序位置，一个是决定相等，那我们就应该保证当排序位置相同时，其 equals 也相同，否则就会产生逻辑混乱
 */
public class Cw {

    private static final Logger logger = LoggerFactory.getLogger(Cw.class);

    public static void main(String[] args) {
        List<CityCw> cityCws = new ArrayList<>();
        cityCws.add(new CityCw("021", "上海"));
        cityCws.add(new CityCw("021", "沪"));

        Collections.sort(cityCws);
        logger.info("{}", cityCws);

        CityCw cityCw = new CityCw("021", "沪");
        int index = cityCws.indexOf(cityCw);//equals比较code
        logger.info("{}", index);
        index = Collections.binarySearch(cityCws, cityCw);//compareTo比较name
        logger.info("{}", index);
    }

}

class CityCw implements Comparable<CityCw> {
    private String code;
    private String name;

    @Override
    public int compareTo(CityCw o) {
        //按照城市名称排序
        return new CompareToBuilder().append(name, o.name).toComparison();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CityCw cityCw = (CityCw) o;
        //与 compareTo 不一致
        return new EqualsBuilder().append(code, cityCw.code).isEquals();
        //与 compareTo 一致
//        return new EqualsBuilder().append(name, cityCw.name).isEquals();
    }

    public CityCw(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CityCw{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
