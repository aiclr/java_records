package cn.aiclr.jvm.suggestions.book.java151.chapter01;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <pre>17.慎用动态编译
 * java6 开始支持动态编译，可以在运行期直接编译java文件，执行class，并且能够获得相关的输入输出，甚至还能监听相关的事件
 * Java的动态编译对源提供了多个渠道。
 * 比如
 *      可以是字符串（例子中就是字符串），
 *      可以是文本文件，
 *      可以是编译过的字节码文件（.class文件），
 *      可以是存放在数据库中的明文代码或是字节码。
 * 只要是符合Java规范的就都可以在运行期动态加载，
 *
 * 其实现方式就是实现JavaFileObject接口，
 *      重写getCharContent、openInputStream、openOutputStream，
 * 或者实现JDK已经提供的两个SimpleJavaFileObject、ForwardingJavaFileObject
 *
 * 注意
 * （1）在框架中谨慎使用
 *      比如要在Struts中使用动态编译，动态实现一个类，它若继承自ActionSupport就希望它成为一个Action。能做到，但是debug很困难；
 *      再比如在Spring中，写一个动态类，要让它动态注入到Spring容器中，这是需要花费老大功夫的。
 * （2）不要在要求高性能的项目使用动态编译
 *      毕竟需要一个编译过程，与静态编译相比多了一个执行环节，因此在高性能项目中不要使用动态编译。
 *      不过，如果是在工具类项目中它则可以很好地发挥其优越性，
 *      比如在Eclipse工具中写一个插件，就可以很好地使用动态编译，不用重启即可实现运行、调试功能，非常方便。
 * （3）动态编译要考虑安全问题
 *      如果你在Web界面上提供了一个功能，允许上传一个Java文件然后运行，那就等于说：“我的机器没有密码，大家都来看我的隐私吧”，这是非常典型的注入漏洞，
 *      只要上传一个恶意Java程序就可以让你所有的安全工作毁于一旦。
 * （4）记录动态编译过程建议记录源文件、目标文件、编译过程、执行过程等日志，
 *      不仅仅是为了诊断，还是为了安全和审计，对Java项目来说，空中编译和运行是很不让人放心的，留下这些依据可以更好地优化程序。
 */
public class Aq {

    private static final Logger logger = LoggerFactory.getLogger(Aq.class);

    public static void main(String[] args) throws Exception {
        // 1. 构建 Java 源代码字符串

        //类名及文件
        String clsName = "Hello";
        //方法名
        String methodName = "sayHello";
        //源码
        String sourceStr = """
                public class Hello {
                    public String sayHello(String name){
                        return "Hello,"+name+"!";
                    }
                }
                """;

        // 2. 创建 JavaFileObject 对象
        SimpleJavaFileObject javaSource = new StringJavaObject(clsName, sourceStr);

        // 3. 获取系统编译器
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

        //DiagnosticCollector 是 Java 编译工具 API（javax.tools 包）中的一个类，
        //主要用于收集编译过程中产生的诊断信息（如错误、警告等）。
        //它通常与 JavaCompiler.CompilationTask 一起使用，以便在编译完成后能够获取详细的诊断信息。
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();

        //Java标准文件管理器
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null);

        //自定义 javax.tools.ForwardingJavaFileManager; 将.class 文件 保存到内存中
        InMemoryJavaFileManager memoryJavaFileManager = new InMemoryJavaFileManager(fileManager);

        //编译参数，类似于javac <options> 中的options
        List<String> options = new ArrayList<>();
        //.class文件存储位置
//        options.addAll(Arrays.asList("-d","./tmp/java"));
//        options.addAll(Arrays.asList("-d", "d:/tmp/java"));

        //要编译的 文件
        List<JavaFileObject> javaFileObjects = Arrays.asList(javaSource);

        // 4. 编译
        JavaCompiler.CompilationTask task = compiler.getTask(null, memoryJavaFileManager, diagnostics, options, null, javaFileObjects);

        boolean success = task.call();
        if (!success) {
            for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics()) {
                logger.error("{}", diagnostic);
            }
            throw new RuntimeException("Compilation failed.");
        }

        // 5. 自定义 ClassLoader 来加载编译后的类
        InMemoryClassLoader loader = new InMemoryClassLoader();

        // 6. 反射调用方法
        Class<?> helloClass = loader.defineClass(clsName, memoryJavaFileManager.getClassBytes().get(clsName));

        Object instance = helloClass.getDeclaredConstructor().newInstance();
        Method sayHello = helloClass.getMethod(methodName, String.class);
        String result = (String) sayHello.invoke(instance, "Dynamic Compilation");
        logger.info("{}", result);  // 输出：Hello,Dynamic Compilation!
    }
}

/**
 * <pre>SimpleJavaFileObject 是 JavaFileObject 的一个抽象实现，用于封装 Java 源文件或类文件的内容。
 * 它允许你自定义源码的来源（比如字符串、数据库、网络等），而不仅仅是来自磁盘文件。
 * Java 源文件对象
 */
class StringJavaObject extends SimpleJavaFileObject {
    //源代码
    final String code;

    //遵循java规范的类名和文件
    public StringJavaObject(String name, String code) {
        super(URI.create("string:///" + name.replace('.', '/') + Kind.SOURCE.extension), // @link substring="URI.create" target="URI#create(String)"
                Kind.SOURCE);
        this.code = code;
    }

    @Override
    public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
        return code;
    }
}

/**
 * <pre>SimpleJavaFileObject 是 JavaFileObject 的一个抽象实现，用于封装 Java 源文件或类文件的内容。
 * 它允许你自定义源码的来源（比如字符串、数据库、网络等），而不仅仅是来自磁盘文件。
 * java 编译后的 .class 类文件对象
 */
class StringJavaClassObject extends SimpleJavaFileObject {
    private ByteArrayOutputStream bos = new ByteArrayOutputStream();

    public StringJavaClassObject(String name, JavaFileObject.Kind kind) {
        super(URI.create("string:///" + name + kind.extension), kind);
    }

    @Override
    public OutputStream openOutputStream() {
        return bos;
    }

    public byte[] getBytes() {
        return bos.toByteArray();
    }
}

/**
 * <pre>ForwardingJavaFileManager 是一个装饰器模式的实现，用于包装标准的 JavaFileManager，从而可以拦截和自定义某些行为，
 * 例如：
 *  在写入 .class 文件时将其保存到内存中而不是磁盘。
 *  自定义类加载逻辑。
 *  动态修改编译过程。
 */
class InMemoryJavaFileManager extends ForwardingJavaFileManager<JavaFileManager> {

    private final Map<String, StringJavaClassObject> classFiles = new HashMap<>();

    public InMemoryJavaFileManager(JavaFileManager fileManager) {
        super(fileManager);
    }

    public Map<String, byte[]> getClassBytes() {
        return classFiles.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().getBytes()));
    }

    @Override
    public JavaFileObject getJavaFileForOutput(Location location, String className, JavaFileObject.Kind kind, FileObject sibling) throws IOException {
        if (kind == JavaFileObject.Kind.CLASS) {
            StringJavaClassObject file = new StringJavaClassObject(className, kind);
            classFiles.put(className, file);
            return file;
        }
        return super.getJavaFileForOutput(location, className, kind, sibling);
    }
}

/**
 * 自定义 ClassLoader
 */
class InMemoryClassLoader extends ClassLoader {
    public Class<?> defineClass(String name, byte[] bytes) {
        return defineClass(name, bytes, 0, bytes.length);
    }
}

