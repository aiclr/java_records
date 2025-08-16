package cn.aiclr.jvm.suggestions.book.java151.chapter06.dm;

/**
 * 使用枚举定义通用鉴权者
 */
public enum CommonIdentifier implements Identifier {

    //权限等级
    Reader, Author, Admin;

    /**
     * 鉴权实现
     */
    @Override
    public boolean identify() {
        return switch (this) {
            case Admin, Author -> true;
            case Reader -> false;
        };
    }
}
