package cn.aiclr.jvm.designpattern.creation;

import cn.aiclr.jvm.designpattern.creation.prototype.deep.Dog;
import cn.aiclr.jvm.designpattern.creation.prototype.deep.SheepPro;
import cn.aiclr.jvm.designpattern.creation.prototype.shallow.Sheep;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;

@DisplayName("原型模式")
class SheepTest {

    private static final Logger log = LoggerFactory.getLogger(SheepTest.class);

    @Test
    @DisplayName("浅拷贝")
    void shallowCopyTest() {
        Sheep root = new Sheep("root", "yellow", 1);
        Sheep leaf = new Sheep("leaf", "black", 2);
        root.setSheep(leaf);
        log.info("{}", root);
        try {
            Sheep rootClone = (Sheep) root.clone();
            log.info("{}", rootClone);
            assertSame(root.getSheep(), rootClone.getSheep(), "浅拷贝不会拷贝引用类型");

            /**
             * <pre>因为 StringTable（heap area）
             * 所以  assertSame “==” 测试通过
             */
            rootClone.getSheep().setColor("write");
            assertSame("write", root.getSheep().getColor(), "修改rootClone的sheep会影响原类root");
            assertEquals("write", root.getSheep().getColor(), "修改rootClone的sheep会影响原类root");

        } catch (CloneNotSupportedException e) {
            log.error("{}", e.getMessage(), e);
        }
    }

    @Test
    @DisplayName("深拷贝--引用类型实现 Cloneable.clone()方法")
    void deepCopyTest() {
        SheepPro root = new SheepPro("root", "yellow", 1);
        Dog dog = new Dog("修狗");
        root.setDog(dog);
        log.info("{}", root);
        try {
            SheepPro rootClone = (SheepPro) root.clone();
            log.info("{}", rootClone);
            assertNotSame(root.getDog(), rootClone.getDog(), "深拷贝会拷贝引用类型，");

            rootClone.getDog().setName("旺财");
            assertSame("修狗", root.getDog().getName(), "修改rootClone的dog不会影响原类");
            assertEquals("修狗", root.getDog().getName(), "修改rootClone的dog不会影响原类");
        } catch (CloneNotSupportedException e) {
            log.error("{}", e.getMessage(), e);
        }
    }

    @Test
    @DisplayName("深拷贝--对象序列化实现深拷贝")
    void deepCopyBySerializeTest() {
        SheepPro root = new SheepPro("root", "yellow", 1);
        Dog dog = new Dog("修狗");
        root.setDog(dog);
        log.info("{}", root);
        SheepPro rootClone = (SheepPro) root.deepClone();
        log.info("{}", rootClone);
        assertNotSame(root.getDog(), rootClone.getDog(), "深拷贝会拷贝引用类型，");

        rootClone.getDog().setName("旺财");
        assertSame("修狗", root.getDog().getName(), "修改rootClone的dog不会影响原类");
        assertEquals("修狗", root.getDog().getName(), "修改rootClone的dog不会影响原类");
    }


}