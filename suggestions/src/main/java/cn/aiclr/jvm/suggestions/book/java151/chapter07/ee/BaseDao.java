package cn.aiclr.jvm.suggestions.book.java151.chapter07.ee;

import cn.aiclr.jvm.suggestions.utils.GenericUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;

/**
 * <pre>BaseDao 和 UserDao 是 ORM 中的常客，
 * BaseDao 实现对数据库的基本操作，比如增删改查，
 * 而 UserDao 则是一个比较具体的数据库操作，其作用是对 User 表进行操作，
 * 如果 BaseDao 能够提供足够多的基本方法，
 * 比如单表的增删改查，那些与 UserDao 类似的 BaseDao 子类就可以省去大量的开发工作。
 * 但问题是持久层的 session 对象（这里模拟的是 Hibernate Session ）需要明确一个具体的类型才能操作，
 * 比如 get 查询，需要获得两个参数：实体类类型（用于确定映射的数据表）和主键
 * 获取实体类类型
 *  最好的办法就是父类泛型化，
 *  子类明确泛型参数，
 *  然后通过反射读取相应的类型即可，
 *  于是就有了我们代码中的 clz 变量：
 *      通过反射获得泛型类型。
 *      如此实现后，UserDao 可以不用定义任何方法，
 *      继承过来的父类操作方法已经能满足基本需求了，
 *      这样代码结构清晰，可读性又好
 *
 * 如果考虑反射效率问题，
 * 没有 clz 变量，不使用反射，
 * 每个 BaseDao 的子类都要实现一个查询操作，
 * 代码将会大量重复，
 * 违反了 “Don’t Repeat Yourself” 这条最基本的编码规则，
 * 这会致使项目重构、优化难度加大，代码的复杂度也会提高很多
 */
public abstract class BaseDao<T> {

    protected static final Logger logger = LoggerFactory.getLogger(BaseDao.class);

    private final Class<T> clz = GenericUtils.getGenericClassType(getClass());

    public T get(long id) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        logger.info("{} - get({})", clz.getSimpleName(), id);
        return clz.getConstructor().newInstance();
    }
}
