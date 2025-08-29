package cn.aiclr.jvm.designpattern.structural.adapter.springmvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface Controller {
    Logger log = LoggerFactory.getLogger(Controller.class);
}

class HttpController implements Controller {
    public void doHttpHandler() {
        log.info("doHttpHandler");
    }
}

class SimpleController implements Controller {
    public void doSimpleHandler() {
        log.info("doSimpleHandler");
    }
}

class AnnotationController implements Controller {
    public void doAnnotationHandler() {
        log.info("doAnnotationHandler");
    }
}
