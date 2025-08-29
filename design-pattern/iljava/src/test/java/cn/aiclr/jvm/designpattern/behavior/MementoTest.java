package cn.aiclr.jvm.designpattern.behavior;

import cn.aiclr.jvm.designpattern.behavior.memento.Caretaker;
import cn.aiclr.jvm.designpattern.behavior.memento.Originator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <pre>备忘录模式  memento pattern
 * 不破坏封装性的前提下,捕获一个对象的内部状态,
 * 并在该对象之外保存这个状态,
 * 后续可以恢复到原先的保存的状态
 *
 * 实现信息封装,不需要关心状态的保存细节
 *
 * 数据库事务管理
 * 备忘录加原型模式配合节约内存
 */
@DisplayName("备忘录模式")
class MementoTest {

    private static final Logger log = LoggerFactory.getLogger(MementoTest.class);

    @Test
    void mementoTest() {
        Originator originator = new Originator();
        Caretaker caretaker = new Caretaker();

        originator.setState("first");
        caretaker.add(originator.saveStateMemento());

        originator.setState("second");
        caretaker.add(originator.saveStateMemento());

        originator.setState("third");
        caretaker.add(originator.saveStateMemento());

        log.info("当前状态: {}", originator.getState());
        Assertions.assertEquals("third", originator.getState());

        originator.getStateMemento(caretaker.get(1));
        log.info("当前状态: {}", originator.getState());
        Assertions.assertEquals("second", originator.getState());
    }
}
