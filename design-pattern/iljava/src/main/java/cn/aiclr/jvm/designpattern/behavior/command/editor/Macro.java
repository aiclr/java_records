package cn.aiclr.jvm.designpattern.behavior.command.editor;

import java.util.ArrayList;
import java.util.List;

/**
 * 宏
 */
public class Macro {
    private final List<Action> actions;

    public Macro() {
        actions = new ArrayList<>();
    }

    public void record(Action action) {
        actions.add(action);
    }

    public void run() {
        actions.forEach(Action::perform);
    }
}
