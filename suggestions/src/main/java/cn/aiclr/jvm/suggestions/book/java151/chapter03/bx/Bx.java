package cn.aiclr.jvm.suggestions.book.java151.chapter03.bx;

/**
 * <pre>50.使用 package-info 类为包服务
 * Java中有一个特殊的类：package-info 类，它是专门为本包服务的
 * 1.它不能随便被创建
 *      在一般的 IDE 中，Eclipse、package-info 等文件是不能随便被创建的，会报 “Type name is notvalid” 错误，类名无效。
 *      在 Java 变量定义规范中规定如下字符是允许的：字母、数字、下划线，以及那个不怎么常用的 $ 符号，不过中划线可不在之列，
 *      用记事本创建一个，然后拷贝进去项目再改一下就成了，更直接的办法就是从别的项目中拷贝过来
 * 2.它服务的对象很特殊
 *      描述和记录本包信息
 * 3.package-info 类不能有实现代码
 *      package-info 类再怎么特殊也是一个类，也会被编译成 package-info.class，
 *      但是在 package-info.java 文件里不能声明 package-info 类
 *
 * 不可以继承，没有接口，没有类间关系（关联、组合、聚合等）等，其特殊的作用
 * 1.声明友好类和包内访问常量
 *      这个比较简单，而且很实用，比如一个包中有很多内部访问的类或常量，
 *      就可以统一放到 package-info 类中，这样很方便，而且便于集中管理，
 *      可以减少友好类到处游走的情况
 *      把一个包需要的类和常量都放置在本包下，在语义上和习惯上都能让程序员更适应
 * 2.为在包上标注注解提供便利
 *      比如我们要写一个注解（Annotation），查看一个包下的所有对象，
 *      只要把注解标注到 package-info 文件中即可，
 *      而且在很多开源项目也采用了此方法，
 *      比如 Struts2 的 @namespace、Hibernate 的 @FilterDef 等。
 * 3.提供包的整体注释说明
 *      如果是分包开发，也就是说一个包实现了一个业务逻辑或功能点或模块或组件，
 *      则该包需要有一个很好的说明文档，说明这个包是做什么用的，版本变迁历史，与其他包的逻辑关系等，
 *      package-info 文件的作用在此就发挥出来了，这些都可以直接定义到此文件中，
 *      通过 javadoc 生成文档时，会把这些说明作为包文档的首页，让读者更容易对该包有一个整体的认识。
 *      当然在这点上它与 package.html 的作用是相同的，不过 package-info 可以在代码中维护文档的完整性，
 *      并且可以实现代码与文档的同步更新
 */
public class Bx {
    public static void main(String[] args) {
        new PkgClass().test();
        System.out.println(PkgConst.PACKAGE_CONST);
    }
}
