package cn.aiclr.jvm.base;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class SimpleTest {

    private static final Logger logger = LoggerFactory.getLogger(SimpleTest.class);

    /**
     * <a href="https://docs.oracle.com/javase/specs/jvms/se6/html/Instructions.doc.html">
     * CHAPTER 6
     *
     * The Java Virtual Machine Instruction Set</a>
     */
    @Test
    @DisplayName("测试 ++")
    void test() {
        /**
         * ++i jvm字节码
         *    L0
         *     LINENUMBER 20 L0
         *     ICONST_1
         *     INVOKESTATIC java/lang/Integer.valueOf (I)Ljava/lang/Integer;
         *     ASTORE 1
         */
        Integer i = 1;
        /**
         *    L1
         *     LINENUMBER 21 L1
         *     ICONST_2
         *     ALOAD 1
         *     INVOKEVIRTUAL java/lang/Integer.intValue ()I
         *     ICONST_1
         *     IADD
         *     INVOKESTATIC java/lang/Integer.valueOf (I)Ljava/lang/Integer;
         *     DUP
         *     ASTORE 1
         *     INVOKESTATIC org/junit/jupiter/api/Assertions.assertEquals (ILjava/lang/Integer;)V
         */
        Assertions.assertEquals(2, ++i);
        /**
         *    L2
         *     LINENUMBER 22 L2
         *     ICONST_2
         *     ALOAD 1
         *     INVOKESTATIC org/junit/jupiter/api/Assertions.assertEquals (ILjava/lang/Integer;)V
         */
        Assertions.assertEquals(2, i);
        /**
         *    L3
         *     LINENUMBER 23 L3
         *     GETSTATIC cn/aiclr/jvm/base/SimpleTest.logger : Lorg/slf4j/Logger;
         *     LDC "i={}"
         *     ALOAD 1
         *     INVOKEINTERFACE org/slf4j/Logger.info (Ljava/lang/String;Ljava/lang/Object;)V (itf)
         */
        logger.info("i={}", i);




        /**
         * j++ jvm字节码
         *    L4
         *     LINENUMBER 28 L4
         *     ICONST_1
         *     INVOKESTATIC java/lang/Integer.valueOf (I)Ljava/lang/Integer;
         *     ASTORE 2
         */
        Integer j = 1;
        /**
         *    L5
         *     LINENUMBER 29 L5
         *     ICONST_1
         *     ALOAD 2
         *     ASTORE 3
         *     ALOAD 2
         *     INVOKEVIRTUAL java/lang/Integer.intValue ()I
         *     ICONST_1
         *     IADD
         *     INVOKESTATIC java/lang/Integer.valueOf (I)Ljava/lang/Integer;
         *     ASTORE 2
         *     ALOAD 3
         *     INVOKESTATIC org/junit/jupiter/api/Assertions.assertEquals (ILjava/lang/Integer;)V
         */
        Assertions.assertEquals(1, j++);
        /**
         *    L6
         *     LINENUMBER 30 L6
         *     ICONST_2
         *     ALOAD 2
         *     INVOKESTATIC org/junit/jupiter/api/Assertions.assertEquals (ILjava/lang/Integer;)V
         */
        Assertions.assertEquals(2, j);
        /**
         *    L7
         *     LINENUMBER 31 L7
         *     GETSTATIC cn/aiclr/jvm/base/SimpleTest.logger : Lorg/slf4j/Logger;
         *     LDC "j={}"
         *     ALOAD 2
         *     INVOKEINTERFACE org/slf4j/Logger.info (Ljava/lang/String;Ljava/lang/Object;)V (itf)
         */
        logger.info("j={}", j);
    }
}
