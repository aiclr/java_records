package cn.aiclr.jvm.suggestions.book.java151.chapter07.ed;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * <pre> 反射优化
 * 在一般的模板方法模式中，抽象模板（这里是 {@link AbsPopulator}类）需要定义一系列的基本方法，
 * 一般都是 protected 访问级别的，并且是抽象方法，
 * 这标志着子类必须实现这些基本方法，这对子类来说既是一个约束也是一个负担。
 * 但是使用了反射后，不需要定义任何抽象方法，
 * 只需定义一个基本方法鉴别器（例子中 {@link #isInitDataMethod(java.lang.reflect.Method)}）即可加载符合规则的基本方法。
 * 鉴别器在此处的作用是鉴别子类方法中哪些是基本方法，
 * 模板方法（例子中的 {@link #dataInitialing()} ）则根据基本方法鉴别器返回的结果通过反射执行相应的方法
 */
public abstract class AbsPopulatorPro {

    protected static final Logger logger = LoggerFactory.getLogger(AbsPopulatorPro.class);

    public final void dataInitialing() {
        Method[] methods = getClass().getMethods();
        try {
            for (Method method : methods) {
                if (method.canAccess(this) && isInitDataMethod(method)) {
                    method.invoke(this);
                }
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            logger.error("{}", e.getMessage(), e);
        }
    }

    private boolean isInitDataMethod(Method method) {
        return method.getName().startsWith("init") //方法名以 init 开头
                && method.canAccess(this) //可跳过安全检查
                && Modifier.isPublic(method.getModifiers()) // public 方法
                && method.getReturnType().equals(Void.TYPE) // 返回值为 void
                && !method.isVarArgs() // 参数为空
                && !Modifier.isAbstract(method.getModifiers()); // 非抽象方法
    }
}
