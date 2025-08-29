package cn.aiclr.jvm.designpattern.structural.adapter.springmvc;

import java.util.ArrayList;
import java.util.List;

public class DispatchServlet {

    public static List<HandlerAdapter> handlerAdapters = new ArrayList<>();

    public DispatchServlet() {
        handlerAdapters.add(new AnnotationHandlerAdapter());
        handlerAdapters.add(new HttpHandlerAdapter());
        handlerAdapters.add(new SimpleHandlerAdapter());
    }

    public void doDispatch(String type) {
        if (type == null || type.isEmpty())
            return;
        //此处模拟 SpringMVC 从 request 取 handler 对象
        //适配器可以获取到希望的 Controller
        Controller controller = null;
        switch (type) {
            case "HttpController":
                controller = new HttpController();
                break;
            case "SimpleController":
                controller = new SimpleController();
                break;
            case "AnnotationController":
                controller = new AnnotationController();
                break;
            default:
                break;
        }
        if (controller != null) {
            //获取对应适配器：Controller 变化，适配器也随之变化获取对应的 Adapter
            HandlerAdapter adapter = getHandler(controller);
            //通过适配器执行对应的controller对应的方法
            adapter.handle(controller);
        }
    }

    public HandlerAdapter getHandler(Controller controller) {
        //遍历 根据得到的controller，返回对应的适配器
        for (HandlerAdapter adapter : handlerAdapters) {
            if (adapter.supports(controller)) {
                return adapter;
            }
        }
        return null;
    }

}
