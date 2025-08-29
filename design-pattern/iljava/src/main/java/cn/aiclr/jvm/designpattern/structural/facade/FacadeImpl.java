package cn.aiclr.jvm.designpattern.structural.facade;

public class FacadeImpl implements Facade {

    private final Model1 model1;
    private final Model2 model2;

    public FacadeImpl() {
        this.model1 = Model1.getInstance();
        this.model2 = Model2.getInstance();
    }

    @Override
    public void start() {
        model1.on();
        model2.start();
    }

    @Override
    public void end() {
        model2.stop();
        model1.off();
    }
}
