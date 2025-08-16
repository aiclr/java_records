package cn.aiclr.jvm.suggestions.book.java151.chapter06.dm;

/**
 * <pre>91.枚举和注解结合使用威力更大
 *
 * 注解的写法和接口很类似，都采用了关键字 interface，
 * 而且都不能有实现代码，
 * 常量定义默认都是 public static final 类型的等，
 * 它们的主要不同点是：
 * 注解要在 interface 前加上 @ 字符，而且不能继承，不能实现，这经常会给我们的开发带来一些障碍
 *
 * ACL（Access Control List，访问控制列表）设计案例
 * ACL三要素
 *  1）资源，有哪些信息是要被控制起来的
 *  2）权限级别，不同的访问者规划在不同的级别中
 *  3）控制器（也叫鉴权人），控制不同的级别访问不同的资源（鉴权人是整个ACL的设计核心）
 */
public class Dm {

    public static void main(String[] args) {

        Library library = new Library();
        Access access = library.getClass().getAnnotation(Access.class);
        CommonIdentifier level = access.level();

        System.out.println(level);

        if (!level.identify()) {
            System.out.println(CommonIdentifier.REFUSE_WORD);
        }
    }
}
