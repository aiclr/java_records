package cn.aiclr.jvm.io.nio.local;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

import static cn.aiclr.jvm.io.utils.FileSeparatorUtil.expandUserHome;

/**
 * Channel:用于源节点与目标节点的连接，java nio中负责缓冲区中数据的传输
 * Channel本身不存储数据，因此需要配合缓冲区进行传输
 * <p>
 * java.nio.channels.Channel接口实现类
 * <ul>
 *  <li>FileChanel</li>
 *  <li>SocketChannel</li>
 *  <li>ServerSocketChannel</li>
 *  <li>DatagramChannel</li>
 * </ul>
 * <p>
 * 获取通道
 * java针对支持Channel的类提供getChannel()方法
 * <ul>
 *  <li>本地IO：
 *      <ul>
 *       <li>FileInputStream/FileOutputStream</li>
 *       <li>RandomAccessFile</li>
 *      </ul>
 *  </li>
 *  <li>网络IO：
 *      <ul>
 *       <li>Socket</li>
 *       <li>ServerSocket</li>
 *       <li>DatagramSocket</li>
 *      </ul>
 *  </li>
 *  <li>jdk7,NIO.2针对各种Channel提供静态方法open()</li>
 *  <li>jdk7,NIO.2 Files工具类newByteChannel()</li>
 * </ul>
 * <p>
 * 通道之间数据传输
 * <ul>
 *     <li>transferForm()</li>
 *     <li>transferTo()</li>
 * </ul>
 *
 * <p>
 * 分散读取与聚集写入
 * <ul>
 * <li>Scattering Reads 将通道中的数据分散到多个缓冲区中</li>
 * <li>Gathering Writes 将多个缓冲区中的数据聚集到通道中</li>
 * </ul>
 * <p>
 * 字符集 Charset
 * <ul><li>编码 字符串-->字节数组</li>
 * <li>解码 字节数组-->字符串</li></ul>
 */
class ChannelTest {

    private static final Logger logger = LoggerFactory.getLogger(ChannelTest.class);

    @ParameterizedTest
    @DisplayName("跨平台文件路径获取")
    @CsvSource({"~/Downloads/jdk-21_windows-x64_bin.msi",
            "C:\\Users\\PC\\Downloads\\jdk-21_windows-x64_bin.msi",
            "/home/pc/Desktop/jdk-21_windows-x64_bin.msi",
    })
    void expandUserHomeTest(String filePath) {
        logger.info(expandUserHome(filePath));
    }

    @ParameterizedTest
    @DisplayName("非直接缓冲区复制文件")
    @CsvSource({"4096,~/Downloads/jdk-21_windows-x64_bin.msi,~/Desktop/jdk-21_windows-x64_bin.msi"})
    void copyByBuffer(int capacity, String inFile, String outFile) {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        FileChannel fisChannel = null;
        FileChannel fosChannel = null;
        try {
            fis = new FileInputStream(expandUserHome(inFile));
            fos = new FileOutputStream(expandUserHome(outFile));
            //获取通道
            fisChannel = fis.getChannel();
            fosChannel = fos.getChannel();
            //分配缓冲区大小
            ByteBuffer buf = ByteBuffer.allocate(capacity);
            //从通道写入数据到缓冲区
            while (fisChannel.read(buf) != -1) {
                //切换为读模式
                buf.flip();
                //缓冲区中的数据写入通道中
                fosChannel.write(buf);
                buf.clear();
            }
        } catch (FileNotFoundException e) {
            logger.error("{}", e.getMessage(), e);
        } catch (IOException e) {
            logger.error("{}", e.getMessage(), e);
        } finally {
            try {
                if (fosChannel != null)
                    fosChannel.close();
            } catch (IOException e) {
                logger.error("{}", e.getMessage(), e);
            }
            try {
                if (fisChannel != null)
                    fisChannel.close();
            } catch (IOException e) {
                logger.error("{}", e.getMessage(), e);
            }
            try {
                if (fos != null)
                    fos.close();
            } catch (IOException e) {
                logger.error("{}", e.getMessage(), e);
            }
            try {
                if (fis != null)
                    fis.close();
            } catch (IOException e) {
                logger.error("{}", e.getMessage(), e);
            }
        }
    }

