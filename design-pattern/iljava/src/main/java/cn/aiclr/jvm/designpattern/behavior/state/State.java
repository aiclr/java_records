package cn.aiclr.jvm.designpattern.behavior.state;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <pre>状态模式 state pattern
 * 解决对象在多种状态转换时,需要对外输出不同的行为
 * 状态和行为一一对应,状态之间可以相互转换
 * 当一个对象的内在状态改变时,允许改变其行为,这个对象看起来像是改变了类
 *
 * 符合ocp原则
 *
 * 代码可读性强,避免出现很多if-else
 *
 * 会产生很多类,每个状态对应一个类,增加维护难度
 *
 * 当一个事件或者对象有很多种状态,状态之间会相互转换,对不同的状态要求有不同的行为的时候,可以考虑使用状态模式
 *
 * eg:
 *  1. 抽象抽奖活动状态
 *  2. 车道进出场规则校验状态模式
 */
public interface State {

    Logger log = LoggerFactory.getLogger(State.class);

    /**
     * 扣除50积分后将状态设置为可以抽奖状态
     */
    void deductMoney();

    /**
     * <pre>是否抽中奖品
     * 不扣积分则无法抽奖，需要先扣除积分
     */
    boolean raffle();

    /**
     * <pre>发放奖品
     * 未抽奖不能发奖品
     */
    void dispensePrize();

}
