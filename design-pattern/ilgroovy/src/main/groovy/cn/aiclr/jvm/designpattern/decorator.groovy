package cn.aiclr.jvm.designpattern

import groovy.sql.Sql

import java.lang.reflect.InvocationHandler
import java.sql.Connection

/**
 * <pre>装饰器模式
 * 传统写法
 */
class Logger {
    def log(String message) {
        println message
    }
}

class TimeStampingLogger extends Logger {
    private Logger logger

    TimeStampingLogger(logger) {
        this.logger = logger
    }

    def log(String message) {
        def now = Calendar.instance
        logger.log("$now.time: $message")
    }
}

class UpperLogger extends Logger {
    private Logger logger

    UpperLogger(logger) {
        this.logger = logger
    }

    def log(String message) {
        logger.log(message.toUpperCase())
    }
}

def logger = new UpperLogger(new TimeStampingLogger(new Logger()))
logger.log("G'day Mate")

logger = new TimeStampingLogger(new UpperLogger(new Logger()))
logger.log("Hi There")

/**
 * <pre>
 * a touch of dynamic behaviour
 * 闭包三属性 this owner delegate
 * 利用 delegate 动态写法
 */
println "\n利用 delegate 动态写法"

class GenericLowerDecorator {
    private delegate

    GenericLowerDecorator(delegate) {
        this.delegate = delegate
    }

    /**
     * <pre>在 Groovy 中，{@link GroovyObject#invokeMethod(String, Object)} 是一个非常强大的 元编程（Metaprogramming） 方法， 它允许你在运行时动态地拦截和处理方法调用。
     * 它是 GroovyObject 接口的一部分，是实现动态行为的核心机制之一
     *
     * 会拦截所有方法调用（包括存在的）
     *
     * 当你调用一个对象上不存在的方法时，Groovy 会尝试调用该对象的 invokeMethod 来处理这个“未知”调用
     *
     * 与 methodMissing 的区别
     *
     * Groovy 提供了两个类似的机制：
     * 方法	             用途	                    优先级
     * methodMissing	仅拦截不存在的方法调用	        ✅ 更高
     * invokeMethod	    拦截所有方法调用（包括存在的）	较低
     *
     * 如果你想让 invokeMethod 拦截所有方法调用（包括存在的），你需要不实现 methodMissing，并确保方法未被直接定义
     *
     * methodMissing 是 Groovy 的一个“魔法方法”，当你调用一个对象上不存在的方法时，
     * Groovy 会自动调用 methodMissing 来处理这个调用，而不是直接抛出 MissingMethodException。
     *
     */
    def invokeMethod(String name, args) {
        def newArgs = args.collect { arg ->
            if (arg instanceof String) {
                return arg.toLowerCase()
            } else {
                return arg
            }
        }
        delegate.invokeMethod(name, newArgs)
    }
}

class GenericUpperDecorator {
    private delegate

    GenericUpperDecorator(delegate) {
        this.delegate = delegate
    }

    def invokeMethod(String name, args) {
        def newArgs = args.collect { arg ->
            if (arg instanceof String) {
                return arg.toUpperCase()
            } else {
                return arg
            }
        }
        delegate.invokeMethod(name, newArgs)
    }
}

class GenericTimeStampingDecorator {
    private delegate

    GenericTimeStampingDecorator(delegate) {
        this.delegate = delegate
    }

    def invokeMethod(String name, args) {
        def newArgs = args.collect { arg ->
            if (arg instanceof String) {
                def now = Calendar.instance
                // 注意 GStringImpl
                println("${now.time}: $arg".class)
                println(("${now.time}: $arg" as String).class)
                return "${now.time}: $arg" as String
            } else {
                return arg
            }
        }
        delegate.invokeMethod(name, newArgs)
    }
}


logger = new GenericLowerDecorator(new TimeStampingLogger(new Logger()))
logger.log('new GenericLowerDecorator(new TimeStampingLogger(new Logger()))')
println()

logger = new GenericTimeStampingDecorator(new UpperLogger(new Logger()))
logger.log('new GenericTimeStampingDecorator(new UpperLogger(new Logger()))')
println()

logger = new GenericUpperDecorator(new GenericTimeStampingDecorator(new Logger()))
logger.log('new GenericUpperDecorator(new GenericTimeStampingDecorator(new Logger()))')

/**
 * More dynamic decorating
 */
println "\n利用 delegate 动态写法"

class Calc {
    def add(a, b) { a + b }
}

class TracingDecorator {
    private delegate

    TracingDecorator(delegate) {
        this.delegate = delegate
    }

    def invokeMethod(String name, args) {
        println "Calling $name$args"
        def before = System.currentTimeMillis()
        def result = delegate.invokeMethod(name, args)
        println "Got $result in ${System.currentTimeMillis() - before} ms"
        result
    }
}

def traceCalc = new TracingDecorator(new Calc())
assert 15 == traceCalc.add(3, 12)

/**
 * <pre>Decorating with an Interceptor
 * 用拦截器装饰
 */
println "\n利用 ProxyMetaClass 和 TracingInterceptor，用拦截器装饰"

class TimingInterceptor extends TracingInterceptor {
    private beforeTime

    def beforeInvoke(object, String methodName, Object[] arguments) {
        super.beforeInvoke(object, methodName, arguments)
        beforeTime = System.currentTimeMillis()
    }

    Object afterInvoke(Object object, String methodName, Object[] arguments, Object result) {
        super.afterInvoke(object, methodName, arguments, result)
        def duration = System.currentTimeMillis() - beforeTime
        writer.write("Duration: $duration ms\n")
        writer.flush()
        result
    }
}

/**
 * 全称	        缩写	    含义
 * constructor	ctor	构造函数，用于创建和初始化对象的特殊方法
 */
def proxy = ProxyMetaClass.getInstance(Calc)
proxy.interceptor = new TimingInterceptor()
proxy.use {
    assert 7 == new Calc().add(1, 6)
}

/**
 * <pre>Runtime behaviour embellishment
 * 利用 ExpandoMetaClass 动态写法
 */
println "\n利用 ExpandoMetaClass 动态写法"
//下面一行 current mechanism(机制) to enable ExpandoMetaClass，注释掉也可以正常执行
GroovySystem.metaClassRegistry.metaClassCreationHandle = new ExpandoMetaClassCreationHandle()
logger = new Logger()
logger.metaClass.log = { String m -> println 'message: ' + m.toUpperCase() }
logger.log('GroovySystem.metaClassRegistry.metaClassCreationHandle=new ExpandoMetaClassCreationHandle()')

/**
 *  Decorating with java.lang.reflect.Proxy
 */
protected Sql getGroovySql() {
    final Connection con = session.connection()
    def invoker = { object, method, args ->
        if (method.name == "close") {
            log.debug("ignoring call to Connection.close() for use by groovy.sql.Sql")
        } else {
            log.trace("delegating $method")
            return con.invokeMethod(method.name, args)
        }
    } as InvocationHandler;
    def proxy = Proxy.newProxyInstance(getClass().getClassLoader(), [Connection] as Class[], invoker)
    return new Sql(proxy)
}
