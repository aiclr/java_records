package cn.aiclr.jvm.juc;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.LongStream;

@Execution(ExecutionMode.CONCURRENT)
class ForkJoinSmnCalculateTest {

    private static final Logger logger = LoggerFactory.getLogger(ForkJoinSmnCalculateTest.class);

    @Test
    @DisplayName("ForkJoin")
    void testForkJoin() {
        try (ForkJoinPool pool = new ForkJoinPool()) {
            ForkJoinTask<Long> task = new ForkJoinSmnCalculate(0L, 50000000000L);
            Long sum = pool.invoke(task);
            logger.info("{}", sum);
        }
    }

    @Test
    @DisplayName("for循环")
    void testForeach() {
        long sum2 = 0L;
        for (long i = 0; i <= 50000000000L; i++) {
            sum2 += i;
        }
        logger.info("{}", sum2);
    }

    @Test
    @DisplayName("java8 新特性")
    void testStream() {
        Long sum = LongStream.rangeClosed(0L, 50000000000L)
                .parallel()
                .reduce(0L, Long::sum);
        logger.info("{}", sum);
    }

}