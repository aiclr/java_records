package cn.aiclr.jvm.suggestions.book.java151.chapter07.ee;

/**
 * <pre>对于 UserDao 类，
 * 编译器编译时已经明确了其参数类型是 String，
 * 因此可以通过反射的方式来获取其类型，
 * 这也是 {@link cn.aiclr.jvm.suggestions.utils.GenericUtils#getGenericClassType(Class)} 方法使用的场景
 */
public class UserDao extends BaseDao<User> {
}
