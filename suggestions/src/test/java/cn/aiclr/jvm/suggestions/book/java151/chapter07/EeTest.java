package cn.aiclr.jvm.suggestions.book.java151.chapter07;

import cn.aiclr.jvm.suggestions.book.java151.chapter07.ee.Dept;
import cn.aiclr.jvm.suggestions.book.java151.chapter07.ee.DeptDao;
import cn.aiclr.jvm.suggestions.book.java151.chapter07.ee.User;
import cn.aiclr.jvm.suggestions.book.java151.chapter07.ee.UserDao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

class EeTest {

    @Test
    @DisplayName("模拟ORM框架持久层")
    void test() {
        try {
            UserDao userDao = new UserDao();
            User user = userDao.get(1L);

            DeptDao deptDao = new DeptDao();
            Dept dept = deptDao.get(1L);
        } catch (NoSuchMethodException | InstantiationException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }
}
