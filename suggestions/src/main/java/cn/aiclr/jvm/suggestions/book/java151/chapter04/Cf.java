package cn.aiclr.jvm.suggestions.book.java151.chapter04;

/**
 * <pre>58.强烈建议使用 UTF 编码
 * Java中的编码规则:
 *  1.Java 文件编码
 *      记事本创建一个 .java 后缀的文件，则文件的编码格式就是操作系统默认的格式。如果是使用 IDE 工具创建的，如 Eclipse，则依赖于 IDE 的设置，Eclipse 默认是操作系统编码（Windows一般为GBK）
 *  2.Class 文件编码
 *      通过 javac 命令生成的后缀名为 .class 的文件是 UTF-8 编码的 UNICODE 文件，
 *      这在任何操作系统上都是一样的，只要是 class 文件就会是 UNICODE 格式。
 *      需要说明的是，UTF 是 UNICODE 的存储和传输格式，它是为了解决 UNICODE 的高位占用冗余空间而产生的，
 *      使用 UTF 编码就标志着字符集使用的是 UNICODE
 *
 * Java乱码  一般都是字符集不一致造成的
 * 中文字符集范围 GB18030（2000年） > GBK（1995年） > GB2312（1980年）版本向上兼容，只是包含汉字数量不同
 */
public class Cf {
}