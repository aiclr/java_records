package cn.aiclr.jvm.suggestions.book.java151.chapter04;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;

class CfTest {

    private static final Logger logger = LoggerFactory.getLogger(CfTest.class);

    @Test
    @DisplayName("制造乱码")
    void testMessyCode() throws UnsupportedEncodingException {
        String str = "中文是否乱码";
        //编辑器默认是 UTF-8 ,此处使用 GBK 获取
        byte[] b = str.getBytes("GBK");
        //此处会用回默认 UTF-8
        logger.info("乱码：{}", new String(b));
    }

    @Test
    @DisplayName("解决乱码")
    void testNoMessyCode() throws UnsupportedEncodingException {
        String str = "中文是否乱码";
        //编辑器默认是 UTF-8 ,此处使用 GBK 获取
        byte[] b = str.getBytes("GBK");
        //此处会用回默认 UTF-8
        logger.info("解决乱码：{}", new String(b, "GBK"));
    }

    /**
     * 中文字符集范围 GB18030（2000年） > GBK（1995年） > GB2312（1980年）版本向上兼容，只是包含汉字数量不同
     */
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


}
