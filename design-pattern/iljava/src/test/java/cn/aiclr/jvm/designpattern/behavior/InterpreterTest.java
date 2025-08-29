package cn.aiclr.jvm.designpattern.behavior;

import cn.aiclr.jvm.designpattern.behavior.interpreter.Calculator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.stream.Stream;

@DisplayName("解释器模式")
class InterpreterTest {

    /**
     * 创建一个提供测试参数的静态方法
     *
     * @return 返回类型必须是 Stream<Arguments>
     */
    private static Stream<Arguments> provideTestData() {

        HashMap<String, Integer> var = new HashMap<>();
        var.put("a", 10);
        var.put("b", 20);
        var.put("c", 30);

        return Stream.of(
                Arguments.of("a+b+c", var, 60),
                Arguments.of("a+b+c+a+b+c", var, 120),
                Arguments.of("a+b-c", var, 0),
                Arguments.of("a+b-c+a+b-c", var, 0)
        );
    }

    @ParameterizedTest
    @MethodSource("provideTestData")
    void test(String expStr, HashMap<String, Integer> var, Integer expected) {
        Calculator calculator = new Calculator(expStr);
        Assertions.assertEquals(expected, calculator.run(var));
    }

}
