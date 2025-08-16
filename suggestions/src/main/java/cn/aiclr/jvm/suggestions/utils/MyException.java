package cn.aiclr.jvm.suggestions.utils;

import java.util.ArrayList;
import java.util.List;

public class MyException extends Exception {

    private List<Throwable> causes = new ArrayList<>();

    public List<Throwable> getCauses() {
        return causes;
    }

    public void setCauses(List<Throwable> causes) {
        this.causes = causes;
    }

    public MyException(List<? extends Throwable> causes) {
        this.causes.addAll(causes);
    }
}
