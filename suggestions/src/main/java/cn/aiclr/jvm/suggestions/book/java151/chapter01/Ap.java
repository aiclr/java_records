package cn.aiclr.jvm.suggestions.book.java151.chapter01;

import com.oracle.truffle.js.scriptengine.GraalJSEngineFactory;

import javax.script.Bindings;
import javax.script.Invocable;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Objects;

/**
 * <pre>16.易变业务使用脚本语言编写
 * 脚本语言特性
 *  灵活。脚本语言一般都是动态类型，可以不用声明变量类型而直接使用，也可以在运行期改变类型
 *  便捷。脚本语言是一种解释型语言，不需要编译成二进制代码，也不需要像Java一样生成字节码。它的执行是依靠解释器解释的，因此在运行期变更代码非常容易，而且不用停止应用
 *  简单。只能说部分脚本语言简单，比如Groovy，Java程序员若转到Groovy程序语言上，只需要两个小时，看完语法说明，看完Demo即可使用了，没有太多的技术门槛
 * 脚本语言的这些特性是Java所缺少的，引入脚本语言可以使Java更强大，
 *
 * 于是Java 从 JDK 1.6 开始内置了对脚本语言的支持，其中包括 JavaScript（通过内嵌的 Nashorn 引擎 在 JDK 8~14 中支持，JDK 15+ 已移除 Nashorn）
 * 为了在 JDK 15 及以上版本中继续使用 JavaScript 引擎，你可以使用 GraalVM 提供的 JavaScript 引擎。
 *      <a href="https://mvnrepository.com/artifact/org.graalvm.js/js">js</a>
 *      <a href="https://mvnrepository.com/artifact/org.graalvm.js/js-scriptengine">js-scriptengine</a>
 *
 * 但是因为脚本语言比较多，Java的开发者也很难确定该支持哪种语言，
 * 于是 JCP（Java Community Process）很聪明地提出了 JSR223 规范，
 * 只要符合该规范的语言都可以在Java平台上运行（它对JavaScript是默认支持的）
 * 诸位读者有兴趣的话可以自己写个脚本语言，然后再实现ScriptEngine，即可在Java平台上运行
 */
public class Ap {

    /**
     * JavaScript（通过内嵌的 Nashorn 引擎 在 JDK 8~14 中支持，JDK 15+ 已移除 Nashorn）
     * 为了在 JDK 15 及以上版本中继续使用 JavaScript 引擎，你可以使用 GraalVM 提供的 JavaScript 引擎。
     */
    public Integer invokeJavaScript(int var1, int var2) {
        //获取一个JavaScript的执行引擎
        ScriptEngineFactory jsEngineFactory = new GraalJSEngineFactory();
        ScriptEngine engine = new ScriptEngineManager().getEngineByName(jsEngineFactory.getEngineName());
        Integer result = null;
        if (Objects.nonNull(engine)) {
            //建立上下文变量
            Bindings bind = engine.createBindings();
            bind.put("factor", 1);
            //绑定上下文，作用域是当前引擎范围
            engine.setBindings(bind, ScriptContext.ENGINE_SCOPE);
            try (InputStream is = getClass().getClassLoader().getResourceAsStream("js/Ap.js");
                 Reader reader = new InputStreamReader(Objects.requireNonNull(is));
            ) {
                //执行js代码
                engine.eval(reader);
                //是否可回调方法
                if (engine instanceof Invocable invocable) {
                    //执行js中的函数
                    result = (Integer) invocable.invokeFunction("formula", var1, var2);
                } else {
                    throw new RuntimeException("脚本引擎不支持方法调用");
                }
            } catch (IOException | ScriptException | NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }
}