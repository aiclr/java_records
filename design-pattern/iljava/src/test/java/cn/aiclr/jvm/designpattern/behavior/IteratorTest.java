package cn.aiclr.jvm.designpattern.behavior;

import cn.aiclr.jvm.designpattern.behavior.iterator.College;
import cn.aiclr.jvm.designpattern.behavior.iterator.CollegeComputer;
import cn.aiclr.jvm.designpattern.behavior.iterator.CollegeInfo;
import cn.aiclr.jvm.designpattern.behavior.iterator.OutputImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>迭代器接口,jdk 已提供
 *  1. 提供统一的方法遍历对象,不用考虑聚合(集合存放,数组存放)的类型,
 *  2. 隐藏聚合的内部结构,客户端要遍历聚合的时候取迭代器,不用知道具体是聚合方式
 *  3. 单一职责原则,管理对象聚合,遍历对象聚合的责任分开,聚合改变,只影响聚合对象;遍历方式改变,只影响迭代器
 *  4. 用于相似对象的展示,遍历一组相同对象时使用,适合使用迭代器模式
 *
 * 缺陷: 每个聚合对象都要一个迭代器,会生成多个迭代器不好管理类
 */
@DisplayName("迭代器模式")
class IteratorTest {

    @Test
    void iteratorTest() {
        List<College> colleges = new ArrayList<>();
        CollegeComputer computer = new CollegeComputer();
        CollegeInfo info = new CollegeInfo();

        colleges.add(computer);
        colleges.add(info);

        OutputImpl outPut = new OutputImpl(colleges);
        outPut.printCollege();
    }
}
