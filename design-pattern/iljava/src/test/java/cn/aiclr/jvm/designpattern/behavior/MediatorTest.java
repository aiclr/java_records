package cn.aiclr.jvm.designpattern.behavior;

import cn.aiclr.jvm.designpattern.behavior.mediator.ColleagueAlarm;
import cn.aiclr.jvm.designpattern.behavior.mediator.ColleagueCoffeeMachine;
import cn.aiclr.jvm.designpattern.behavior.mediator.ColleagueCurtains;
import cn.aiclr.jvm.designpattern.behavior.mediator.ColleagueTV;
import cn.aiclr.jvm.designpattern.behavior.mediator.Mediator;
import cn.aiclr.jvm.designpattern.behavior.mediator.MediatorConcrete;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("中介模式")
class MediatorTest {

    @Test
    void mediatorTest() {
        Mediator mediator = new MediatorConcrete();

        ColleagueAlarm alarm = new ColleagueAlarm(mediator, "Alarm");
        ColleagueTV tv = new ColleagueTV(mediator, "TV");
        ColleagueCoffeeMachine coffeeMachine = new ColleagueCoffeeMachine(mediator, "CoffeeMachine");
        ColleagueCurtains curtains = new ColleagueCurtains(mediator, "Curtains");

        alarm.sendMessage(0);
        tv.sendMessage(0);
        coffeeMachine.sendMessage(1);
        curtains.sendMessage(1);
    }
}
