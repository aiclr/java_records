package cn.aiclr.jvm.suggestions.book.java151.chapter01;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class ApTest {

    private static final Logger logger = LoggerFactory.getLogger(ApTest.class);

    @ParameterizedTest
    @CsvSource({"1,2,3", "2,3,5", "3,4,7"})
    void invokeJavaScript(int var1, int var2, int var3) {
        Ap ap = new Ap();
        Integer result = ap.invokeJavaScript(var1, var2);
        logger.info("{}+{}*{}={}", var1, var2, 1, result);
        Assertions.assertEquals(var3, result);
    }
}