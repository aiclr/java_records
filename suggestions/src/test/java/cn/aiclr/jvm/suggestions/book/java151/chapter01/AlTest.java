package cn.aiclr.jvm.suggestions.book.java151.chapter01;

import cn.aiclr.jvm.suggestions.utils.SerializationUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Execution(ExecutionMode.SAME_THREAD)
@Disabled
class AlTest {

    private static final Logger logger = LoggerFactory.getLogger(AlTest.class);

    @Test
    @DisplayName("直接量 v1 序列化")
    void testV1FinalField() {
        SerializationUtils.writeObject(new FinalField());
    }

    @Test
    @DisplayName("直接量 v2 反序列化 新字面量")
    void testV2FinalField() {
        FinalField obj = (FinalField) SerializationUtils.readObject();
        logger.info("{}", obj.getName());
        Assertions.assertEquals("凯奇", obj.getName());
    }

    @Test
    @DisplayName("new String v1 序列化")
    void testV1FinalNewString() {
        SerializationUtils.writeObject(new FinalNewString());
    }

    @Test
    @DisplayName("new String v2 反序列化 不会重新赋值")
    void testV2FinalNewString() {
        FinalNewString obj = (FinalNewString) SerializationUtils.readObject();
        logger.info("{}", obj.getName());
        Assertions.assertEquals("尼古拉斯", obj.getName());
    }


    @Test
    @DisplayName("构造函数赋值 v1 序列化 ")
    void testV1Constructor() {
        SerializationUtils.writeObject(new FinalField());
    }

    @Test
    @DisplayName("构造函数赋值 v2 反序列化 构造函数不执行,不会重新赋值")
    void testV2Constructor() {
        FinalField obj = (FinalField) SerializationUtils.readObject();
        logger.info("{}", obj.getName());
        Assertions.assertEquals("尼古拉斯", obj.getName());
    }

    @Test
    @DisplayName("方法赋值 v1 序列化")
    void testV1Method() {
        SerializationUtils.writeObject(new Am());
    }

    @Test
    @DisplayName("方法赋值 v2 反序列化 方法不执行,不会重新赋值")
    void testV2Method() {
        Am obj = (Am) SerializationUtils.readObject();
        logger.info("{}", obj.getName());
        Assertions.assertEquals("尼古拉斯", obj.getName());
    }

    @Test
    @DisplayName("writeObject 序列化基本工资和工龄")
    void testWriteObject() {
        //薪酬
        AnSalary anSalary = new AnSalary(1000, 1, 2000);
        //人员信息
        AnPerson anPerson = new AnPerson("社畜", anSalary);
        //序列化，持久化，供分布式其他类使用
        SerializationUtils.writeObject(anPerson);
    }

    @Test
    @DisplayName("readObject 反序列化得到基本工资和工龄")
    void testReadObject() {
        AnPerson anPerson = (AnPerson) SerializationUtils.readObject();
        StringBuffer sb = new StringBuffer();
        sb.append("姓名:").append(anPerson.getName());
        sb.append("\t基本工资：").append(anPerson.getAnSalary().getBasePay());
        sb.append("\t工龄：").append(anPerson.getAnSalary().getSeniority());
        sb.append("\t绩效：").append(anPerson.getAnSalary().getBonus());
        logger.info("{}", sb);
    }
}