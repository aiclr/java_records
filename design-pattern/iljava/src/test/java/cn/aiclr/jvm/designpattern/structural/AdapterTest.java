package cn.aiclr.jvm.designpattern.structural;

import cn.aiclr.jvm.designpattern.structural.adapter.AdapterClass;
import cn.aiclr.jvm.designpattern.structural.adapter.AdapterObject;
import cn.aiclr.jvm.designpattern.structural.adapter.Volatage220v;
import cn.aiclr.jvm.designpattern.structural.adapter.Volatage5V;
import cn.aiclr.jvm.designpattern.structural.adapter.springmvc.DispatchServlet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * @see <a href="https://junit.org/junit5/docs/current/user-guide/#writing-tests-parameterized-tests-sources-ValueSource">@ValueSource</a>
 * @see <a href="https://junit.org/junit5/docs/current/user-guide/#writing-tests-parameterized-tests-sources-null-and-empty">@NullAndEmptySource</a>
 */
@DisplayName("适配器模式")
class AdapterTest {

    @DisplayName("类适配器")
    @Test
    void classAdapter() {
        AdapterClass adapter = new AdapterClass();
        Assertions.assertEquals(5, adapter.outPut5V());
    }

    @DisplayName("对象适配器-组合")
    @Test
    void objectAdapter() {
        AdapterObject adapter = new AdapterObject(new Volatage220v());
        Assertions.assertEquals(5, adapter.outPut5V());
    }

    @DisplayName("接口适配器")
    @Test
    void interfaceAdapter() {
        //内部类实现适配器。接口拥有多个方法，只关注使用到的方法即可,使用时实现具体方法
        Volatage5V adapter = new Volatage5V() {
            @Override
            public int outPut5V() {
                return new Volatage220v().output220v() / 44;
            }
        };
        Assertions.assertEquals(5, adapter.outPut5V());
        //Lambda 适用于接口只有一个方法
        Volatage5V adapterLambda = () -> new Volatage220v().output220v() / 44;
        Assertions.assertEquals(5, adapterLambda.outPut5V());

        // 直接转换
        int result = new Volatage220v().output220v() / 44;
        Assertions.assertEquals(5, result);
    }

    /**
     * <pre>
     * SpringMvc HandlerAdapter
     * DispatcherServlet
     * doDispatch(...) 方法下
     * 通过 HandlerMapping 来映射 Controller
     * mappedHandler=getHandler(processedRequest);
     * 获取适配器
     * HandlerAdapter ha = getHandlerAdapter(mappedHand;ler.getHandler())
     * 通过适配器调用 controller 的方法并返回 ModelAndView
     * mv=ha.handle(...);
     */
    @DisplayName("模拟测试 SpringMvc HandlerAdapter")
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"HttpController", "SimpleController", "AnnotationController"})
    void springMVCTest(String type) {
        new DispatchServlet().doDispatch(type);
    }
}
