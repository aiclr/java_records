package cn.aiclr.jvm.suggestions.book.java151.chapter06.dm;

/**
 * 鉴权人
 */
public interface Identifier {
    //提示
    String REFUSE_WORD = "无权访问";

    //鉴权
    boolean identify();
}
