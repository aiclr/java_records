package cn.aiclr.jvm.designpattern.behavior.visitor;

/**
 * 具体访问者
 */
public class ElementMan extends Element {
    @Override
    void accept(Visitor visitor) {
        //第二次分派
        visitor.manVisitor(this);
    }
}
