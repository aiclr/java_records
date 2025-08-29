package cn.aiclr.jvm.designpattern.behavior.interpreter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

/**
 * <pre>解释器模式 Interpreter Pattern
 * 给定一个表达式,定义它的文法的一种表示,并定义一个解释器,使用该解释器来解释语言中的表达式
 *
 * 在编译原理中,一个算术表达式通过词法分析器形成词法单元,
 * 而后这些词法单元,再通过语法分析器构建语法分析树,最终形成一颗抽象的语法分析树
 * 这里的词法分析器和语法分析器都可以看作是解释器
 *
 * 场景:
 *  1.将一个需要解释执行的语言中的句子表示为一个抽象语法树
 *  2.一些重复出现的问题可以用一种简单的语言来表达
 *  3.一个简单语法需要解释的场景
 * 例子: 编译器、运算表达式计算、正则表达式、机器人等
 *
 * 抽象表达式：声明 一个抽象的解释操作,
 * 这个方法为抽象语法树中所有的节点所共享
 * 通过 hashMap 键值对,可以获取到变量的值
 */
public abstract class Expression {

    protected static final Logger log = LoggerFactory.getLogger(Expression.class);

    /**
     * <pre>a+b-c
     * 解释公式和数值,key 是公式,参数 a,b,c
     *
     * @param var {a=10,b=20}
     * @return result
     */
    public abstract int interpreter(HashMap<String, Integer> var);
}
