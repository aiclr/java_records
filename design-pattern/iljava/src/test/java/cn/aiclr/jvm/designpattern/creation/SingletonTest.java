package cn.aiclr.jvm.designpattern.creation;

import cn.aiclr.jvm.designpattern.creation.singleton.SingletonEnum;
import cn.aiclr.jvm.designpattern.creation.singleton.StaticInnerClass;
import cn.aiclr.jvm.designpattern.creation.singleton.hungry.StaticBlock;
import cn.aiclr.jvm.designpattern.creation.singleton.hungry.StaticVariable;
import cn.aiclr.jvm.designpattern.creation.singleton.lazy.SyncMethod;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @see <a href="https://junit.org/junit5/docs/current/user-guide/#writing-tests-parallel-execution-synchronization">@Execution</a>
 * @see <a href="https://junit.org/junit5/docs/current/user-guide/#writing-tests-repeated-tests">@RepeatedTest</a>
 */
@DisplayName("单例模式")
class SingletonTest {

    Supplier<String> msgSup = () -> "单例模式";

    private static final Logger log = LoggerFactory.getLogger(SingletonTest.class);

    @DisplayName("同步代码块+双重检查实现懒汉式单例模式-效率高")
    @Execution(ExecutionMode.CONCURRENT)//多线程
    @RepeatedTest(5)
    void testSyncBlockDoubleCheck() {
        log.info("{}🚦{}", Thread.currentThread().getName(), SyncMethod.getInstanceDoubleCheck().hashCode());
        assertSame(SyncMethod.getInstanceDoubleCheck(), SyncMethod.getInstanceDoubleCheck(), msgSup);
    }

    @DisplayName("懒汉式单例模式错误同步代码块示范-极难复现")
    @Execution(ExecutionMode.CONCURRENT)//多线程
    @RepeatedTest(5)
    void testSyncBlockNotSafe() {
        log.info("{}🚦{}", Thread.currentThread().getName(), SyncMethod.syncBlockNotSafe().hashCode());
        assertSame(SyncMethod.syncBlockNotSafe(), SyncMethod.syncBlockNotSafe(), msgSup);
    }

    @DisplayName("同步方法实现懒汉式单例模式-效率低")
    @Execution(ExecutionMode.CONCURRENT)//多线程
    @RepeatedTest(5)
    void testSyncMethod() {
        log.info("{}🚦{}", Thread.currentThread().getName(), SyncMethod.getInstance().hashCode());
        assertSame(SyncMethod.getInstance(), SyncMethod.getInstance(), msgSup);
    }

    @DisplayName("枚举防止反射攻击")
    @Test
    void testEnum() {
        SingletonEnum instance = SingletonEnum.INSTANCE;
        SingletonEnum instance1 = SingletonEnum.INSTANCE;
        assertSame(instance, instance1, msgSup);
        assertThrows(NoSuchMethodException.class, SingletonEnum.class::getDeclaredConstructor);// 抛出异常！
    }

    @DisplayName("利用静态内部类实现单例模式")
    @Test
    void testStaticInnerClass() {
        StaticInnerClass instance = StaticInnerClass.getInstance();
        StaticInnerClass instance1 = StaticInnerClass.getInstance();
        Assertions.assertSame(instance, instance1, msgSup);
        //模拟反射攻击
        try {
            Constructor<StaticInnerClass> constructor = StaticInnerClass.class.getDeclaredConstructor();
            constructor.setAccessible(true);//跳过安全检查
            StaticInnerClass instance2 = constructor.newInstance();
            assertNotSame(instance, instance2);
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
                 IllegalAccessException e) {
            log.error("{}", e.getMessage(), e);
        }
    }

    @DisplayName("利用静态代码块实现恶汉式单例模式")
    @Test
    void testStaticBlock() {
        StaticBlock instance = StaticBlock.getInstance();
        StaticBlock instance1 = StaticBlock.getInstance();
        assertSame(instance, instance1, msgSup);
        //模拟反射攻击
        try {
            Constructor<StaticBlock> constructor = StaticBlock.class.getDeclaredConstructor();
            constructor.setAccessible(true);//跳过安全检查
            StaticBlock instance2 = constructor.newInstance();
            assertNotSame(instance, instance2);
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
                 IllegalAccessException e) {
            log.error("{}", e.getMessage(), e);
        }
    }

    @DisplayName("利用类变量/静态变量实现恶汉式单例模式")
    @Test
    void testStaticVariable() {
        StaticVariable instance = StaticVariable.getInstance();
        StaticVariable instance1 = StaticVariable.getInstance();
        assertSame(instance, instance1, msgSup);
        //模拟反射攻击
        try {
            Constructor<StaticVariable> constructor = StaticVariable.class.getDeclaredConstructor();
            constructor.setAccessible(true);//跳过安全检查
            StaticVariable instance2 = constructor.newInstance();
            assertNotSame(instance, instance2);
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
                 IllegalAccessException e) {
            log.error("{}", e.getMessage(), e);
        }
    }

}
