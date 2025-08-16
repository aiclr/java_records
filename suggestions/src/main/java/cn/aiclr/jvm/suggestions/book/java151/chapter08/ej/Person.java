package cn.aiclr.jvm.suggestions.book.java151.chapter08.ej;

public class Person {

    /**
     * 构造函数不应该抛出非受检异常
     * @param _age
     */
    public Person(int _age) {
        if (_age < 18) {
            throw new RuntimeException("年龄必须不小于18");
        }
    }

    public void seeMovie() {
        System.out.println("欣赏小黄片");
    }

}
