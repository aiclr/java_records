package cn.aiclr.jvm.designpattern.structural.proxy.proxystatic;

/**
 * 被代理类
 */
public class TeacherImpl implements Teacher {

    @Override
    public void teach() {
        log.info("Teacher 正在工作");
    }
}
