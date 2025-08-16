package cn.aiclr.jvm.suggestions.book.java151.chapter06.dl;

public class Sparrow extends Bird {

    private Desc.Color color;

    public Sparrow() {
    }

    public Sparrow(Desc.Color color) {
        this.color = color;
    }

    @Override
    public Desc.Color getColor() {
        return color;
    }
}
