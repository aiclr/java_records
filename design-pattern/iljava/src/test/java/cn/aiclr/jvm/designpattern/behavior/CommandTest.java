package cn.aiclr.jvm.designpattern.behavior;

import cn.aiclr.jvm.designpattern.behavior.command.Command;
import cn.aiclr.jvm.designpattern.behavior.command.CommandLightOff;
import cn.aiclr.jvm.designpattern.behavior.command.CommandLightOn;
import cn.aiclr.jvm.designpattern.behavior.command.Invoker;
import cn.aiclr.jvm.designpattern.behavior.command.ReceiverLight;
import cn.aiclr.jvm.designpattern.behavior.command.editor.Action;
import cn.aiclr.jvm.designpattern.behavior.command.editor.ActionClose;
import cn.aiclr.jvm.designpattern.behavior.command.editor.ActionOpen;
import cn.aiclr.jvm.designpattern.behavior.command.editor.ActionSave;
import cn.aiclr.jvm.designpattern.behavior.command.editor.Editor;
import cn.aiclr.jvm.designpattern.behavior.command.editor.Macro;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("命令模式")
class CommandTest {

    @DisplayName("普通")
    @Test
    void commandTest() {
        //命令接收者
        ReceiverLight light = new ReceiverLight();
        //开灯命令
        CommandLightOn lightOn = new CommandLightOn(light);
        //关灯命令
        CommandLightOff lightOff = new CommandLightOff(light);

        //命令调度员
        Invoker invoker = new Invoker();

        //添加命令
        invoker.setCommand(0, lightOn, lightOff);

        //触发命令
        invoker.onButtonWasPushed(0);
        //触发命令
        invoker.offButtonWasPushed(0);
        //触发撤回命令
        invoker.undoButtonWasPushed();

        //todo 暂不支持反复撤销
        invoker.undoButtonWasPushed();
        invoker.undoButtonWasPushed();
    }

    @DisplayName("匿名内部类")
    @Test
    void anonymousTest() {
        //命令接收者
        ReceiverLight light = new ReceiverLight();

        //命令调度员
        Invoker invoker = new Invoker();

        //添加命令
        invoker.setCommand(0, new Command() {
            @Override
            public void execute() {
                light.on();
            }

            @Override
            public void undo() {
                light.off();
            }
        }, new Command() {
            @Override
            public void execute() {
                light.off();
            }

            @Override
            public void undo() {
                light.on();
            }
        });

        //触发命令
        invoker.onButtonWasPushed(0);
        //触发命令
        invoker.offButtonWasPushed(0);
        //触发撤回命令
        invoker.undoButtonWasPushed();

        //todo 暂不支持反复撤销
        invoker.undoButtonWasPushed();
        invoker.undoButtonWasPushed();
    }

    @DisplayName("编辑器")
    @Test
    void editorTest() {
        Macro macro = new Macro();
        Editor editor = new Editor();
        macro.record(new ActionOpen(editor));
        macro.record(new ActionSave(editor));
        macro.record(new ActionClose(editor));
        macro.run();
    }

    @DisplayName("编辑器-匿名内部类")
    @Test
    void editorAnonymousTest() {
        Macro macro = new Macro();
        Editor editor = new Editor();
        macro.record(new Action() {
            @Override
            public void perform() {
                editor.open();
            }
        });
        macro.record(new Action() {
            @Override
            public void perform() {
                editor.save();
            }
        });
        macro.record(new Action() {
            @Override
            public void perform() {
                editor.close();
            }
        });
        macro.run();
    }

    @DisplayName("编辑器-lambda")
    @Test
    void editorLambdaTest() {
        Macro macro = new Macro();
        Editor editor = new Editor();
        macro.record(editor::open);
        macro.record(editor::save);
        macro.record(editor::close);
        macro.run();
    }

}
