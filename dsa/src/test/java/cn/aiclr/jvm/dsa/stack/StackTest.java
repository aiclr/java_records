package cn.aiclr.jvm.dsa.stack;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("模拟栈")
class StackTest {

    @Test
    @DisplayName("栈的操作")
    void stackTest() {
        SimpleStack stack = new SimpleStack(10);
        stack.show();
        stack.push(0);
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        stack.push(5);
        stack.push(6);
        stack.push(7);
        stack.push(8);
        stack.push(9);
        //栈满
        Assertions.assertThrows(RuntimeException.class, () -> stack.push(10));
        stack.show();

        Assertions.assertEquals(9, stack.peek());
        Assertions.assertEquals(9, stack.pop());
        Assertions.assertEquals(8, stack.peek());
        Assertions.assertEquals(8, stack.pop());
        Assertions.assertEquals(7, stack.pop());
        Assertions.assertEquals(6, stack.pop());
        Assertions.assertEquals(5, stack.pop());
        Assertions.assertEquals(4, stack.pop());
        Assertions.assertEquals(3, stack.pop());
        Assertions.assertEquals(2, stack.pop());
        Assertions.assertEquals(1, stack.pop());
        Assertions.assertEquals(0, stack.pop());
        Assertions.assertThrows(IndexOutOfBoundsException.class, stack::pop);
    }

    @DisplayName("前缀表达式/波兰表示法")
    @Test
    void polishNotation() {
        //中缀表达式：(3+4)*5-6
        String infixNotation = "(3+4)*5-6";
        //后缀表达式：-*+3456
        char[] prefixNotation = new char[]{'-', '*', '+', '3', '4', '5', '6'};
        //不算括号
        SimpleStack stack = new SimpleStack(prefixNotation.length);

        for (int i = prefixNotation.length - 1; i >= 0; i--) {
            char item = prefixNotation[i];
            if (SimpleStack.isOper(item)) {
                //后缀表达式 先出栈的是左操作数
                int first = stack.pop();
                int second = stack.pop();
                int result = SimpleStack.cal(first, second, item);
                //将数字字符转为对应的 数值
                stack.push(result);
            } else {
                //将数字字符转为对应的 数值
                // item - '0' 字符 ASCI码表 0对应48,1对应49,2对应50。。。 所以 '1'-'0' = 1;'2'-'0'=2;
                stack.push(item - '0');
                //使用 Character.getNumericValue()
//                stack.push(Character.getNumericValue(item));
            }
        }
        Assertions.assertEquals(29, stack.pop());
    }

    @DisplayName("后缀表达式/逆波兰表示法")
    @Test
    void postfixNotation() {
        //中缀表达式：(3+4)*5-6
        String infixNotation = "(3+4)*5-6";
        //后缀表达式：-*+3456
        char[] postfixNotation = new char[]{'3', '4', '+', '5', '*', '6', '-'};
        //不算括号
        SimpleStack stack = new SimpleStack(postfixNotation.length);

        for (int i = 0; i < postfixNotation.length; i++) {
            char item = postfixNotation[i];
            if (SimpleStack.isOper(item)) {
                //后缀表达式 先出栈的是右操作数
                int second = stack.pop();
                int first = stack.pop();
                int result = SimpleStack.cal(first, second, item);
                //将数字字符转为对应的 数值
                stack.push(result);
            } else {
                //将数字字符转为对应的 数值
                // item - '0'
//                stack.push(item - '0');
                //使用 Character.getNumericValue()
                stack.push(Character.getNumericValue(item));
            }
        }
        Assertions.assertEquals(29, stack.pop());
    }
}
