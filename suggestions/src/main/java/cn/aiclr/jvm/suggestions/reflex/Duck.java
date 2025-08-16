package cn.aiclr.jvm.suggestions.reflex;

public class Duck extends Animal {

    private int level;

    public Duck(int level) {
        this.level = level;
    }

    private int getLevel() {
        return level;
    }

    private void setLevel(int level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return super.toString() + "\tlevel=" + level;
    }
}
