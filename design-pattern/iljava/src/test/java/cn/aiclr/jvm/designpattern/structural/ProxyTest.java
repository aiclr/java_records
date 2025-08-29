package cn.aiclr.jvm.designpattern.structural;

import cn.aiclr.jvm.designpattern.structural.proxy.proxycglib.Dog;
import cn.aiclr.jvm.designpattern.structural.proxy.proxycglib.ProxyCglibFactory;
import cn.aiclr.jvm.designpattern.structural.proxy.proxydynamic.ProxyFactory;
import cn.aiclr.jvm.designpattern.structural.proxy.proxydynamic.Student;
import cn.aiclr.jvm.designpattern.structural.proxy.proxydynamic.StudentImpl;
import cn.aiclr.jvm.designpattern.structural.proxy.proxystatic.Teacher;
import cn.aiclr.jvm.designpattern.structural.proxy.proxystatic.TeacherImpl;
import cn.aiclr.jvm.designpattern.structural.proxy.proxystatic.TeacherProxy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

@DisplayName("代理模式")
class ProxyTest {
    private static final Logger log = LoggerFactory.getLogger(ProxyTest.class);

    @Test
    @DisplayName("静态代理")
    void staticProxyTest() {
        Teacher teacher = new TeacherImpl();
        TeacherProxy proxy = new TeacherProxy(teacher);
        proxy.teach();
    }

    @Test
    @DisplayName("jdk动态代理")
    void dynamicProxyTest() {
        //创建目标对象
        Student student = new StudentImpl();
        //创建代理对象
        Student proxy = (Student) new ProxyFactory(student).getProxyInstance();

        //会调用Object.toString()方法，也会被代理增强
        log.info("{}", proxy.toString());

        //class com.sun.proxy.$Proxy12 "$Proxy**" 表示内存中动态生存的代理对象
        log.info(proxy.getClass().toString());

        proxy.study();

        assertEquals("How do fish learn to swim?", proxy.ask("How do fish learn to swim?"));
    }

    /**
     * <pre>在模块化项目（JPMS, Java Platform Module System） 中使用 JUnit 5 进行单元测试，需要特别注意模块系统的访问控制规则。
     * 由于 JPMS 默认限制了模块之间的反射和包访问，直接运行测试可能会遇到 IllegalAccessError、Module not open 等问题。
     *
     * 添加 JVM 启动参数，开放必要的模块访问权限
     *
     * build.gradle test
     * <code>
     *     test{
     *         jvmArgs('--add-opens=java.base/java.lang=ALL-UNNAMED','--add-opens=java.base/java.lang.reflect=ALL-UNNAMED')
     *     }
     * </code>
     *
     * dog 和 proxy 是两个不同的对象，但 CGLIB 默认没有重写 toString()、hashCode()、equals() 方法，
     * 所以它们的默认行为（继承自 Object）会输出相同的格式（类名 + @ + 哈希码），而这个哈希码是基于对象内存地址的。
     *
     * 但由于 CGLIB 创建的是 Dog 的子类（如 Dog$$EnhancerByCGLIB$$abc123），代理对象的类名不同，但 toString() 没有被重写，所以看起来“一样”。
     */
    @Test
    @DisplayName("cglib 动态代理")
    void cglibProxyTest() {
        Dog dog = new Dog();
        log.info("source Instance: {} class= {}", dog, dog.getClass().getSimpleName());
        ProxyCglibFactory factory = new ProxyCglibFactory(dog);
        Dog proxy = (Dog) factory.getProxyInstance();
        log.info("proxy Instance: {} class= {}", proxy, proxy.getClass().getSimpleName());

        assertNotSame(dog, proxy);
        assertNotEquals(dog, proxy);

        proxy.bark();
    }


    /**
     * <pre>无法代理增强 static 方法
     * cglib 无法设置回调函数 {@code  enhancer.setCallback(this);}
     * 因为 static 方法的 local variables 中不存在 this
     */
    @Test
    @DisplayName("cglib 动态代理 static 方法失效: static 方法的 local variables 中不存在 this")
    void cglibProxyStaticTest() {
        Dog dog = new Dog();
        log.info("source Instance: {} class= {}", dog, dog.getClass().getSimpleName());
        ProxyCglibFactory factory = new ProxyCglibFactory(dog);
        Dog proxy = (Dog) factory.getProxyInstance();
        log.info("proxy Instance: {} class= {}", proxy, proxy.getClass().getSimpleName());
        proxy.bark2();

        //static 方法 不推荐使用实例对象调用，使用类名直接调用。cglib 无法增强
        Dog.bark2();
    }

    /**
     * <pre>
     * final 无法被代理
     * final 无法被子类重写
     */
    @Test
    @DisplayName("cglib 动态代理 final 方法失效：final 方法无法被子类重写")
    void cglibProxyFinalTest() {
        Dog dog = new Dog();
        log.info("source Instance: {} class= {}", dog, dog.getClass().getSimpleName());
        ProxyCglibFactory factory = new ProxyCglibFactory(dog);
        Dog proxy = (Dog) factory.getProxyInstance();
        log.info("proxy Instance: {} class= {}", proxy, proxy.getClass().getSimpleName());
        proxy.bark3();
    }
}
