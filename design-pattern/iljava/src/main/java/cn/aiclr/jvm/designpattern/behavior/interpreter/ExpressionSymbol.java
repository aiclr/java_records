package cn.aiclr.jvm.designpattern.behavior.interpreter;

import java.util.HashMap;

/**
 * <pre>符号解释器,每个运算符的符号,都只和自己左右两个数字有关系
 * 但左右两个数字有可能也是一个解析结果,无论何种类型,都是 Expression 的实现类
 *
 * 非终结符表达式,实现与文法中的非终结符相关的解释操作
 */
public class ExpressionSymbol extends Expression {

    protected Expression left;
    protected Expression right;

    public ExpressionSymbol(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    /**
     * <pre>此类是让子类来实现,因此此处的 interpreter 为默认实现
     *
     * @param var {a=10,b=20}
     * @return result
     */
    @Override
    public int interpreter(HashMap<String, Integer> var) {
        return 0;
    }
}
