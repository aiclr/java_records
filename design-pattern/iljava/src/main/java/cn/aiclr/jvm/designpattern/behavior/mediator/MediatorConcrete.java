package cn.aiclr.jvm.designpattern.behavior.mediator;

import java.util.HashMap;
import java.util.Map;

/**
 * 中介实现类
 */
public class MediatorConcrete extends Mediator {

    private final Map<String, Colleague> colleagueMap;
    private final Map<String, String> interMap;

    public MediatorConcrete() {
        colleagueMap = new HashMap<>();
        interMap = new HashMap<>();
    }

    @Override
    public void register(String colleagueName, Colleague colleague) {
        colleagueMap.put(colleagueName, colleague);
        switch (colleague) {
            case ColleagueAlarm ignored -> interMap.put("Alarm", colleagueName);
            case ColleagueTV ignored -> interMap.put("TV", colleagueName);
            case ColleagueCoffeeMachine ignored -> interMap.put("CoffeeMachine", colleagueName);
            case ColleagueCurtains ignored -> interMap.put("Curtains", colleagueName);
            case null -> throw new IllegalStateException("Unexpected value: null");
            default -> throw new IllegalStateException("Unexpected value: " + colleague);
        }
    }

    /**
     * 中介核心方法
     * 根据得到的消息,完成任务
     * 协调各个同事对象,完成任务
     *
     * @param stateChange
     * @param colleagueName
     */
    @Override
    public void getMessage(int stateChange, String colleagueName) {
        switch (colleagueMap.get(colleagueName)) {
            case ColleagueTV ignored -> {
                if (0 == stateChange) {
                    ((ColleagueCurtains) colleagueMap.get(interMap.get("Curtains"))).stopCurtains();
                    ((ColleagueCoffeeMachine) colleagueMap.get(interMap.get("CoffeeMachine"))).startCoffeeMachine();
                    ((ColleagueTV) colleagueMap.get(interMap.get("TV"))).startTV();
                }
                if (1 == stateChange) {
                    ((ColleagueTV) colleagueMap.get(interMap.get("TV"))).stopTV();
                    ((ColleagueCoffeeMachine) colleagueMap.get(interMap.get("CoffeeMachine"))).stopCoffeeMachine();
                    ((ColleagueCurtains) colleagueMap.get(interMap.get("Curtains"))).startCurtains();
                }
            }
            case ColleagueAlarm ignored -> {
                if (0 == stateChange) {
                    ((ColleagueCurtains) colleagueMap.get(interMap.get("Curtains"))).stopCurtains();
                    ((ColleagueCoffeeMachine) colleagueMap.get(interMap.get("CoffeeMachine"))).stopCoffeeMachine();
                }
                if (1 == stateChange) {
                    ((ColleagueCurtains) colleagueMap.get(interMap.get("Curtains"))).startCurtains();
                    ((ColleagueCoffeeMachine) colleagueMap.get(interMap.get("CoffeeMachine"))).startCoffeeMachine();
                }
            }
            case ColleagueCoffeeMachine ignored -> {
                if (0 == stateChange) {
                    ((ColleagueCoffeeMachine) colleagueMap.get(interMap.get("CoffeeMachine"))).stopCoffeeMachine();
                }
                if (1 == stateChange) {
                    ((ColleagueCoffeeMachine) colleagueMap.get(interMap.get("CoffeeMachine"))).startCoffeeMachine();
                }
            }
            case ColleagueCurtains ignored -> {
                if (0 == stateChange) {
                    ((ColleagueCoffeeMachine) colleagueMap.get(interMap.get("CoffeeMachine"))).stopCoffeeMachine();
                    ((ColleagueCurtains) colleagueMap.get(interMap.get("Curtains"))).stopCurtains();
                }
                if (1 == stateChange) {
                    ((ColleagueCurtains) colleagueMap.get(interMap.get("Curtains"))).startCurtains();
                    ((ColleagueCoffeeMachine) colleagueMap.get(interMap.get("CoffeeMachine"))).startCoffeeMachine();
                }
            }
            case null -> throw new IllegalStateException("Unexpected value: null");
            default -> throw new IllegalStateException("Unexpected value");
        }
    }

    /**
     * 自己也可以发消息
     */
    @Override
    public void sendMessage() {
        throw new UnsupportedOperationException();
    }
}
