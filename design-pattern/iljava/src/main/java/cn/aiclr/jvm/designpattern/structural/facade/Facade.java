package cn.aiclr.jvm.designpattern.structural.facade;

/**
 * 外观模式 分层
 */
public interface Facade {
    /**
     * start 时，{@link Model1#on()}，{@link Model2#start()}
     */
    void start();

    /**
     * end 是，{@link Model1#off()}，{@link Model2#stop()}
     */
    void end();
}
