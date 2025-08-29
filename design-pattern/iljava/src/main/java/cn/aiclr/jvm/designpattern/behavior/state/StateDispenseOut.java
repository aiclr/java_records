package cn.aiclr.jvm.designpattern.behavior.state;

/**
 * 奖品派完状态
 */
public class StateDispenseOut implements State {
    Activity activity;

    public StateDispenseOut(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void deductMoney() {
        log.warn("奖品派完了");
    }

    @Override
    public boolean raffle() {
        log.warn("奖品派完了");
        return false;
    }

    @Override
    public void dispensePrize() {
        log.warn("奖品派完了");
    }
}
