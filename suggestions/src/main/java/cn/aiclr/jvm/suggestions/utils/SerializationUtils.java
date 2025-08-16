package cn.aiclr.jvm.suggestions.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * 读写java类
 */
public class SerializationUtils {

    private static final Logger logger = LoggerFactory.getLogger(SerializationUtils.class);

    private static String FILE_NAME = null;

    static {
        if (File.separator.equals("/")) {
            //unix
            FILE_NAME = "/tmp/java/obj.bin";
        } else if (File.separator.equals("\\")) {
            //win
            FILE_NAME = "d:/tmp/java/obj.bin";
        }
    }

    /**
     * 序列化
     */
    public static void writeObject(Serializable s) {
        try (OutputStream fo = new FileOutputStream(FILE_NAME);
             ObjectOutputStream oos = new ObjectOutputStream(fo)
        ) {
            oos.writeObject(s);
        } catch (Exception e) {
            logger.error("{}", e.getMessage(), e);
        }
    }

    /**
     * 反序列化 把一个对象从内存块转化为可传输的数据流，通过网络发送到消息消费者那里，进行反序列化，生成实例对象
     */
    public static Object readObject() {
        Object obj = null;
        try (InputStream fi = new FileInputStream(FILE_NAME);
             ObjectInput input = new ObjectInputStream(fi)
        ) {
            obj = input.readObject();
        } catch (Exception e) {
            logger.error("{}", e.getMessage(), e);
        }
        return obj;
    }

    /**
     * <pre>此工具类要求被拷贝的对象必须实现 Serializable 接口
     * 注意
     * 1.对象的内部属性都是可序列化的
     *      如果有内部属性不可序列化，则会抛出序列化异常，这会让调试者很纳闷：生成一个对象怎么会出现序列化异常呢？从这一点来考虑，也需要把 CloneUtils 工具的异常进行细化处理
     * 2.注意方法和属性的特殊修饰符
     *      比如 final、static 变量的序列化问题会被引入到对象拷贝中来（参考12），这点需要特别注意，同时 transient 变量（瞬态变量，不进行序列化的变量）也会影响到拷贝的效果
     *
     * 参考 Apache commons 工具类
     * {@link org.apache.commons.lang3.SerializationUtils}
     * {@link org.apache.commons.lang3.SerializationUtils#serialize(Serializable)}
     * {@link org.apache.commons.lang3.SerializationUtils#deserialize(byte[])}
     */
    @SuppressWarnings("unchecked")
    public static <T extends Serializable> T clone(T obj) {
        //新对象
        T cloneObj = null;

        ByteArrayOutputStream baos = null;
        ObjectOutputStream oos = null;
        ByteArrayInputStream bais = null;
        ObjectInputStream ois = null;
        try {
            //读取源对象字节
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(obj);
            //分配内存空间写入原始对象，生产新对象
            bais = new ByteArrayInputStream(baos.toByteArray());
            ois = new ObjectInputStream(bais);
            //返回新对象
            cloneObj = (T) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            logger.error("{}", e.getMessage(), e);
            throw new RuntimeException("error", e);
        } finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    logger.error("{}", e.getMessage(), e);
                }
            }
            if (bais != null) {
                try {
                    bais.close();
                } catch (IOException e) {
                    logger.error("{}", e.getMessage(), e);
                }
            }
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    logger.error("{}", e.getMessage(), e);
                }
            }
            if (baos != null) {
                try {
                    baos.close();
                } catch (IOException e) {
                    logger.error("{}", e.getMessage(), e);
                }
            }
        }
        return cloneObj;
    }
}
