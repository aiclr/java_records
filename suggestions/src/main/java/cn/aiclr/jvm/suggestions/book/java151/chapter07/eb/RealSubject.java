package cn.aiclr.jvm.suggestions.book.java151.chapter07.eb;

/**
 * 具体主题实现类型
 */
public class RealSubject implements Subject {

    @Override
    public void request() {
        logger.info("RealSubject 具体主题角色实现类");
    }
}
