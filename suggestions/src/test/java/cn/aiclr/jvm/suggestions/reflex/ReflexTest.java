package cn.aiclr.jvm.suggestions.reflex;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class ReflexTest {

    private static final Logger logger = LoggerFactory.getLogger(ReflexTest.class);

    @Test
    @DisplayName("反射调用 private 修饰的方法")
    void testInvokePrivateMethod() {
        Duck duck = new Duck(100);
        logger.info("{}", duck);
        //获取 大 Class 对象
        final Class<?> duckClass = Duck.class;
        try {
            Method setLevel = duckClass.getDeclaredMethod("setLevel", int.class);
            logger.info("setLevel.canAccess(duck) = {}", setLevel.canAccess(duck));
            setLevel.setAccessible(true);
            logger.info("设置私有方法可访问后：setLevel.canAccess(duck) = {}", setLevel.canAccess(duck));
            setLevel.invoke(duck, 101);
            logger.info("{}", duck);

            Method getLevel = duckClass.getDeclaredMethod("getLevel");
            logger.info("getLevel.canAccess(duck) = {}", getLevel.canAccess(duck));
            getLevel.setAccessible(true);
            logger.info("设置私有方法可访问后：getLevel.canAccess(duck) = {}", getLevel.canAccess(duck));
            Assertions.assertEquals(101, getLevel.invoke(duck));
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            logger.error("{}", e.getMessage(), e);
        }
    }

    @Test
    @DisplayName("反射设置父类 private field")
    void testSetSuperPrivateField() {
        Duck duck = new Duck(100);
        logger.info("{}", duck);
        //获取 大 Class 对象
        final Class<?> duckClass = Duck.class;
        final Class<?> duckSuperClass = duckClass.getSuperclass();
        try {
            Field nameField = duckSuperClass.getDeclaredField("name");
            logger.info("nameField.canAccess(duck) = {}", nameField.canAccess(duck));
            nameField.setAccessible(true);
            logger.info("设置私有属性可访问后：nameField.canAccess(duck) = {}", nameField.canAccess(duck));
            nameField.set(duck, "可达鸭");
            logger.info("{}", duck);
            Assertions.assertEquals("可达鸭", nameField.get(duck));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            logger.error("{}", e.getMessage(), e);
        }
    }

    @Test
    @DisplayName("通过 Class 对象 创建实例")
    void testClassNewInstance() {

        try {
            //获取 大 Class 对象
            final Class<?> duckClass = Duck.class;
            Constructor<?> duckClassConstructor = duckClass.getConstructor(int.class);
            Duck duck = (Duck) duckClassConstructor.newInstance(100);
            logger.info("{}", duck);

            //获取 大 Class 对象
            final Class<?> duckSuperClass = duckClass.getSuperclass();
            Constructor<?> duckSuperClassConstructor = duckSuperClass.getConstructor();
            Animal animal = (Animal) duckSuperClassConstructor.newInstance();
            Field nameField = duckSuperClass.getDeclaredField("name");
            nameField.setAccessible(true);
            nameField.set(animal, "可达鸭");
            logger.info("{}", animal);
            Assertions.assertEquals("可达鸭", nameField.get(animal));
        } catch (InstantiationException | NoSuchMethodException | InvocationTargetException |
                 IllegalAccessException | NoSuchFieldException e) {
            logger.error("{}", e.getMessage(), e);
        }
    }
}