    /**
     * 直接缓冲区复制文件
     * 直接在堆外内存（物理）修改，操作系统不需要再拷贝一次
     * java.nio.MappedByteBuffer
     * <p>
     * MappedByteBuffer map(MapMode mode,long position, long size)
     * mode 读写模式
     * position 直接修改的其实位置
     * size 映射内存的大小,最大值不能超过Integer.MAX_VALUE
     */
    @ParameterizedTest
    @DisplayName("直接缓冲区复制文件")
    @CsvSource({"4096,~/Downloads/jdk-21_windows-x64_bin.msi,~/Desktop/jdk-21_windows-x64_bin.msi"})
    void copyByMappedBuffer(long capacity, String inFile, String outFile) {
        MappedByteBuffer inMappedBuf;
        MappedByteBuffer outMappedBuf;
        int byteSize = (int) capacity;
        byte[] bytes = new byte[byteSize];
        try (FileChannel fisChannel = FileChannel.open(Paths.get(expandUserHome(inFile)), StandardOpenOption.READ);
             FileChannel fosChannel = FileChannel.open(Paths.get(expandUserHome(outFile)), StandardOpenOption.READ, StandardOpenOption.WRITE, StandardOpenOption.CREATE)) {
            long filesize = fisChannel.size();//返回值单位为byte
            for (long i = 0; i < fisChannel.size(); i += capacity, filesize -= capacity) {
                capacity = i + capacity > fisChannel.size() ? filesize : capacity;
                inMappedBuf = fisChannel.map(FileChannel.MapMode.READ_ONLY, i, capacity);
                outMappedBuf = fosChannel.map(FileChannel.MapMode.READ_WRITE, i, capacity);
                if (byteSize > inMappedBuf.limit()) {
                    bytes = new byte[inMappedBuf.limit()];
                }
                inMappedBuf.get(bytes);
                //仅修改path文件，不修改newFilePath
                // 需要先做如下设置：
                // FileChannel fisChannel = FileChannel.open(Paths.get(path), StandardOpenOption.READ,StandardOpenOption.WRITE);
                // inMappedBuf = fisChannel.map(FileChannel.MapMode.READ_WRITE, i, l);
//                 inMappedBuf.put(1,(byte)'-');

                outMappedBuf.put(bytes);

                //仅修改newFilePath，不修改path文件
//                outMappedBuf.put(1, (byte) '-');
            }
        } catch (IOException e) {
            logger.error("{}", e.getMessage(), e);
        }
    }

    @ParameterizedTest
    @DisplayName("直接缓冲区复制文件增强版")
    @CsvSource({"4096,~/Downloads/jdk-21_windows-x64_bin.msi,~/Desktop/jdk-21_windows-x64_bin.msi"})
    void copyByMappedBufferPlus(long capacity, String inFile, String outFile) {
        MappedByteBuffer inmapbuf;
        int byteSize = (int) capacity;
        byte[] bytes = new byte[byteSize];
        try (FileChannel fisChannel = FileChannel.open(Paths.get(expandUserHome(inFile)), StandardOpenOption.READ, StandardOpenOption.WRITE);
             FileChannel fosChannel = FileChannel.open(Paths.get(expandUserHome(outFile)), StandardOpenOption.READ, StandardOpenOption.WRITE, StandardOpenOption.CREATE)) {
            long filesize = fisChannel.size();//返回值单位为byte数
            for (long i = 0; i < fisChannel.size(); i += capacity, filesize -= capacity) {
                capacity = i + capacity > fisChannel.size() ? filesize : capacity;
                inmapbuf = fisChannel.map(FileChannel.MapMode.READ_ONLY, i, capacity);
                if (byteSize > inmapbuf.limit()) {
                    bytes = new byte[inmapbuf.limit()];
                }
                inmapbuf.get(bytes);
                inmapbuf.flip();
                fosChannel.write(inmapbuf);
                inmapbuf.clear();
            }

        } catch (IOException e) {
            logger.error("{}", e.getMessage(), e);
        }
    }

    @ParameterizedTest
    @DisplayName("直接缓冲区 transferFrom 通道之间数据传输 复制文件。15g 文件直接停电关机,可能是固态硬盘过热，掉盘")
    @CsvSource({"~/Downloads/jdk-21_windows-x64_bin.msi,~/Desktop/jdk-21_windows-x64_bin.msi"})
    void copyByTransferFrom(String inFile, String outFile) {
        try (FileChannel inChannel = FileChannel.open(Paths.get(expandUserHome(inFile)), StandardOpenOption.READ);
             FileChannel outChannel = FileChannel.open(Paths.get(expandUserHome(outFile)), StandardOpenOption.READ, StandardOpenOption.WRITE, StandardOpenOption.CREATE_NEW);) {
            outChannel.transferFrom(inChannel, 0, inChannel.size());
        } catch (IOException e) {
            logger.error("{}", e.getMessage(), e);
        }
    }

