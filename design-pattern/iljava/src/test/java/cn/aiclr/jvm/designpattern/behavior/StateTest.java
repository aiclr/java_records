package cn.aiclr.jvm.designpattern.behavior;

import cn.aiclr.jvm.designpattern.behavior.state.Activity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.IntStream;

/**
 * @see <a href="https://junit.org/junit5/docs/current/user-guide/#writing-tests-test-instance-lifecycle">@TestInstance</a>
 * @see <a href="https://junit.org/junit5/docs/current/user-guide/#writing-tests-parameterized-tests-argument-aggregation">@MethodSource</a>
 */
@DisplayName("状态模式")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class StateTest {
    private static final Logger log = LoggerFactory.getLogger(StateTest.class);

    Activity activity;

    @BeforeAll
    void before() {
        activity = new Activity(2);
    }

    @ParameterizedTest
    @MethodSource("range")
    void stateTest(int argument) {
        log.info("奖池{}", activity.getCount());
        log.info("第{}次抽奖", argument);
        activity.deductMoney();
        activity.raffle();
    }

    static IntStream range() {
        //从0到21,从1开始
        return IntStream.range(0, 21).skip(1);
    }
}
