package cn.aiclr.jvm.dsa.stack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <pre>数组模拟栈
 *
 * 关于栈的运算，先将算术运算转换为对应表达式
 *
 * 前缀表达式（Prefix Notation / Polish Notation 由波兰逻辑学家扬·武卡谢维奇（Jan Łukasiewicz）提出，故又称波兰表示法）：操作符位于其操作数之前 不带括号
 *
 *  （3 + 4）* 5 - 6 对应的前缀表达式是 -*+3456 （顺序不能变）
 *  表达式转换过程：
 *  （3+4）= +34
 *  【(3+4)】*5 = 【+34】*5 = *【+34】5 = *+345
 *  【(3+4)*5】-6 = 【*+345】-6 = -【*+345】6 = -*+3456
 *  运算过程 从右到左扫描前缀表达式 遇到数字压入栈
 *  遇到运算符弹出栈顶两个数，将运算结果再压入堆栈
 *
 *  从右到左扫描前缀表达式
 *  将 6543 压入栈，遇到 + 号，弹出 3,4 运算 3+4=7,7 再入栈
 *  遇到 * 弹出 7,5 运算 7*5=35,35入栈
 *  遇到 - 弹出 35,6 运算 35-6=29,29入栈
 *
 * 中缀表达式 (Infix Notation) =正常运算表达式 带括号
 * （3 + 4）* 5 - 6 对应的中缀表达式是（3+4）*5-6
 *  计算机一般将中缀表达式转换为后缀表达式运算
 *
 * 后缀表达式（Postfix Notation / Reverse Polish Notation, RPN 逆波兰表示法） 不带括号
 *
 * （3+4）* 5 - 6 对应的后缀表达式是 34+5*6-
 * 表达式转换过程：
 * (3+4) = 34+
 * 【(3+4)】*5 =【34+】*5 =【34+】5* = 34+5*
 * 【(3+4)*5】-6 = 【34+5*】- 6 = 【34+5*】6- = 34+5*6-
 *
 * 后缀表达时转换示例：
 *  a+b         ab+         ab   ab+
 *  a+(b-c)     abc-+       bc-  a(bc-)+
 *  a+(b-c)*d   abc-d*+     bc-  (bc-)d*  a((bc-)d*)+
 *  a+d*(b-c)   adbc-*+     bc-  d(bc-)*  a(d(bc-)*)+
 *  a=1+3       a13+=       13+  a(13+)=
 *  运算过程 从左到右扫描后缀表达式 遇到数字压入堆栈
 *  遇到运算符弹出栈顶两个数，将运算结果再压入堆栈
 *  从左到右扫描后缀表达式
 *  将 34 压入栈，遇到 + 弹出 右操作数4、左操作数3 运算 3+4=7, 7入栈、5入栈
 *  遇到 * 弹出 右操作数5、左操作数7 运算 7*5=35,35入栈、6入栈
 *  遇到 - 弹出 右操作数6、左操作数35 运算 35-6=29 29入栈
 *
 *  特性	        中缀 (Infix)	            前缀 (Prefix)	    后缀 (Postfix)
 * 操作符位置	操作数之间 (A + B)	    操作数之前 (+ A B)	操作数之后 (A B +)
 * 是否需要括号	是（用于改变优先级）	    否	                 否
 * 人类可读性	高	                    低	                 中等（习惯后）
 * 计算机处理难度	复杂（需处理优先级和括号）	中等（从右向左用栈）	简单（从左向右用栈）
 * 典型应用	    日常书写、编程语言	        一些Lisp方言	        编译器内部表示、HP计算器
 *
 * 前缀和后缀表达式是计算机处理数学表达式的理想形式。
 * 虽然对人类来说不如中缀表达式直观，但它们的无歧义性和计算高效性使其在计算机科学中占据核心地位。
 * 理解它们的转换和求值方法是学习数据结构（尤其是栈）和编译原理的重要基础
 */
public class SimpleStack {

    private static final Logger log = LoggerFactory.getLogger(SimpleStack.class);

    public int maxSize;
    public int[] stack;
    public int top = -1;

    public SimpleStack(int maxSize) {
        this.maxSize = maxSize;
        stack = new int[maxSize];
    }

    /**
     * 栈是否已满 栈顶指针指向 数组最后一个位置
     */
    public boolean isFull() {
        return top == maxSize - 1;
    }

    /**
     * 栈是否已空 栈顶指针指向 -1
     */
    public boolean isEmpty() {
        return top == -1;
    }

    /**
     * 从栈顶入栈
     *
     * @param value 待入栈元素
     */
    public void push(int value) {
        if (isFull()) {
            throw new RuntimeException("栈已满，无法入栈");
        }
        top++;
        stack[top] = value;
    }

    /**
     * 栈顶元素出栈
     *
     * @return 栈顶元素
     */
    public int pop() {
        if (isEmpty()) {
            throw new IndexOutOfBoundsException("栈内无元素");
        }
        int result = stack[top];
        top--;
        return result;
    }

    /**
     * 从栈顶开始打印栈内元素
     */
    public void show() {
        if (isEmpty()) {
            return;
        }
        for (int i = top; i >= 0; i--) {
            log.info("{}", stack[i]);
        }
    }

    /**
     * 运算符优先级
     *
     * @param oper 运算符
     * @return 注意： java 中 int 和 char 可以混用
     */
    public int priority(int oper) {
        if (oper == '*' || oper == '/') {
            return 1;
        } else if (oper == '+' || oper == '-') {
            return 0;
        } else throw new IllegalArgumentException("不支持的运算");
    }

    /**
     * 判断是否为运算符
     */
    public static boolean isOper(char val) {
        return val == '+' || val == '-' || val == '*' || val == '/';
    }

    /**
     * 运算 注意顺序
     */
    public static int cal(int first, int second, int oper) {
        log.info("{} {} {}", first, (char) oper, second);
        return switch (oper) {
            case '+' -> first + second;
            case '*' -> first * second;
            case '-' -> first - second;
            case '/' -> first / second;
            default -> throw new IllegalArgumentException("不支持的运算");
        };
    }

    /**
     * 返回栈顶元素，不出栈
     *
     * @return 栈顶元素
     */
    public int peek() {
        return stack[top];
    }

}