    @ParameterizedTest
    @DisplayName("直接缓冲区 transferTo 通道之间数据传输 复制文件。15g 文件直接停电关机,可能是固态硬盘过热，掉盘")
    @CsvSource({"~/Downloads/jdk-21_windows-x64_bin.msi,~/Desktop/jdk-21_windows-x64_bin.msi"})
    void copyByTransferTo(String inFile, String outFile) {
        try (FileChannel inChannel = FileChannel.open(Paths.get(expandUserHome(inFile)), StandardOpenOption.READ);
             FileChannel outChannel = FileChannel.open(Paths.get(expandUserHome(outFile)), StandardOpenOption.READ, StandardOpenOption.WRITE, StandardOpenOption.CREATE_NEW);) {
            inChannel.transferTo(0, inChannel.size(), outChannel);
        } catch (IOException e) {
            logger.error("{}", e.getMessage(), e);
        }
    }

    @ParameterizedTest
    @DisplayName("分散读取 聚集写入")
    @CsvSource({"~/Desktop/查数脚本.sql,~/Desktop/查数脚本.txt"})
    void randomAccessFileTest(String inFile, String outFile) {
        try (RandomAccessFile raf1 = new RandomAccessFile(expandUserHome(inFile), "rw");
             FileChannel channel1 = raf1.getChannel();
             RandomAccessFile raf2 = new RandomAccessFile(expandUserHome(outFile), "rw");
             FileChannel channel2 = raf2.getChannel();
        ) {
            ByteBuffer buf1 = ByteBuffer.allocate(100);
            ByteBuffer buf2 = ByteBuffer.allocate(1024);
            ByteBuffer[] bufs = {buf1, buf2};
            //分散读取
            channel1.read(bufs);
            for (ByteBuffer byteBuffer : bufs) {
                byteBuffer.flip();
            }
            logger.info(new String(bufs[0].array(), 0, bufs[0].limit()));
            logger.info("###SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS");
            logger.info(new String(bufs[1].array(), 0, bufs[1].limit()));
            //聚集写入
            channel2.write(bufs);
        } catch (FileNotFoundException e) {
            logger.error("{}", e.getMessage(), e);
        } catch (IOException e) {
            logger.error("{}", e.getMessage(), e);
        }
    }

    @Test
    @DisplayName("Charset.availableCharsets()")
    void availableCharsets() {
        SortedMap<String, Charset> map = Charset.availableCharsets();
        Set<Map.Entry<String, Charset>> entries = map.entrySet();
        for (Map.Entry<String, Charset> entry : entries) {
            logger.info("[{}]=[{}]", entry.getKey(), entry.getValue());
        }
    }

    @Test
    @DisplayName("中文字符集范围 GB18030（2000年） > GBK（1995年） > GB2312（1980年）")
    void charset() {
        Charset gb2312 = Charset.forName("GB2312");
        //编码器
        CharsetEncoder encoder = gb2312.newEncoder();
        //解码器
        CharsetDecoder decoder = gb2312.newDecoder();

        CharBuffer cb = CharBuffer.allocate(1024);
        cb.put("冷冷的冰雨在脸上胡乱的拍");
        //切换读模式
        cb.flip();
        //编码
        ByteBuffer eb = null;
        CharBuffer decode = null;
        try {
            //GBK编码
            eb = encoder.encode(cb);
            for (int i = 0; i < eb.limit(); i++) {
                logger.info("{}", eb.get());
            }
            //切换读模式
            eb.flip();
            //GBK解码
            decode = decoder.decode(eb);
            logger.info("{}", decode);

            //切换读模式
            eb.flip();
            //UTF-8 解码
            CharBuffer decode1 = StandardCharsets.UTF_8.decode(eb);
            logger.error("{}", decode1);

            Charset gbk = Charset.forName("GBK");
            //切换读模式
            eb.flip();
            //GBK 解码
            logger.info("{}", gbk.decode(eb));

            Charset gb18030 = Charset.forName("GB18030");
            //切换读模式
            eb.flip();
            //GB18030 解码
            logger.info("{}", gb18030.decode(eb));
        } catch (CharacterCodingException e) {
            logger.error("{}", e.getMessage(), e);
        }
    }
}