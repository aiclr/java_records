package cn.aiclr.jvm.suggestions.book.java151.chapter06;

/**
 * <pre>86.在 switch 的 default 代码块中增加 AssertionError 错误
 *
 * switch 后跟枚举类型，case 后列出所有的枚举项，这是一个使用枚举的主流写法，
 * 那留着 default 语句似乎没有任何作用，
 * 程序已经列举了所有的可能选项，肯定不会执行到default语句，
 * 看上去纯属多余嘛！错了，这个 default 还是很有用的
 */
public class Dh {

    /**
     * <pre>由于把所有的枚举项都列举完了，不可能有其他值，
     * 所以就不需要 default 代码块了，这是普遍的认识，
     * 但问题是我们的 switch 代码与枚举之间没有强制约束关系，
     * 也就是说两者只是在语义上建立了联系，并没有一个强制约束，
     * 比如 LogLevel 枚举发生改变，增加了一个枚举项 FATAL，
     * 如果此时我们对 switch 语句不做任何修改，编译虽不会出现问题，
     * 但是运行期会发生非预期的错误： FATAL 类型的日志没有输出
     *
     * 避免出现这类错误，建议在 default 后直接抛出一个 AssertionError 错误
     */
    public static void logLevel(LogLevelDh logLevel) {
        switch (logLevel) {
            case DEBUG -> System.out.println("debug");
            case INFO -> System.out.println("info");
            case WARN -> System.out.println("warn");
            case ERROR -> System.out.println("error");
            default -> throw new IllegalArgumentException("Unexpected value: " + logLevel);
        }
    }

    public enum LogLevelDh {
        FATAL, DEBUG, INFO, WARN, ERROR;
    }
}
