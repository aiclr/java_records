package cn.aiclr.jvm.suggestions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Java乱码  一般都是字符集不一致造成的
 * 中文字符集范围 GB18030（2000年） > GBK（1995年） > GB2312（1980年）版本向上兼容，只是包含汉字数量不同
 */
class GarbledCodeTest {

    private static final Logger logger = LoggerFactory.getLogger(GarbledCodeTest.class);

    @Test
    @DisplayName("国标码")
    void testGBCharset() throws UnsupportedEncodingException {
        String str = "中文是否乱码";
        byte[] b = str.getBytes("GB2312");

        String s = new String(b, "GBK");
        logger.info("GB2312解码>_GBK编码: 不会乱码>_{}", s);
        Assertions.assertEquals(str, s);

        String s1 = new String(b, "GB18030");
        logger.info("GB2312解码>_GB18030编码: 不会乱码>_{}", s1);
        Assertions.assertEquals(str, s1);

        String s2 = new String(b, "GB2312");
        logger.info("GB2312解码>_GB2312编码: 不会乱码>_{}", s2);
        Assertions.assertEquals(str, s2);
    }

    @Test
    @DisplayName("默认UTF-8")
    void testDefaultCharset() {
        String str = "中文是否乱码";
        //默认 UTF-8
        byte[] b = str.getBytes();
        //默认 UTF-8
        String s = new String(b, StandardCharsets.UTF_8);
        logger.info("默认编解码：不会乱码>_{}", s);
        Assertions.assertEquals(str, s);
        logger.info("默认字符集={}", Charset.defaultCharset().displayName());
    }

    @Test
    @DisplayName("编码不一致乱码")
    void testGarbledCode() throws UnsupportedEncodingException {
        String str = "中文是否乱码";
        //编辑器默认使用UTF-8,此处使用GBK
        byte[] b = str.getBytes("GBK");
        //此处仍使用UTF-8编码
        String s = new String(b);
        logger.info("GBK解码>_默认字符集编码：乱码>_{}", s);
    }


    @Test
    @DisplayName("编码一致不乱码")
    void testSameCharset() throws UnsupportedEncodingException {
        String str = "中文是否乱码";
        //保持编码一致
        byte[] b = str.getBytes("GBK");
        String s = new String(b, "GBK");
        logger.info("GBK编码>_GBK解码：不会乱码>_{}", s);
        Assertions.assertEquals(str, s);
    }

}