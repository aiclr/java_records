package cn.aiclr.jvm.suggestions.reflex.annotation;

import cn.aiclr.jvm.suggestions.reflex.Business;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class BusinessTest {

    private static final Logger logger = LoggerFactory.getLogger(BusinessTest.class);

    @Test
    @DisplayName("反射插上注解的翅膀")
    void test() {
        Business business = new Business();
        Class<?> businessClass = Business.class;
        //public 方法
        final Method[] methods = businessClass.getMethods();

        List<Method> beforeList = new ArrayList<>();
        List<Method> coreList = new ArrayList<>();
        List<Method> afterList = new ArrayList<>();

        for (Method method : methods) {
            if (method.isAnnotationPresent(MyBefore.class)) {
                beforeList.add(method);
            }
            if (method.isAnnotationPresent(MyCore.class)) {
                coreList.add(method);
            }
            if (method.isAnnotationPresent(MyAfter.class)) {
                afterList.add(method);
            }
        }
        try {
            for (Method coreMethod : coreList) {
                if (Objects.nonNull(coreMethod)) {
                    for (Method beforeMethod : beforeList) {
                        beforeMethod.invoke(business);
                    }

                    coreMethod.invoke(business);

                    for (Method afterMethod : afterList) {
                        afterMethod.invoke(business);
                    }
                }
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            logger.error("{}", e.getMessage(), e);
        }

    }

}